package sensor.timeseries.motif

import breeze.linalg.{BitVector, DenseVector, sum}
import sensor.timeseries.similarity_search.MatrixProfileI.mass

object MotifDiscovery {

  /** Remove trivial match when finding motifs
    *
    * @param matchVector a Boolean vector represents matches
    * @param queryIndex current query index
    * @return match vector with trivial matches removed
    */
  def removeTrivial(matchVector: BitVector, queryIndex: Int) = {

    var i = queryIndex
    while (i < matchVector.length && matchVector(i)) {
      i += 1
    }
    val lastTrivialMatch = i - 1

    i = queryIndex - 1
    while (i >= 0 && matchVector(i)) {
      i -= 1
    }
    val firstTrivialMatch = i + 1
    matchVector(firstTrivialMatch to lastTrivialMatch) := false
    matchVector
  }

  /** Finding motifs of a sequence
    *
    * Using fast z noramlized enclidean distance calculation as a subroutine
    * to find k-motifs. The motif is defined according to the paper:
    * Finding Motifs in Time Series by Jessica Lin, Eamonn Keogh,
    * Stefano Lonardi and Pranav Patel
    *
    * @param s data sequence
    * @param window window size
    * @param threshold distance threshold to be considiered a match
    * @return k-motifs
    */
  def findMotif(s: DenseVector[Double], window: Int, threshold: Double) = {
    //require(k < s.length, "Unable to find k-motifs when k is larger than sequence length")
    val n = s.length
    val motifCount = DenseVector.zeros[Double](n - window + 1)
    for (idx <- 0 to n - window) {
      val distanceProfile = mass(s(idx until idx + window), s)
      motifCount :+= removeTrivial(distanceProfile <:< threshold, idx).map(x => if (x) 1.0 else 0.0)
    }
    motifCount
  }
}
