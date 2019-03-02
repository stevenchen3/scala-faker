/*
 * Copyright (c) 2019, Steven Chen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
