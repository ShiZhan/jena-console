/**
 * XML Schema dateTime
 */
package helper

/**
 * @author ShiZhan
 * according to:
 * http://www.w3.org/TR/xmlschema-2/#dateTime
 * http://www.w3.org/TR/xmlschema11-2/#dateTimeStamp
 */
object DateTime {
  import java.util.{ Calendar, Date }
  import com.hp.hpl.jena.datatypes.xsd.XSDDateTime

  private def _c = Calendar.getInstance
  private def _x = (c: Calendar) => new XSDDateTime(c).toString

  def get = { val c = _c; _x(c) }
  def get(d: Date) = { val c = _c; c.setTime(d); _x(c) }
  def get(i: Long) = { val c = _c; c.setTimeInMillis(i); _x(c) }
}