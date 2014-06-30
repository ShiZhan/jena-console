/**
 * Get program version
 */
package helper

/**
 * @author ShiZhan
 * get program version from GIT repository or use build-in string
 */
object Version {
  def get =
    try { Resource.getString("master").trim }
    catch { case e: Exception => "version info inaccessible" }
}