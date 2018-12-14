package sensor.timeseries.example

import net.seninp.jmotif.sax.SAXProcessor
import net.seninp.jmotif.sax.alphabet.NormalAlphabet
import sensor.timeseries.dataset.NaiveTimeSeries

object SAXExample {
  def main(args: Array[String]): Unit = {

    val na = new NormalAlphabet
    val sp = new SAXProcessor

    //val series = NaiveTimeSeries.simpleRepeatingPatterns.toArray
    val series = NaiveTimeSeries.increasingWithNoise(500).toArray

    val paaSize = 100
    val alphabetSize = 3
    val nThreshold = 0.0

    // perform the discretization
    val str = sp.ts2saxByChunking(series, paaSize, na.getCuts(alphabetSize), nThreshold)
    println(str.getSAXString(""))
  }
}
