package com.sequence.util

sealed trait FillMethod

case class Linear(maxGap: Int = Integer.MAX_VALUE) extends FillMethod
object Nearest extends FillMethod
object Next extends FillMethod
object Previous extends FillMethod
case class Constant(value: Double) extends FillMethod
