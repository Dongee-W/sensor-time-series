package sensor.timeseries.fault_detection

import breeze.linalg.DenseVector
import sensor.timeseries.UnivariateTimeSeries
import org.scalatest.FlatSpec

class RapidFaultDetectionSpec extends FlatSpec {
  "The noise function" should "identify windows with noise greater than threshold" in {
    val v = DenseVector(1.0, 1.5, 1.6, 3.1, 3.0, 3.5, 4.0, 4.5, 4.6, 4.7)

    println(SensorFaultDetection.noise(v, 3))
  }

}
