package sensor.timeseries

import breeze.linalg._
import breeze.math.Complex
import breeze.numerics.sqrt
import sensor.timeseries.universal_functions._
import breeze.signal._
import sensor.timeseries.math.distance.PairwiseDistance
import sensor.timeseries.similarity_search.MatrixProfileI
import org.slf4j.LoggerFactory
import smile.math.distance.{DynamicTimeWarping, EuclideanDistance}

object Test {
  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) / 1000000 + "ms")
    result
  }

  def main(args: Array[String]): Unit = {
    val dtw = new DynamicTimeWarping(new EuclideanDistance())
    println(PairwiseDistance.euclidean(Array(1.1,1.2,1.3,4.5,Double.NaN), Array(12.1,12.2,12.3,42.5,12.4)))
    //println(dtw.d(Array(Array(1.1,1.2,1.3,4.5,12.4)), Array(Array(12.1,12.2,12.3,2.5,12.4))))
    //println(PairwiseDistance.dtw(DenseVector(Array(1.1,1.2,1.3,4.5,Double.PositiveInfinity)), DenseVector(Array(12.1,12.2,12.3,2.5,12.4)), new EuclideanDistance()))




  }

}
