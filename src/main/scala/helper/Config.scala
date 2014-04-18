/**
 *
 */
package helper

/**
 * @author ShiZhan
 * configuration information
 * JCROOT: program root
 * JCDATA: program data
 *
 * NOTE:
 * if above variables are not defined/exported, current directory will be used.
 */
object Config {
  import java.io.File
  import scala.util.Properties.{ envOrElse, userDir }

  val _PWD = userDir
  val jcRoot = new File(envOrElse("JC_ROOT", _PWD))
  val JCROOT = jcRoot.getAbsolutePath
  val jcData = new File(envOrElse("JC_DATA", _PWD) + "/.data")
  val JCDATA = jcData.getAbsolutePath

  if (!jcData.exists) jcData.mkdir
}
