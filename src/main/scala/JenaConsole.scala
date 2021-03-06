/**
 * @author ShiZhan
 * @year 2014
 * @name JenaConsole Project
 */
object JenaConsole extends App {
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

  println("Portable Jena Console")
  args.toList match {
    case Nil => Console.run
    case "-h" :: Nil => println(usage)
    case "-v" :: Nil => println(helper.Version.get)
    case "-i" :: fileNames => fileNames.foreach(Engine.tdbloader)
    case "-c" :: fileNames => Engine.combine(fileNames)
    case "-q" :: arguments => Engine.doQuery(arguments)
    case "-u" :: arguments => Engine.doUpdate(arguments)
    case "-R" :: model :: rules => Engine.infer(model, rules)
    case _ => println(incorrectArgs)
  }
}