/**
 * Timer
 */
package common

/**
 * @author ShiZhan
 * Timer
 */
object Timing {
  implicit class TimerWrapper[T](op: () => T) {
    def runWithTimer = {
      val t1 = compat.Platform.currentTime
      val result = op()
      val t2 = compat.Platform.currentTime
      (result, t2 - t1)
    }
  }
}