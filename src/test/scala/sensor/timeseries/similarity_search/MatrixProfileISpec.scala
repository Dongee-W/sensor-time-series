package sensor.timeseries.similarity_search

import breeze.linalg.{DenseVector, sum}
import sensor.timeseries.UnivariateTimeSeries
import sensor.timeseries.universal_functions.{mean, std}
import org.scalatest.FlatSpec
import org.slf4j.LoggerFactory


class MatrixProfileISpec extends FlatSpec {

  val logger = LoggerFactory.getLogger("test.logger")

  "The slidingDotProduct function" should
    "compute dot products of a query and all sliding windows" in {

    val test = MatrixProfileI.slidingDotProduct(DenseVector(1.0, 2.0, 3.0),
      DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0))
    logger.debug(test.toString)

    val slid = UnivariateTimeSeries.slidingWindow(DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0), 3)
    logger.debug((slid * DenseVector(1.0, 2.0, 3.0)).toString)
  }

  "The mass function" should "calculate distance profile of a query sequence" in {
    val dp = MatrixProfileI.mass(DenseVector(1.0, 2.0, 3.0),
      DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0))

    val v1 = DenseVector(1.0, 2.0, 3.0)
    val v2 = DenseVector(0.6, 1.0, 2.0)

    val v1Norm = (v1 - mean(v1)) / std(v1)
    val v2Norm = (v2 - mean(v2)) / std(v2)

    logger.debug("Manual: " + Math.sqrt(sum((v1Norm - v2Norm) *:* (v1Norm - v2Norm))))

    logger.debug("With mass: " + dp.toString)
  }

  "The stamp function" should "calculate matrix profile for two sequence" in {
    val dp = MatrixProfileI.stamp(DenseVector(1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 0.5, 0.6, 1.0),
      DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0), 3, true)

    logger.debug(dp.toString)
  }

  "The stampI function" should "calculate matrix profile for two sequence" in {
    val dp = MatrixProfileI.stampI(DenseVector(1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 0.5, 0.6, 1.0),
      DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0), 3, true)

    logger.debug(dp.toString)
  }

  "The stampK function" should "calculate matrix profile for two sequence" in {
    val dp = MatrixProfileI.stampK(DenseVector(1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 0.5, 0.6, 1.0),
      DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0), 3, 1, true)

    logger.debug(dp.toString)
  }

  "The stampKBeam function" should "calculate matrix profile for two sequence" in {
    val dp = MatrixProfileI.stampKBeam(DenseVector(1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 0.5, 0.6, 1.0),
      DenseVector(0.5, 0.6, 1.0, 2.0, 3.0, 2.0, 1.0), 3, 1, true)

    logger.debug(dp.toString)
  }

}
