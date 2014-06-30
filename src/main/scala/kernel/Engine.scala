/**
 * Jena Console engine
 */
package kernel

/**
 * @author ShiZhan
 * Program engine
 */
object Engine {
  import helper.Config.{ JCROOT, JCDATA }
  import helper.{ GetString, Platform, Version }
  import common.FileEx._
  import common.ModelEx._
  import common.Timing._
  import Infer._

  private val JCVER = Version.get
  val status = s"""
Jena Console:$JCVER
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
      val (result, t) = { () => store.queryAny(sparql) }.runWithTimer
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
      val (result, t) = { () => store.update(sparql) }.runWithTimer
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
    if (Nil == ruleFNs)
      data.infer(defaultRules).validateAndSave(modelFN + "-infered.n3", "N3")
    else
      (data /: ruleFNs) { (baseModel, ruleFN) =>
        val ruleList = ruleFN.toRuleList
        val ruleName = ruleFN.toFile.getName
        val (result, t) = { () => baseModel.infer(ruleList) }.runWithTimer
        println("[%s] Infered in %d milliseconds".format(ruleName, t))
        result.validateAndSave(modelFN + "-" + ruleName + ".n3", "N3")
        baseModel union result.getDeductionsModel
      } store (modelFN + "-final.n3", "N3")
  }

  def combine(files: List[String]) =
    files.asModels.join.store(files.head + "-combined.n3", "N3")

  def runShell(cArgs: List[String]) = {
    import sys.process._
    cArgs.toSeq.lines_!.foreach(println)
  }
}