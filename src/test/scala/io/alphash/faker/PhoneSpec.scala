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

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

class PhoneSpec extends FlatSpec with Matchers {
  def containsDigitOnly(xs: Seq[Char]): Boolean = xs match {
    case head +: tail ⇒
      if (Try(head.toString.toInt).isSuccess) containsDigitOnly(tail) else false
    case _ ⇒ true
  }

  "Country code" should "be one of those in the given list" in {
    List("1", "65") should contain(Phone().countryCode)
  }

  "Area code" should "be one of those in the given list" in {
    List(None, Some("201"), Some("202")) should contain(Phone().areaCodeOption())
  }

  it should "be correct one for a given country" in {
    Map(Some("65") -> List(None), Some("1") -> List(Some("201"), Some("202"))).foreach { t ⇒
      t._2 should contain(Phone().areaCodeOption(t._1))
    }
  }

  "A phone number" should "have the type of '+(country code) [area code] numbers'" in {
    Map(
      Some("65") -> (13, 4, List("+65 ", "+65 "), List("3", "6")),
      Some("1")  -> (17, 9, List("+1 (201) ", "+1 (202) "), List("2", "3"))
    ).foreach { t ⇒
      val number = Phone().phoneNumber(t._1)
      number.length should be(t._2._1)
      number.split(" ").size should be(3)

      // Check country code
      t._2._3 should contain(number.take(t._2._2))

      val s = number.drop(t._2._2).replaceAll(" ", "").replaceAll("-", "")
      containsDigitOnly(s) should be(true)

      // Check number prefix
      t._2._4.foreach { p ⇒
        t._2._4 should contain(number.drop(t._2._2).take(p.length))
      }
    }
  }

  it should "be either Singapore or US phone number" in {
    val prefixes = List("+65 ", "+1 (")
    (1 to 100).foreach { _ ⇒
      // scalastyle:off
      prefixes should contain(Phone().phoneNumber().take(4))
      // scalastyle:on
    }
  }

  it should "throw Exception if unsupported country code given" in {
    a[Exception] should be thrownBy {
      Phone().phoneNumber(Some("00"))
    }
  }

  // scalastyle:off
  "A toll free phone number" should "have the type of '(888) 123-4567'" in {
    val prefixes = List("(888)", "(800)", "(777)")
    (1 to 100).foreach { _ ⇒
      val tollfree = Phone().tollFreePhoneNumber
      tollfree.size should be(14)
      val digits = tollfree.split(" ")
      prefixes should contain(digits(0))
      digits(1).split("-").size should be(2)
      digits(1).split("-")(0).size should be(3)
    }
  }
  // scalastyle:on

  // scalastyle:off
  "A E164 phone number" should "have the type of '+71234567891'" in {
    val number = Phone().e164PhoneNumber(Some("65"))
    number.size should be(11)
    number.startsWith("+") should be(true)
    List("+653", "+656") should contain(number.take(4))
  }
  // scalastyle:on

  it should "have the predefined type '+653', '+656', '+1201', or '+1202'" in {
    val prefixes = List("+653", "+656", "+1201", "+1202")
    (1 to 100).foreach { _ ⇒
      val number = Phone().e164PhoneNumber()
      prefixes.filter(p ⇒ number.startsWith(p)).size should be(1)
      (number.size == 11 || number.size == 12) should be(true)
    }
  }

  it should "throw Exception if unsupported country code given" in {
    a[Exception] should be thrownBy {
      Phone().e164PhoneNumber(Some("00"))
    }
  }
}
