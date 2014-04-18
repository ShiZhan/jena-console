/**
 * TriGraM engine
 */
package kernel

/**
 * @author ShiZhan
 * Program engine
 */
object Engine {
  import helper.Config.{ JCROOT, JCDATA }
  import helper.{ GetString, Platform, Version }
  import common.ModelEx._
  import common.Gauge.timedOp
  import Infer._

  private val JCVER = Version.get
  val status = s"""
TriGraM:     $JCVER
  code:      $JCROOT
  data:      $JCDATA""" + Platform.BRIEFING

  def tdbloader(modelFile: String) =
    try { tdb.tdbloader.main("--loc=" + JCDATA, modelFile) }
    catch { case e: Exception => println(e) }

  def tdbinfo =
    try { tdb.tdbstats.main("--loc=" + JCDATA) }
    catch { case e: Exception => println(e) }

  def tdbquery(queryFile: String) =
    try { tdb.tdbquery.main("--loc=" + JCDATA, "--query=" + queryFile) }
    catch { case e: Exception => println(e) }

  def tdbupdate(updateFile: String) =
    try { tdb.tdbupdate.main("--loc=" + JCDATA, "--update=" + updateFile) }
    catch { case e: Exception => println(e) }

  def infer(rdfs: String, data: String) =
    try { riotcmd.infer.main("--rdfs=" + rdfs, data) }
    catch { case e: Exception => println(e) }

  private val store = new Store(JCDATA)

  def shutdown = store.close

  def timedQuery(sparql: String) = if ("" != sparql) {
    try {
      val (result, t) = timedOp { () => store.queryAny(sparql) }
      println(result)
      println("Query executed in %d milliseconds".format(t))
    } catch {
      case e: Exception => e.printStackTrace
    }
  }
  def doQueryFromConsole = timedQuery(GetString.fromConsole)
  def doQueryFromFile(fileName: String) = timedQuery(GetString.fromFile(fileName))
  def doQuery(qArgs: List[String]) =
    if (Nil == qArgs) doQueryFromConsole else qArgs.foreach(doQueryFromFile)

  def timedUpdate(sparql: String) = {
    try {
      val (result, t) = timedOp { () => store.update(sparql) }
      println("Update Executed in %d milliseconds".format(t))
    } catch {
      case e: Exception => e.printStackTrace
    }
  }
  def doUpdateFromConsole = timedUpdate(GetString.fromConsole)
  def doUpdateFromFile(fileName: String) = timedUpdate(GetString.fromFile(fileName))
  def doUpdate(uArgs: List[String]) =
    if (Nil == uArgs) doUpdateFromConsole else uArgs.foreach(doUpdateFromFile)

  def infer(modelFN: String, ruleFNs: List[String]) = {
    val data = load(modelFN)
    def output(suffix: String) = s"$modelFN-$suffix.n3"
    if (Nil == ruleFNs) {
      data.infer(defaultRules).validateAndSave(output("deduction"), "N3")
    } else {
      (data /: ruleFNs) { (baseModel, ruleFN) =>
        val rules = parseRules(ruleFN)
        val (result, t) = timedOp { () => baseModel.infer(rules) }
        println("Inferring Executed in %d milliseconds".format(t))
        result.validateAndSave(output(new java.io.File(ruleFN).getName), "N3")
        baseModel union result.getDeductionsModel
      } store (output("final"), "N3")
    }
  }

  def combine(files: List[String]) =
    files.asModels.join.store(files.head + "-combined.n3", "N3")
}