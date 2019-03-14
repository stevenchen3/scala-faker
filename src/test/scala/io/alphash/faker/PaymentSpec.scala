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

class PaymentSpec extends FlatSpec with Matchers {
  val cards = List(
    ("visa",             16, 1, List("4")),
    ("mastercard",       16, 2, List("51", "52", "53", "54", "55")),
    ("american express", 15, 2, List("34", "37")),
    ("discover",         16, 4, List("6011")),
    ("jcb",              16, 4, List("3528", "3538", "3548", "3558", "3568", "3578", "3588")),
    ("diners club",      14, 2, List("36", "38", "39"))
  )

  "Credit card type" should "be one of the given six types" in {
    cards.map(_._1) should contain(Payment().creditCardType.toLowerCase)
  }

  "Credit card number" should "contain only digits" in {
    cards.foreach { c ⇒
      val n = Try(Payment().creditCardNumber(Some(c._1)).toLong).getOrElse(0)
      n should not be(0)
    }
  }

  // scalastyle:off
  it should "be in a valid credit card number format" in {
    val number = Payment().creditCardNumber()
    number.length match {
      case x if x == 14 ⇒ List("36", "38", "39") should contain(number.take(2))
      case x if x == 15 ⇒ List("34", "37") should contain(number.take(2))
      case _ ⇒ number.length should be(16)
    }
  }
  // scalastyle:on

  it should "have predefined length" in {
    cards.foreach { c ⇒
      Payment().creditCardNumber(Some(c._1)).size should be(c._2)
    }
  }

  it should "have predefined prefix" in {
    cards.foreach { c ⇒
      c._4 should contain (Payment().creditCardNumber(Some(c._1)).take(c._3))
    }
  }

  it should "throw Exception if invalid credit card type is given" in {
    a[Exception] should be thrownBy {
      Payment().creditCardNumber(Some("foobar"))
    }
  }
}
