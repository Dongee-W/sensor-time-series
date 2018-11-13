package com.sequence.similarity_search

import breeze.linalg.{DenseVector, sum}
import com.sequence.UnivariateTimeSeries
import com.sequence.universal_functions.{mean, std}
import org.scalatest.FlatSpec


class MatrixProfileISpec extends FlatSpec {
  "The slidingDotProduct function" should
    "compute dot products of a query and all sliding windows" in {
    val test = MatrixProfileI.slidingDotProduct(DenseVector(1.0, 2.0, 3.0),
      DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0))
    println(test)

    val slid = UnivariateTimeSeries.slidingWindow(DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0), 3)
    println(slid * DenseVector(1.0, 2.0, 3.0))
  }

  "The mass function" should "calculate distance profile of a query sequence" in {
    val dp = MatrixProfileI.mass(DenseVector(1.0, 2.0, 3.0),
      DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0))

    val v1 = DenseVector(1.0, 2.0, 3.0)
    val v2 = DenseVector(0.6, 1.0, 2.0)

    val v1Norm = (v1 - mean(v1)) / std(v1)
    val v2Norm = (v2 - mean(v2)) / std(v2)

    println("MANUAL 2:" + Math.sqrt(sum((v1Norm - v2Norm) *:* (v1Norm - v2Norm))))

    println(dp)
  }
  "The mass2 function" should "calculate distance profile of a query sequence" in {
    val dp = MatrixProfileI.mass2(DenseVector(1.0, 2.0, 3.0),
      DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0))

    val v1 = DenseVector(1.0, 2.0, 3.0)
    val v2 = DenseVector(0.6, 1.0, 2.0)

    val v1Norm = (v1 - mean(v1)) / std(v1)
    val v2Norm = (v2 - mean(v2)) / std(v2)

    println("MANUAL 2:" + Math.sqrt(sum((v1Norm - v2Norm) *:* (v1Norm - v2Norm))))

    println(dp)
  }

  "The stamp function" should "calculate matrix profile for two sequence" in {
    val dp = MatrixProfileI.stamp(DenseVector(1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 0.5, 0.6, 1.0),
      DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0), 3, true)

    println(dp)
  }

}
