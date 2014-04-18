/**
 * JVM states
 */
package helper

/**
 * @author ShiZhan
 * Get JVM states information
 */
object JVMstates {
  private val mb = 1024 * 1024

  private val runtime = Runtime.getRuntime

  def MEMFREE = runtime.freeMemory / mb
  def MEMUSED = (runtime.totalMemory - runtime.freeMemory) / mb
  def MEMTOTAL = runtime.totalMemory / mb
  def MEMMAX = runtime.maxMemory / mb
}