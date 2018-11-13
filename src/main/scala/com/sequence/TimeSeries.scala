package com.sequence

import breeze.linalg.DenseMatrix

class TimeSeries[K](val index: Array[Int], val data: DenseMatrix[K],
                    val keys: Array[K])//(implicit val kClassTag: ClassTag[K])
  extends Serializable {


}

