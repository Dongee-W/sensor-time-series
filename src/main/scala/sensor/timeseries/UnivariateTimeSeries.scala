package sensor.timeseries

import breeze.linalg.{*, BitVector, DenseMatrix, DenseVector, Vector}
import sensor.timeseries.util._
import sensor.timeseries.universal_functions.{std, mean}

object UnivariateTimeSeries {


  def fill(values: Array[Double], fillMethod: FillMethod) = {
    fillMethod match {
      case Linear(maxGap) => fillLinear(values: Array[Double], maxGap)
      case Constant(filler) => fillValue(values: Array[Double], filler)
      case Previous => fillPrevious(values)
      case Next => fillNext(values)
      case Nearest => fillNearest(values)
    }
  }

  def fillLinear(values: Array[Double], maxGap: Int): Array[Double] = {
    fillLinear(new DenseVector(values), maxGap).toArray
  }

  def fillLinear(values: Vector[Double], maxGap: Int): DenseVector[Double] = {
    val result = values.copy.toArray
    var i = 1
    while (i < result.length - 1) {
      val rangeStart = i
      var gap = 0
      while (i < result.length - 1 && result(i).isNaN) {
        i += 1
        gap += 1
      }
      val before = result(rangeStart - 1)
      val after = result(i)
      if (gap <= maxGap && i != rangeStart && !before.isNaN && !after.isNaN) {
        val increment = (after - before) / (i - (rangeStart - 1))
        for (j <- rangeStart until i) {
          result(j) = result(j - 1) + increment
        }
      }
      i += 1
    }
    new DenseVector(result)
  }

  /**
    * Replace all NaNs with a specific value
    */
  def fillValue(values: Array[Double], filler: Double): Array[Double] = {
    fillValue(new DenseVector(values), filler).toArray
  }

  /**
    * Replace all NaNs with a specific value
    */
  def fillValue(values: Vector[Double], filler: Double): DenseVector[Double] = {
    val result = values.copy.toArray
    var i = 0
    while (i < result.size) {
      if (result(i).isNaN) result(i) = filler
      i += 1
    }
    new DenseVector(result)
  }
  /**
    * Replace all NaNs with a specific value
    */
  def replaceInfinity(values: Array[Double], replacer: Double): Array[Double] = {
    replaceInfinity(new DenseVector(values), replacer).toArray
  }

  /**
    * Replace all NaNs with a specific value
    */
  def replaceInfinity(values: Vector[Double], replacer: Double): DenseVector[Double] = {
    val result = values.copy.toArray
    var i = 0
    while (i < result.size) {
      if (result(i).isInfinity) result(i) = replacer
      i += 1
    }
    new DenseVector(result)
  }

  def fillPrevious(values: Array[Double]): Array[Double] = {
    fillPrevious(new DenseVector(values)).toArray
  }

  /**
    * fills in NaN with the previously available not NaN, scanning from left to right.
    * 1 NaN NaN 2 Nan -> 1 1 1 2 2
    */
  def fillPrevious(values: Vector[Double]): DenseVector[Double] = {
    val result = values.copy.toArray
    var filler = Double.NaN // initial value, maintains invariant
    var i = 0
    while (i < result.length) {
      filler = if (result(i).isNaN) filler else result(i)
      result(i) = filler
      i += 1
    }
    new DenseVector(result)
  }

  def fillNext(values: Array[Double]): Array[Double] = {
    fillNext(new DenseVector(values)).toArray
  }

  /**
    * fills in NaN with the next available not NaN, scanning from right to left.
    * 1 NaN NaN 2 Nan -> 1 2 2 2 NaN
    */
  def fillNext(values: Vector[Double]): DenseVector[Double] = {
    val result = values.copy.toArray
    var filler = Double.NaN // initial value, maintains invariant
    var i = result.length - 1
    while (i >= 0) {
      filler = if (result(i).isNaN) filler else result(i)
      result(i) = filler
      i -= 1
    }
    new DenseVector(result)
  }

  def fillNearest(values: Array[Double]): Array[Double] = {
    fillNearest(new DenseVector(values)).toArray
  }

  def fillNearest(values: Vector[Double]): DenseVector[Double] = {
    val result = values.copy.toArray
    var lastExisting = -1
    var nextExisting = -1
    var i = 1
    while (i < result.length) {
      if (result(i).isNaN) {
        if (nextExisting < i) {
          nextExisting = i + 1
          while (nextExisting < result.length && result(nextExisting).isNaN) {
            nextExisting += 1
          }
        }

        if (lastExisting < 0 && nextExisting >= result.size) {
          throw new IllegalArgumentException("Input is all NaNs!")
        } else if (nextExisting >= result.size || // TODO: check this
          (lastExisting >= 0 && i - lastExisting < nextExisting - i)) {
          result(i) = result(lastExisting)
        } else {
          result(i) = result(nextExisting)
        }
      } else {
        lastExisting = i
      }
      i += 1
    }
    new DenseVector(result)
  }

