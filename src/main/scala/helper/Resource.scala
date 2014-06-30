/**
 * API for getting build-in resources
 */
package helper

/**
 * @author ShiZhan
 * API for getting build-in resources
 */
object Resource extends Logging {
  def getInputStream(name: String) =
    getClass.getClassLoader.getResourceAsStream(name)

  def getString(name: String) =
    io.Source.fromInputStream(getInputStream(name)).mkString

  def getStringOrElse(name: String, default: String) =
    try { getString(name) }
    catch { case e: Exception => logger.error(e.toString); default }
}