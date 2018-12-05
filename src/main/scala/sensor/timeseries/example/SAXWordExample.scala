package sensor.timeseries.example

import net.seninp.jmotif.sax.{NumerosityReductionStrategy, SAXProcessor}
import net.seninp.jmotif.sax.alphabet.NormalAlphabet
import net.seninp.jmotif.sax.datastructure.SAXRecords
import sensor.timeseries.dataset.NaiveTimeSeries

object SAXWordExample {
  def main(args: Array[String]): Unit = {
    val series = NaiveTimeSeries.simpleRepeatingPatterns.toArray

    val na = new NormalAlphabet
    val sp = new SAXProcessor

    val slidingWindowSize = 10
    val paaSize = 5
    val alphabetSize = 3
    val nThreshold = 0.0
    val nrStrategy = NumerosityReductionStrategy.MINDIST

    val saxData: SAXRecords = sp.ts2saxViaWindow(series, slidingWindowSize, paaSize,
      na.getCuts(alphabetSize), nrStrategy, nThreshold)

    val motifs = saxData.getSimpleMotifs(10)
    val topMotif = motifs.get(0)
    println("top motif " + String.valueOf(topMotif.getPayload()) + " seen " +
      topMotif.getIndexes().size() + " times.")

  }
}
