package sensor.timeseries.motif

import java.util

import scala.collection.JavaConversions._
import breeze.linalg.DenseMatrix
import net.seninp.jmotif.sax.{NumerosityReductionStrategy, SAXProcessor}

object Bitmap {
  /**
    *
    * @param sequence input sequence
    * @param saxWindowSize determine the size of sax words in original sequence
    * @param saxPaaSize how many alphabet in each word
    * @param saxAlphabetSize number of alphbets
    * @param saxNRStrategy numerosity reduction strategy
    * @param saxNormTreshold threshold used when doing z-normalization
    * @param shingleSize number of characters used when producing bitmap
    * @return
    */
  def squence2Bitmap(sequence: Array[Double],
                     saxWindowSize: Int = 30,
                     saxPaaSize: Int = 6,
                     saxAlphabetSize: Int = 4,
                     saxNRStrategy: NumerosityReductionStrategy = NumerosityReductionStrategy.NONE,
                     saxNormTreshold: Double = 0.01,
                     shingleSize: Int = 2) = {
    val sp = new SAXProcessor

    val shingledData = sp.ts2Shingles(sequence, saxWindowSize, saxPaaSize,
      saxAlphabetSize, saxNRStrategy, saxNormTreshold, shingleSize)

    val keys = new util.TreeSet[String](shingledData.keySet).toList
    val keysWithIndex = keys.zipWithIndex

    val dimension = Math.pow(2, shingleSize).toInt
    val heatmap = DenseMatrix.zeros[Double](dimension, dimension)

    for (entry <- keysWithIndex) yield {
      val value = shingledData.get(entry._1).toDouble
      heatmap(entry._2 / dimension, entry._2 % dimension) = value
    }

    ((0 until heatmap.rows).map { i =>
      heatmap(i, ::).t.toArray
    }.toArray, keys)
  }

}
