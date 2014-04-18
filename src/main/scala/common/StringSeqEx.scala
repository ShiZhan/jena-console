/**
 * Text related functions
 */
package common

import java.io.File
import java.io.PrintWriter

/**
 * @author ShiZhan
 * Text related functions
 * 1. fromFile, toFile: read/write all lines to/from List from/to text file
 * 2. StringWriter: write string to text file by buffered writer
 */
object StringSeqEx {
  import java.io.{ File, PrintWriter }

  def fromFile(fileName: String) =
    try {
      val buf = io.Source.fromFile(new File(fileName))
      val lines = buf.getLines.toList
      buf.close
      lines
    } catch {
      case e: Exception => println(e); List[String]()
    }

  implicit class StringSeqOps[T](lines: Seq[T]) {
    def toFile(fileName: String) =
      try {
        val p = new PrintWriter(new File(fileName))
        lines.foreach(p.println)
        p.close
      } catch {
        case e: Exception => println(e)
      }
  }
}