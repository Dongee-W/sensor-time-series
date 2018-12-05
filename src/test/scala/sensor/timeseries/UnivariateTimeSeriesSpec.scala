package sensor.timeseries

import breeze.linalg.{DenseVector, sum}
import org.scalatest.FlatSpec
import org.slf4j.LoggerFactory

class UnivariateTimeSeriesSpec extends FlatSpec {
  val logger = LoggerFactory.getLogger("test.logger")

  "The fillLinear function" should "linearly interpolate a time series" in {
    val v1 = DenseVector(1.0, Double.NaN, 2.0, 3.0, 4.0)

    logger.debug("Gap = 1: " + UnivariateTimeSeries.fillLinear(v1, 2))

    val v2 = DenseVector(1.0, Double.NaN, Double.NaN, 2.0, 3.0, 4.0)
    logger.debug("Gap = 2: " + UnivariateTimeSeries.fillLinear(v2, 2))

    val v3 = DenseVector(1.0, Double.NaN, Double.NaN, Double.NaN, 2.0, 3.0, Double.NaN,4.0)
    logger.debug("Gap = 3: " + UnivariateTimeSeries.fillLinear(v3, 2))
  }

  "The slidingWindow function" should "returns the sliding matrix given a vetor" in {
    val v = DenseVector(1.0, 2.0, 3.0, 4.0)
    logger.debug(UnivariateTimeSeries.slidingWindow(v, 2).toString)
  }

  "The slidingWindowReduction function" should "returns a reduced sliding vetor" in {
    val v = DenseVector(1.0, 2.0, 3.0, 4.0)
    def reduce(dv: DenseVector[Double]) = sum(dv)
    logger.debug(UnivariateTimeSeries.slidingWindowReduction(v, 2, reduce).toString)
  }

  "The numNaN function" should "return number of NaN in a series" in {
    val v = DenseVector(1.0, 2.0, Double.NaN)
    logger.debug("Number of NaN: " + UnivariateTimeSeries.numNaN(v))
  }


}
