package com.sequence.similarity_search

import breeze.linalg.{DenseVector, accumulate, min, reverse}
import breeze.numerics.{abs, sqrt}
import breeze.signal.{fourierTr, iFourierTr}
import com.sequence.UnivariateTimeSeries
import com.sequence.universal_functions.{mean, std}

/** The algorithm proposed in the paper
  * Matrix Profile I: All Pairs Similarity Joins for Time Series:
  * A Unifying View that Includes Motifs, Discords and Shapelets
  *
  * Chin-Chia Michael Yeh, Yan Zhu, Liudmila Ulanova, Nurjahan Begum,
  * Yifei Ding, Hoang Anh Dau, Diego Furtado Silva, Abdullah Mueen,
  * and Eamonn Keogh
  */
object MatrixProfileI {
  def slidingDotProduct(query: DenseVector[Double], sequence: DenseVector[Double]) = {
    val n = sequence.length
    val m = query.length
    require(n >= m, "Sequence should be longer than query")

    val queryReversed = DenseVector.zeros[Double](n)
    queryReversed(0 until m) := reverse(query)

    val Qf = fourierTr(queryReversed)
    val Tf = fourierTr(sequence)

    val QT = iFourierTr(Qf *:* Tf)

    QT(m - 1 until n).map(z => z.real)
  }

  /** MUEEN’S ALGORITHM FOR SIMILARITY SEARCH
    *
    * Note that constant query of contant subsequence can
    * lead to NaN or Infinity since distance is defined as
    * z-normalized enclidean distance.
    *
    * @param query
    * @param sequence
    * @return
    */
  def mass(query: DenseVector[Double], sequence: DenseVector[Double]) = {
    val m = query.length
    val n = sequence.length
    val QT = slidingDotProduct(query, sequence)
    val qm = mean(query)
    val qsd = std(query)


    val sequenceCumSum = DenseVector.zeros[Double](n + 1)
    sequenceCumSum(1 to n) := accumulate(sequence)
    val sequenceCumSum2 = DenseVector.zeros[Double](n + 1)
    sequenceCumSum2(1 to n) := accumulate(sequence.map(x => x * x))

    val subSequenceSum = sequenceCumSum(m to n) - sequenceCumSum(0 to n - m)
    val subSequenceSum2 = sequenceCumSum2(m to n) - sequenceCumSum2(0 to n - m)

    val subSequenceMean = subSequenceSum /:/ m.toDouble
    // Variance as Expectation of Square minus Square of Expectation
    val subSequenceVariance = subSequenceSum2 / m.toDouble - subSequenceMean.map(x => x * x)
    val subSequenceStd = sqrt(subSequenceVariance)

    // The abs function is used to improve numerical stability
    val distanceMatrix = sqrt(
      2.0 * m.toDouble * abs(1.0 -
        (QT - m.toDouble * qm * subSequenceMean) / (m.toDouble * qsd * subSequenceStd)
      )
    )
    distanceMatrix
  }

  /** Scalable Time series Anytime Matrix Profile
    *
    * Note that the returned matrix profile may contain NaN and Infinity.
    * This is due to the fact that the stamp algorithm use z-normalized
    * euclidean distance as distance metric, which does not exist when
    * the sequence consist of same value. Users are recommended to
    * ignore/drop these values when using the matrix profile.
    *
    * @param sa the first sequence
    * @param sb the second sequence
    * @param subLength interested subsequence length
    * @param ignoreTrivial ignore subsequence within subLength/2 before and after the query
    * @return tuple of matrix profile and matrix profile index
    */
  def stamp(sa: DenseVector[Double], sb: DenseVector[Double],
            subLength: Int, ignoreTrivial: Boolean = false) = {
    val na = sa.length
    val nb = sb.length
    val matrixProfile = DenseVector.fill(na - subLength + 1, Double.PositiveInfinity)
    val matrixProfileIndex = DenseVector.fill[Int](na - subLength + 1, -1)
    for (idx <- 0 to nb - subLength) {
      val distanceProfile = mass(sb(idx until idx + subLength), sa)
      if (ignoreTrivial) {
        val exclusionZone = Math.max(idx - subLength / 2, 0) to Math.min(idx + subLength / 2, nb - subLength)
        distanceProfile(exclusionZone) := matrixProfile(exclusionZone)
      }
      matrixProfileIndex(distanceProfile <:< matrixProfile) := idx
      matrixProfile := min(matrixProfile, distanceProfile)
    }
    (matrixProfile, matrixProfileIndex)
  }



}
