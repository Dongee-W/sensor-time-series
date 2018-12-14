package sensor.timeseries.math.distance

import breeze.linalg.{DenseVector, Vector, sum}
import breeze.numerics.abs
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation
import smile.math.distance.{Distance, DynamicTimeWarping}

object PairwiseDistance {
  def correlation(x: Vector[Double], y: Vector[Double]) = {
    require(x.length == y.length, s"Vectors have different length: ${x.length} != ${y.length}.")
    new PearsonsCorrelation().correlation(x.toArray, y.toArray)
  }

  def correlation(x: Array[Double], y: Array[Double]) = {
    require(x.length == y.length, s"Vectors have different length: ${x.length} != ${y.length}.")
    new PearsonsCorrelation().correlation(x, y)
  }

  def euclidean(x: Vector[Double], y: Vector[Double]) = {
    require(x.length == y.length, s"Vectors have different length: ${x.length} != ${y.length}.")
    Math.sqrt(x -:- y dot x -:- y)
  }

  def euclidean(x: Array[Double], y: Array[Double]) = {
    require(x.length == y.length, s"Vectors have different length: ${x.length} != ${y.length}.")
    val xv = DenseVector(x)
    val yv = DenseVector(y)

    Math.sqrt(xv -:- yv dot xv -:- yv)
  }

  def euclideanFast(x: Vector[Double], y: Vector[Double]) = {
    require(x.length == y.length, s"Vectors have different length: ${x.length} != ${y.length}.")
    x -:- y dot x -:- y
  }

  def manhattan(x: Vector[Double], y: Vector[Double]) = {
    require(x.length == y.length, s"Vectors have different length: ${x.length} != ${y.length}.")
    sum(abs(x -:- y))
  }

  def dtw[T](x: Vector[Double], y: Vector[Double], distanceMetrix: Distance[Array[Double]]) = {
    require(x.length == y.length, s"Vectors have different length: ${x.length} != ${y.length}.")
    val dtw = new DynamicTimeWarping(distanceMetrix)
    dtw.d(Array(x.toArray), Array(y.toArray))
  }

}


