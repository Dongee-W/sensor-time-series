package sensor.timeseries.example

import net.seninp.jmotif.sax.motif.EMMAImplementation
import sensor.timeseries.dataset.NaiveTimeSeries

object EMMAExample {
  def main(args: Array[String]): Unit = {

    val series = NaiveTimeSeries.simpleRepeatingPatterns.toArray

    val MOTIF_SIZE = 10
    val MOTIF_RANGE = 0.2
    val PAA_SIZE = 5
    val ALPHABET_SIZE = 4

    // subsequence with std lower than ZNORM_THRESHOLD will be set to zero
    val ZNORM_THRESHOLD = 0.0


    val motifsEMMA = EMMAImplementation.series2EMMAMotifs(series, MOTIF_SIZE,
      MOTIF_RANGE, PAA_SIZE, ALPHABET_SIZE, ZNORM_THRESHOLD)

    println(motifsEMMA)
  }

}