  def numNaN(ts: Vector[Double]): Int = {
    var i = 0
    var acc = 0
    while (i < ts.length) {
      if (java.lang.Double.isNaN(ts(i))) {
        acc += 1
      }
      i += 1
    }
    acc
  }

  def numNaN(ts: Array[Double]): Int = {
    var i = 0
    var acc = 0
    while (i < ts.length) {
      if (java.lang.Double.isNaN(ts(i))) {
        acc += 1
      }
      i += 1
    }
    acc
  }

  def numPositiveInfinity(ts: Vector[Double]): Int = {
    var i = 0
    var acc = 0
    while (i < ts.length) {
      if (ts(i).isPosInfinity) {
        acc += 1
      }
      i += 1
    }
    acc
  }

  def numPositiveInfinity(ts: Array[Double]): Int = {
    var i = 0
    var acc = 0
    while (i < ts.length) {
      if (ts(i).isPosInfinity) {
        acc += 1
      }
      i += 1
    }
    acc
  }

  def numNegativeInfinity(ts: Vector[Double]): Int = {
    var i = 0
    var acc = 0
    while (i < ts.length) {
      if (ts(i).isNegInfinity) {
        acc += 1
      }
      i += 1
    }
    acc
  }

  def numNegativeInfinity(ts: Array[Double]): Int = {
    var i = 0
    var acc = 0
    while (i < ts.length) {
      if (ts(i).isNegInfinity) {
        acc += 1
      }
      i += 1
    }
    acc
  }

  def numInfinity(ts: Vector[Double]): Int = {
    var i = 0
    var acc = 0
    while (i < ts.length) {
      if (ts(i).isInfinity) {
        acc += 1
      }
      i += 1
    }
    acc
  }

  def numInfinity(ts: Array[Double]): Int = {
    var i = 0
    var acc = 0
    while (i < ts.length) {
      if (ts(i).isInfinity) {
        acc += 1
      }
      i += 1
    }
    acc
  }

  def isNaN(seq: Vector[Double]) = {
    var i = 0
    val bv = BitVector.zeros(seq.length)
    while (i < seq.size) {
      if (seq(i).isNaN) {
        bv.update(i, true)
      }
      i += 1
    }
    bv
  }

  def isInfinity(seq: Vector[Double]): BitVector = {
    var i = 0
    val bv = BitVector.zeros(seq.length)
    while (i < seq.size) {
      if (seq(i).isInfinity) {
        bv.update(i, true)
      }
      i += 1
    }
    bv
  }

  def firstNotNaN(seq: Vector[Double]): Int = {
    var i = 0
    while (i < seq.size) {
      if (!java.lang.Double.isNaN(seq(i))) {
        return i
      }
      i += 1
    }
    i
  }

  def lastNotNaN(ts: Vector[Double]): Int = {
    var i = ts.size - 1
    while (i >= 0) {
      if (!java.lang.Double.isNaN(ts(i))) {
        return i
      }
      i -= 1
    }
    i
  }

  def slidingWindow(x: Vector[Double], window: Int): DenseMatrix[Double] = {
    val numX = x.size
    val numRows = numX - window + 1
    val numCols = window
    val slidingMat = DenseMatrix.zeros[Double](numRows, numCols)
    for (r <- 0 until numRows) {
      for (index <- 0 until window) {
        slidingMat(r, index) = x(r + index)
      }
    }
    slidingMat
  }

  def slidingWindowReduction[T](x: Vector[Double], window: Int,
                                f: (DenseVector[Double]) => Double) = {
    val numX = x.size
    val length = numX - window + 1

    val slidingVec = DenseVector.zeros[Double](length)
    for (r <- 0 until length) {
      slidingVec(r) = f(x(r until r + window).toDenseVector)
    }
    slidingVec
  }

  /** Transform sequence to unit standard deviation and zero mean
    * (NaN and Infinity)
    * @param s sequence
    * @param withStd whether to scale with standard deviation
    * @param withMean whether to subtract mean from data
    * @param stdThreshold if standard deviation is small the stdThreshold, the sequence will not be scaled
    */
  def standardScale(s: Array[Double], withStd: Boolean = true, withMean: Boolean = true, stdThreshold: Double = 0.5) = {
    val v = DenseVector(s)
    val m = mean(v)
    val scaler = std(v)

    val toScale =  withStd && scaler > stdThreshold
    val result =
      if (withMean && toScale) (v - m) / scaler
      else if (withMean && !toScale) v - m
      else if (!withMean && toScale)  v / scaler
      else if (!withMean && !withStd) v
      else v
    result.toArray
  }

  def standardScale(s: DenseVector[Double], withStd: Boolean = true, withMean: Boolean = true, stdThreshold: Double = 0.5) = {
    val m = mean(s)
    val scaler = std(s)

    val toScale =  withStd && scaler > stdThreshold
    val result =
      if (withMean && toScale) (s- m) / scaler
      else if (withMean && !toScale) s - m
      else if (!withMean && toScale)  s / scaler
      else if (!withMean && !withStd) s
      else s

    result
  }

}
