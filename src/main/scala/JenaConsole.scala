/**
 * @author ShiZhan
 * @year 2014
 * @name JenaConsole Project
 */
object Trigram {
  import kernel.{ Console, Engine }

  val usage = """usage: JenaConsole
 no argument     enter console
 -h              print this message
 -v              show program version
 -i [MODEL ...]            import model(s)
 -c [MODEL ...]            combine model(s)
 -q [SPARQL ...]           http://www.w3.org/TR/sparql11-query/
 -u [SPARQL ...]           http://www.w3.org/TR/sparql11-update/
 -R [MODEL] <Rules ...>    Infer over [MODEL] using <Rules ...>"""
  val incorrectArgs = "Incorrect parameters, see help (JenaConsole -h)."

  def main(args: Array[String]) = {
    println("Portable Jena Console")
    args.toList match {
      case Nil => Console.run
      case "-h" :: Nil => println(usage)
      case "-v" :: Nil => println(helper.Version.get)
      case "-i" :: modelFNs => modelFNs.foreach(Engine.tdbloader)
      case "-c" :: modelFNs => if (modelFNs.length > 1) Engine.combine(modelFNs)
      case "-q" :: qArgs => Engine.doQuery(qArgs)
      case "-u" :: uArgs => Engine.doUpdate(uArgs)
      case "-R" :: modelFN :: ruleFNs => Engine.infer(modelFN, ruleFNs)
      case _ => println(incorrectArgs)
    }
  }
}
