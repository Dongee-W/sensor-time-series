package sensor.timeseries.motif

import breeze.linalg.{BitVector, DenseVector, argmax}
import org.scalatest.FlatSpec
import org.slf4j.LoggerFactory
import sensor.timeseries.dataset.NaiveTimeSeries
import sensor.timeseries.similarity_search.MatrixProfileI

class MotifDiscoverySpec extends FlatSpec {
  val logger = LoggerFactory.getLogger("test.logger")
  "The removeTrivial function" should "remove trivial match from match vector" in {
    val mv = BitVector.ones(10)
    mv(4) = false
    mv(7) = false
    println(mv)
    println(MotifDiscovery.removeTrivial(mv, 5))
  }

  "The findMotif function" should "find the best motif in a sequence" in {
    val series = NaiveTimeSeries.simpleRepeatingPatterns
    println(argmax(MotifDiscovery.findMotif(series, 10, 0.1)))

  }
}
