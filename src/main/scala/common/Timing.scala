/**
 * Gauge operations with console feedback
 */
package common

/**
 * @author ShiZhan
 * Gauge operations with console feedback
 * E.g.: to process a large amount of files
 */
object Timing {
  def timedOp[T](op: () => T) = {
    val t1 = compat.Platform.currentTime
    val result = op()
    val t2 = compat.Platform.currentTime
    (result, t2 - t1)
  }
}