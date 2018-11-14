package com.sequence.metrics

import breeze.linalg.DenseVector
import org.scalatest.FlatSpec
import org.slf4j.LoggerFactory

class PairwiseDistanceSpec extends FlatSpec {

  val logger = LoggerFactory.getLogger("test.logger")

  "The correlation function" should "compute Pearson's product-moment correlation between two series" in {
    val v1 = DenseVector(1.0,2,124,12,5,31,2,568,25,2,52,5,3,3,2,2,51,51,64,4)
    val v2 = DenseVector(11.0,22,14,122,5,301,2,568,25,2,52,512,31,32,2,2,521,521,614,24)
    logger.debug(PairwiseDistance.correlation(v1, v2).toString)

  }

  "The euclidean distance" should "compute euclidean distance between two series" in {
    val v3 = DenseVector(2.0, 3.0)
    val v4 = DenseVector(1.0, 4.0)
    logger.debug(PairwiseDistance.euclidean(v3, v4).toString)
  }
}
