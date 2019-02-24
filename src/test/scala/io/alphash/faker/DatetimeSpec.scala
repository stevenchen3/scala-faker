package io.alphash.faker

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalatest.{FlatSpec, Matchers}

class DatetimeSpec extends FlatSpec with Matchers {

  "Datetime.datetime" should "generate a random date between two dates" in {
    val startDate = new DateTime("1970-1-1")
    val endDate = new DateTime("2017-1-1")
    val randomDateString = Datetime().datetime(startDate, endDate)
    val randomDate = DateTime.parse(randomDateString)
    assert(startDate.compareTo(randomDate) * randomDate.compareTo(endDate) > 0)
  }

  it should "raise an exception if start Date is greater than end Date" in {
    val startDate = new DateTime("2017-1-1")
    val endDate = new DateTime("1985-1-1")
    an[Exception] should be thrownBy {
      Datetime().datetime(startDate, endDate, None)
    }
  }

  it should "generate a random date in a custom format" in {
    val startDate = new DateTime("1970-1-1")
    val endDate = new DateTime("1985-1-1")
    val randomDateString = Datetime().datetime(startDate, endDate, Option("yyyy-MM-dd"))
    val randomDate = DateTime.parse(randomDateString)
    val expected = DateTimeFormat.forPattern(
      randomDateString.format("yyyy-MM-dd")
    ).print(randomDate)
    randomDateString shouldBe expected
  }

  it should "generate a random date in a complex format" in {
    val startDate = new DateTime("1970-1-1")
    val endDate = new DateTime("1985-1-1")
    val randomDateString =
      Datetime().datetime(startDate, endDate, Option("yyyy-MM-dd'T'HH:mm:ss.SSS"))
    val randomDate = DateTime.parse(randomDateString)
    val expected = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").print(randomDate)
    randomDateString shouldBe expected
  }

  it should "generate a random date with a bad format" in {
    val startDate = new DateTime("1970-1-1")
    val endDate = new DateTime("1985-1-1")
    an[Exception] should be thrownBy {
      Datetime().datetime(startDate, endDate, Option("Invalid format"))
    }
  }

  "Datetime.time" should "return a valid hour (0:0:0.000 - 23:59:59.999)" in {
    val time = Datetime().time
    time shouldBe a[String]
    val timeAsString = time.split(":").toList
    timeAsString(0).toInt should (be >= 0 and be < 24)
    timeAsString(1).toInt should (be >= 0 and be < 60)
    timeAsString(2).split("\\.")(0).toInt should (be >= 0 and be < 60)
    timeAsString(2).split("\\.")(1).toInt should (be >= 0 and be < 1000)
  }
}
