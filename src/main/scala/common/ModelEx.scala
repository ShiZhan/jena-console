/**
 * Model operations DSL
 */
package common

/**
 * @author ShiZhan
 * Model extended operation
 * 1. load:      use file input stream API for better compatibility
 * 2. join:      combine all models in Seq
 *               combine all models in Seq to base model
 * 3. asModels:  load model from given file name list
 * 4. write:     encapsulate API for model writing in given format
 *               with given file name or instance as "UTF-8"
 */
object ModelEx extends helper.Logging {
  import java.io.File
  import scala.collection.JavaConversions._
  import com.hp.hpl.jena.rdf.model.{ ModelFactory, Model }
  import com.hp.hpl.jena.util.FileManager
  import FileEx._

  def createDefaultModel = ModelFactory.createDefaultModel

  def load(fileName: String) = {
    try {
      FileManager.get.loadModel(fileName, "N3")
    } catch {
      case e: Exception => logger.error(fileName + ": " + e.toString); createDefaultModel
    }
  }

  implicit class ModelFileOps(fileNames: Seq[String]) {
    def asModels = fileNames.map(load)
  }

  implicit class ModelOps(m: Model) {
    def store(fileName: String) = {
      val fos = fileName.toFile.getWriter("UTF-8")
      m.write(fos)
      fos.close
      logger.info("[{}] triples written to [{}]", m.size, fileName)
    }

    def store(file: File) = {
      val fos = file.getWriter("UTF-8")
      m.write(fos)
      fos.close
      logger.info("[{}] triples written to [{}]", m.size, file.getAbsolutePath)
    }

    def store(fileName: String, format: String) = {
      val fos = fileName.toFile.getWriter("UTF-8")
      m.write(fos, format)
      fos.close
      logger.info("[{}] triples written to [{}]", m.size, fileName)
    }

    def store(file: File, format: String) = {
      val fos = file.getWriter("UTF-8")
      m.write(fos, format)
      fos.close
      logger.info("[{}] triples written to [{}]", m.size, file.getAbsolutePath)
    }
  }

  implicit class ModelSeqOps(models: Seq[Model]) {
    def join = models.reduceLeft(_ union _)
    def join(baseModel: Model) = (baseModel /: models) { _ union _ }
  }
}