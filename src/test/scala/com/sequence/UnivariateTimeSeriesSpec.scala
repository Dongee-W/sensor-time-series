package com.sequence

import breeze.linalg.{DenseVector, sum}
import org.scalatest.FlatSpec

class UnivariateTimeSeriesSpec extends FlatSpec {
  "The fillLinear function" should "linearly interpolate a time series" in {
    val v1 = DenseVector(1.0, Double.NaN, 2.0, 3.0, 4.0)

    println("Gap = 1: " + UnivariateTimeSeries.fillLinear(v1, 2))

    val v2 = DenseVector(1.0, Double.NaN, Double.NaN, 2.0, 3.0, 4.0)
    println("Gap = 2: " + UnivariateTimeSeries.fillLinear(v2, 2))

    val v3 = DenseVector(1.0, Double.NaN, Double.NaN, Double.NaN, 2.0, 3.0, Double.NaN,4.0)
    println("Gap = 3: " + UnivariateTimeSeries.fillLinear(v3, 2))
  }

  "The slidingWindow function" should "returns the sliding matrix given a vetor" in {
    val v = DenseVector(1.0, 2.0, 3.0, 4.0)
    println(UnivariateTimeSeries.slidingWindow(v, 2))
  }

  "The slidingWindowReduction function" should "returns a reduced sliding vetor" in {
    val v = DenseVector(1.0, 2.0, 3.0, 4.0)
    def reduce(dv: DenseVector[Double]) = sum(dv)
    println(UnivariateTimeSeries.slidingWindowReduction(v, 2, reduce))
  }

  "The numNaN function" should "return number of NaN in a series" in {
    val v = DenseVector(1.0, 2.0, Double.NaN)
    println("Number of NaN: " + UnivariateTimeSeries.numNaN(v))
  }


}
