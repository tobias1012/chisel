package chisel3.simulator

import svsim._
import chisel3.RawModule
import chisel3.testing.HasTestingDirectory
import java.nio.file.Files
import java.io.File

/** Provides a simple API for "ephemeral" invocations (where you don't care about the artifacts after the invocation completes) to
  * simulate Chisel modules. To keep things really simple, `EphemeralSimulator` simulations can only be controlled using the
  * peek/poke API, which provides enough control while hiding some of the lower-level svsim complexity.
  * @example
  * {{{
  * import chisel3.simulator.EphemeralSimulator._
  * ...
  * simulate(new MyChiselModule()) { module => ... }
  * }}}
  */
object EphemeralSimulator extends PeekPokeAPI {

  private val chiselSim = new ChiselSim {}

  def simulate[T <: RawModule](
    module:       => T,
    layerControl: LayerControl.Type = LayerControl.EnableAll
  )(body: (T) => Unit): Unit = {
    implicit val temporary: HasTestingDirectory = HasTestingDirectory.temporary(deleteOnExit = true)
    chiselSim.simulateRaw(
      module,
      settings = Settings.defaultRaw[T].copy(verilogLayers = layerControl)
    )(body)
  }

}
