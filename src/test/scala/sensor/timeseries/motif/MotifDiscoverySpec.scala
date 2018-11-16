package sensor.timeseries.motif

import breeze.linalg.{BitVector, DenseVector}
import org.scalatest.FlatSpec
import org.slf4j.LoggerFactory
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
    println(MotifDiscovery.findMotif(DenseVector(1.0,2.0, 3, 3, 2.2, 1.1, 3,2, 1.0,2.0, 3), 3, 1.5))

  }
}
