// SPDX-License-Identifier: Apache-2.0

package chisel3.util.circt

import scala.language.reflectiveCalls

import chisel3._
import chisel3.experimental.fromStringToStringParam
import chisel3.internal.Builder

class PlusArgsRetBundle[T <: Data](gen: => T) extends Bundle {
  val found = Output(Bool())
  val result = Output(gen)
}

/** Create an intrinsic which generates a verilog \$value\$plusargs.  This returns a
  * value as indicated by the format string and a flag for whether the value
  * was found.
  */
object PlusArgsValue {

  /** Creates an intrinsic which calls \$value\$plusargs.
    *
    * @example {{{
    * b := PlusArgsValue(UInt(32.W), "FOO=%d")
    * b.found
    * b.value
    * }}}
    */
  def apply[T <: Data](gen: T, str: String): PlusArgsRetBundle[T] = {
    val ty = if (gen.isSynthesizable) chiselTypeOf(gen) else gen
    IntrinsicExpr("circt_plusargs_value", new PlusArgsRetBundle(ty), "FORMAT" -> str)()
  }

  /** Creates an intrinsic which calls \$value\$plusargs and returns a default
    * value if the specified pattern is not found.
    *
    * @example {{{
    * v := PlusArgsValue(UInt(32.W), "FOO=%d", 42.U)
    * }}}
    */
  def apply[T <: Data](gen: T, str: String, default: T): T = {
    val result: PlusArgsRetBundle[T] = apply(gen, str)
    Mux(result.found, result.result, default)
  }
}
