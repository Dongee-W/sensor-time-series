package sensor.timeseries.example

import net.seninp.jmotif.sax.{NumerosityReductionStrategy, SAXProcessor}
import net.seninp.jmotif.sax.alphabet.NormalAlphabet
import net.seninp.jmotif.sax.datastructure.SAXRecords
import sensor.timeseries.dataset.NaiveTimeSeries

import scala.util.Try

object SAXSlidingWindowExample {
  def main(args: Array[String]): Unit = {

    val na = new NormalAlphabet
    val sp = new SAXProcessor

    //val series = NaiveTimeSeries.simpleRepeatingPatterns.toArray
    val series = NaiveTimeSeries.increasingWithNoise(500).toArray

    val slidingWindowSize = 10
    val paaSize = 5
    val alphabetSize = 3
    val nThreshold = 0.0
    val nrStrategy = NumerosityReductionStrategy.MINDIST

    val res: SAXRecords = sp.ts2saxViaWindow(series, slidingWindowSize, paaSize,
      na.getCuts(alphabetSize), nrStrategy, nThreshold)

    val index = res.getIndexes()
    println(index)
    val iter = index.iterator()

    while (iter.hasNext) {
      val idx = iter.next()
      Try {
        println(s"At index $idx: " + String.valueOf(res.getByIndex(idx).getPayload()))
      }

    }
  }
}
