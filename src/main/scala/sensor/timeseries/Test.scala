package sensor.timeseries

import breeze.collection.mutable.Beam
import breeze.linalg._
import breeze.math.Complex
import breeze.numerics.sqrt
import sensor.timeseries.universal_functions._
import breeze.signal._
import breeze.util.quickSelect
import sensor.timeseries.math.distance.PairwiseDistance
import sensor.timeseries.similarity_search.MatrixProfileI
import org.slf4j.LoggerFactory
import sensor.timeseries.dataset.NaiveTimeSeries
import sensor.timeseries.motif.MotifDiscovery
import smile.math.distance.{DynamicTimeWarping, EuclideanDistance}

import scala.util.{Random, Sorting}

object Test {
  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) / 1000000 + "ms")
    result
  }

  def test(v: DenseVector[Double]) = {
    v(0) = 1000.0
    v
  }

  def main(args: Array[String]): Unit = {
    //println(NaiveTimeSeries.increasingWithNoise)

  }

}
