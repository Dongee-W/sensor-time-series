package sensor.timeseries.example

import java.util

import breeze.linalg.DenseMatrix

import scala.collection.JavaConversions._
import net.seninp.jmotif.sax.SAXProcessor
import net.seninp.jmotif.sax.bitmap.BitmapParameters
import sensor.timeseries.dataset.NaiveTimeSeries

object BitmapExample {
  def main(args: Array[String]): Unit = {

    val sp = new SAXProcessor

    val series = NaiveTimeSeries.increasingWithNoise(500).toArray

    /** Default parameters:
      * SAX_WINDOW_SIZE = 30
      * SAX_PAA_SIZE = 6
      * SAX_ALPHABET_SIZE = 4
      * SAX_NR_STRATEGY = NumerosityReductionStrategy.NONE
      * SAX_NORM_THRESHOLD = 0.01
      * SHINGLE_SIZE = 2
      */

    val SHINGLE_SIZE = 3

    val shingledData = sp.ts2Shingles(series, BitmapParameters.SAX_WINDOW_SIZE,
      BitmapParameters.SAX_PAA_SIZE, BitmapParameters.SAX_ALPHABET_SIZE,
      BitmapParameters.SAX_NR_STRATEGY, BitmapParameters.SAX_NORM_THRESHOLD,
      SHINGLE_SIZE)
    println(shingledData)

    val keys = new util.TreeSet[String](shingledData.keySet).toList
    val keysWithIndex = keys.zipWithIndex

    val dimension = Math.pow(2, SHINGLE_SIZE).toInt
    val heatmap = DenseMatrix.zeros[Double](dimension, dimension)

    for (entry <- keysWithIndex) yield {
      val value = shingledData.get(entry._1).toDouble
      heatmap(entry._2 / dimension, entry._2 % dimension) = value
    }

    // Get total size of the heatmap. For SHINGLE_SIZE = 3, there are 64 combinations.
    // shingledData.size

    println(heatmap)
  }

}
