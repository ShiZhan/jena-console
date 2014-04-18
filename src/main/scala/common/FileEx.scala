/**
 * Additional File methods
 */
package common

/**
 * @author ShiZhan
 * Additional File methods
 */
object FileEx {
  import java.io._

  implicit class FileName(name: String) {
    def toFile = new File(name)
    def setExt(ext: String) =
      if (name.split('.').last == ext) name else name + '.' + ext
  }

  implicit class FileOps(file: File) {
    def getWriter =
      new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))

    def getReader =
      new BufferedReader(new InputStreamReader(new FileInputStream(file)))

    def getWriter(coding: String) =
      new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), coding))

    def getReader(coding: String) =
      new BufferedReader(new InputStreamReader(new FileInputStream(file), coding))
  }
}
