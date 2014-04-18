/**
 * check platform information
 */
package helper

/**
 * @author ShiZhan
 * gather platform information
 */
object Platform {
  import com.hp.hpl.jena.Jena.{ VERSION => JENAVER, BUILD_DATE => JENABUILD }
  import com.hp.hpl.jena.tdb.TDB.{ VERSION => TDBVER, BUILD_DATE => TDBBUILD }
  import JVMstates.{ MEMFREE, MEMUSED, MEMTOTAL, MEMMAX }

  val HOSTNAME = java.net.InetAddress.getLocalHost.getHostName
  val OS = System.getProperty("os.name")
  val isWindows = OS.startsWith("Windows")
  val JAVAVER = System.getProperty("java.version")
  val SCALAVER = scala.util.Properties.versionMsg
  val BRIEFING = {
    s"""
Jena core:   $JENAVER $JENABUILD
Jena TDB:    $TDBVER $TDBBUILD
Scala:       $SCALAVER
Java:        $JAVAVER
  MEM FREE:  $MEMFREE MB
  MEM USED:  $MEMUSED MB
  MEM TOTAL: $MEMTOTAL MB
  MEM MAX:   $MEMMAX MB
OS:          $OS
HOSTNAME:    $HOSTNAME
"""
  }
}