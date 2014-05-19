/**
 * Console command loop
 */
package kernel

/**
 * @author ShiZhan
 * Console command loop
 */
object Console {
  import Engine._

  private val usage = """ [Console Usage]
  help        print this message
  status      show program status
  time        show current time
  query       enter SPARQL/read SPARQL file to do query
  update      enter SPARQL/read SPARQL file to do update
  tdbinfo     Jena tdbinfo utility
  tdbloader   Jena tdbloader utility
  tdbquery    Jena tdbquery utility
  tdbupdate   Jena tdbupdate utility
  ! <command> run shell command
  exit        exit console"""
  private val title = "Input Jena command here ..."
  private val prompt = "# "

  def run: Unit = {
    println(title)
    print(prompt)
    for (line <- io.Source.stdin.getLines) {
      line.split(" ").toList match {
        case "exit" :: Nil => { shutdown; return }
        case "help" :: Nil => println(usage)
        case "status" :: Nil => println(status)
        case "time" :: Nil => println(helper.DateTime.get)
        case "query" :: qArgs => doQuery(qArgs)
        case "update" :: uArgs => doUpdate(uArgs)
        case "tdbinfo" :: Nil => tdbinfo
        case "tdbloader" :: modelFile :: Nil => tdbloader(modelFile)
        case "tdbquery" :: sparqlFile :: Nil => tdbquery(sparqlFile)
        case "tdbupdate" :: sparqlFile :: Nil => tdbupdate(sparqlFile)
        case "!" :: cArgs => runShell(cArgs)
        case "" :: Nil =>
        case _ => println(s"Unrecognized command: [$line]")
      }
      print(prompt)
    }
  }
}