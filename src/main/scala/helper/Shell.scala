/**
 * Run shell commands
 */
package helper

/**
 * @author ShiZhan
 * Run shell commands and return output string iterator or exception
 */
object Shell {
  import java.io.{ BufferedReader, InputStreamReader }

  private val runtime = Runtime.getRuntime

  def run(cmd: String) = {
    val p = runtime.exec(cmd)
    val input = new BufferedReader(new InputStreamReader(p.getInputStream))
    Iterator.continually(input.readLine).takeWhile(_ != null)
  }
}