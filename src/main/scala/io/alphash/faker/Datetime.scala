package io.alphash.faker

import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, LocalTime, Seconds}

import scala.util.{Random, Try}

// See 'src/main/scala/com/stratio/khermes/helpers/faker/generators/DatetimeGenerator.scala'
// at https://github.com/Stratio/khermes/blob/master
class Datetime {
  def datetime(from: DateTime, to: DateTime, format: Option[String] = None): String = {
    assert(to.getMillis > from.getMillis, throw new Exception(s"$to must be greater than $from"))
    val diff = Seconds.secondsBetween(from, to).getSeconds
    val randomDate = new Random(System.nanoTime)
    val date: DateTime = from.plusSeconds(randomDate.nextInt(diff.toInt))
    format match {
      case Some(f) ⇒
        Try {
          DateTimeFormat.forPattern(f).print(date)
        }.getOrElse(throw new Exception(s"Invalid DateTimeFormat"))
      case None ⇒ date.toString
    }
  }

  def time: String = new LocalTime(
    Random.nextInt(24), Random.nextInt(60), Random.nextInt(60), Random.nextInt(1000)
  ).toString
}

object Datetime {
  def apply(): Datetime = new Datetime()
}
