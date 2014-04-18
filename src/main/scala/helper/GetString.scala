/**
 * String reader
 */
package helper

/**
 * @author ShiZhan
 * String reader
 */
object GetString {
  def fromConsole = {
    println("input below, end with Ctrl+E.")
    io.Source.fromInputStream(System.in).takeWhile(_ != 5.toChar).mkString
  }

  def fromFile(fileName: String) = {
    try { io.Source.fromFile(fileName).mkString }
    catch { case e: Exception => println(fileName + " exception: " + e.toString); "" }
  }
}