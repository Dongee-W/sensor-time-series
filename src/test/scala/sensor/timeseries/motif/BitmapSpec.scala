package sensor.timeseries.motif

import breeze.linalg.{BitVector, argmax}
import org.scalatest.FlatSpec
import org.slf4j.LoggerFactory
import sensor.timeseries.dataset.NaiveTimeSeries

class BitmapSpec extends FlatSpec {
  val logger = LoggerFactory.getLogger("test.logger")
  "The squence2Bitmap function" should "bitmap representation of the sequence" in {

    val series = NaiveTimeSeries.increasingWithNoise.toArray

    println(Bitmap.squence2Bitmap(series, 3).toList.map(x => x.toList))
  }
}
