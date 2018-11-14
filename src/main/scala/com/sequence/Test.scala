package com.sequence

import breeze.linalg._
import breeze.math.Complex
import breeze.numerics.sqrt
import com.sequence.metrics.PairwiseDistance
import com.sequence.universal_functions._
import breeze.signal._
import com.sequence.similarity_search.MatrixProfileI
import org.slf4j.LoggerFactory

object Test {
  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) / 1000000 + "ms")
    result
  }

  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger("normal.logger")
    logger.info("Hello world.")

    time{
      for (i <- 1 to 100) {
        val v1 = DenseVector.rand[Double](100)
        val v2 = DenseVector.rand[Double](1500)
        val dp = MatrixProfileI.mass(v1, v2)
      }

    }




  }

}
