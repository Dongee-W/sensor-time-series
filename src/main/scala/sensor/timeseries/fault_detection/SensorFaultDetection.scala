package sensor.timeseries.fault_detection

import breeze.linalg.{*, BitVector, DenseMatrix, DenseVector, Vector}
import sensor.timeseries.UnivariateTimeSeries
import sensor.timeseries.universal_functions.std

/** The algorithm base on "Sensor Network Data Fault Types"
 *
 */
object SensorFaultDetection {
  def noise(x: Vector[Double], window: Int): Vector[Double] = {
    def f(v: DenseVector[Double]) = std(v)
    val sdVector = UnivariateTimeSeries.slidingWindowReduction(x, window, f)
    sdVector// >:> threshold
  }

}
