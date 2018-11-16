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
    //val short = DenseVector(1.0,2,3,4,7,0,12312,23,1,123,1,23123)
    //val short = DenseMatrix.rand[Double](4,3)
    //val test = DenseVector.rand[Double](4)
    //short(*, ::).map(x => DenseVector(Sorting.quickSort(x.toArray)))
    /*
    time {
    for (i <- 1 to 100) {
      val v1 = DenseVector.rand[Double](1000)

      val cute =
    }}*/

    //println(quickSelect(short.toArray, 4))
    //println(short)



    /*
    implicit val ord = Ordering.by[Double, Double](x => -x)
    val beam = new Beam[Double](5)
    beam += 1.0
    println(beam)
    beam += 2.0
    beam += 3.0
    beam += 0.0
    println(beam)
    beam += 100.0
    println(beam)
    beam += 10.0
    println(beam)

    println(beam.result()(4))*/





    /*
    time {
      val v1 = DenseVector.rand[Double](1000)
      val v2 = DenseVector.rand[Double](1000)
      MatrixProfileI.stampKF(v1, v2, 256, 3,true)
    }*/
/*
    val v1 = DenseVector.rand[Double](1000)
    val v2 = DenseVector.rand[Double](1000)
    println(MatrixProfileI.stampK(v1, v2, 16, 3, true))
    //println()
    println(MatrixProfileI.stampKFF(v1, v2, 16, 3, true))
*/






    //println(test.foreachPair((idx, value) => idx.toDouble))

    //println(dtw.d(Array(Array(1.1,1.2,1.3,4.5,12.4)), Array(Array(12.1,12.2,12.3,2.5,12.4))))
    //println(PairwiseDistance.dtw(DenseVector(Array(1.1,1.2,1.3,4.5,Double.PositiveInfinity)), DenseVector(Array(12.1,12.2,12.3,2.5,12.4)), new EuclideanDistance()))




  }

}
