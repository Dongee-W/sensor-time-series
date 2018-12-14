package sensor.timeseries.dataset

import breeze.linalg._
import sensor.timeseries.universal_functions.{mean, std}

object NaiveTimeSeries {
  def simpleRepeatingPatterns = {
    val dv = DenseVector.rand[Double](500)

    val pattern = DenseVector(1.0, 2.0, 3.0, 1.0, 2.0, 4.0, 3.0, 2.0, 1.0, 1.0)

    // Simulate uniform distribution [0, 1] with mean at 0.5 and std = 0.289
    val normalized = (pattern - mean(pattern)) * std(pattern) * 0.289

    dv(31 to 40) := pattern
    dv(51 to 60) := pattern
    dv(101 to 110) := pattern
    dv(350 to 359) := pattern
    dv(430 to 439) := pattern
    dv
  }

  def increasingWithNoise(length: Int) = {
    val dv = linspace(0, 20.0 / 500 * length, length)

    val noise = (DenseVector.rand[Double](length) - 0.5) / 2.0

    dv + noise
  }

}
