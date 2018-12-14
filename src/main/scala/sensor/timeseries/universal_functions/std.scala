package sensor.timeseries.universal_functions

import breeze.generic.UFunc
import breeze.linalg.support.CanTraverseValues
import breeze.linalg.support.CanTraverseValues.ValuesVisitor
import org.apache.commons.math3.stat.descriptive.SummaryStatistics

/** Calculate standard deviation of numbers ignoring NaN and Infinity */
object std extends UFunc {
  implicit def standardDeviationFromTraverseDoubles[T](implicit traverse: CanTraverseValues[T, Double]): Impl[T, Double] = {
    new Impl[T, Double] {
      def apply(t: T): Double = {

        val stats = new SummaryStatistics

        traverse.traverse(t, new ValuesVisitor[Double] {
          def visit(a: Double) = {
            if (!a.isNaN && !a.isInfinity) stats.addValue(a)
          }
          def zeros(count: Int, zeroValue: Double) {}
        })

        Math.sqrt(stats.getPopulationVariance)
      }
    }
  }
}
