// SPDX-License-Identifier: Apache-2.0

package chisel3.simulator

import chisel3.testing.scalatest.{HasConfigMap, TestingDirectory}
import org.scalatest.TestSuite

package object scalatest {

  /** A trait that provides ChiselSim APIs and integration with Scalatest.
    *
    * Example usage:
    * {{{
    * import chisel3.simulator.scalatestChiselSim
    * import org.scalatest.funspec.AnyFunSpec
    * import org.scalatest.matches.should.Matchers
    *
    * class Foo extends AnyFunSpec with Matchers with ChiselSim {
    *   /** This has access to all ChiselSim APIs like `simulate`, `peek`, and `poke`. */
    * }
    * }}}
    *
    * @see [[chisel3.simulator.ChiselSim]]
    */
  trait ChiselSim
      extends HasCliOptions
      with HasConfigMap
      with Cli.ChiselOpts
      with Cli.EmitVcd
      with Cli.FirtoolOpts
      with TestingDirectory
      with chisel3.simulator.ChiselSim {
    self: TestSuite =>
  }

}
