package com.sequence.metrics

import breeze.linalg.{Vector, sum}
import breeze.numerics.abs
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation

object PairwiseDistance {
  def correlation(x: Vector[Double], y: Vector[Double]) = {
    require(x.length == y.length, "Two vectors need to have same length.")
    new PearsonsCorrelation().correlation(x.toArray, y.toArray)
  }

  def correlation(x: Array[Double], y: Array[Double]) = {
    require(x.length == y.length, "Two vectors need to have same length.")
    new PearsonsCorrelation().correlation(x, y)
  }

  def euclidean(x: Vector[Double], y: Vector[Double]) = {
    require(x.length == y.length, "Two vectors need to have same length.")
    Math.sqrt(x -:- y dot x -:- y)
  }

  def euclideanFast(x: Vector[Double], y: Vector[Double]) = {
    require(x.length == y.length, "Two vectors need to have same length.")
    x -:- y dot x -:- y
  }

  def manhattan(x: Vector[Double], y: Vector[Double]) = {
    require(x.length == y.length, "Two vectors need to have same length.")
    sum(abs(x -:- y))
  }
}


