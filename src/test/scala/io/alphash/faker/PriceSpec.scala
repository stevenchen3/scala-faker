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

class PriceSpec extends FlatSpec with Matchers {
  "Currency" should "be one of those in the list" in {
    Price.currencies should contain(Price().currency)
  }

  it should "throw Exception if unsupported currency is given" in {
    a [Exception] should be thrownBy {
      Price(Some("XYZ")).currency
    }
  }

  "A random amount" should "not be negative" in {
    (1 to 100).foreach { _ ⇒
      Price().amount < 0 should be(false)
    }
  }

  it should "optionally have currency prefixed" in {
    val amountWithCurrency = Price().amountWithCurrency.split(" ")
    val currency = amountWithCurrency(0)
    val amount   = amountWithCurrency(1)
    Price.currencies should contain(currency)
    Try(Some(amount.toDouble)).getOrElse(None) should not be(None)
  }

  it should "return the given exact currency and amount" in {
    val amountWithCurrency = Price(Some("SGD"), Some(123.45)).amountWithCurrency.split(" ")
    val currency = amountWithCurrency(0)
    val amount   = amountWithCurrency(1)
    currency should be("SGD")
    amount should be("123.45")
  }

  "A given negative amount" should "throw an Exception" in {
    a [Exception] should be thrownBy {
      Price(amountOpt = Some(-12.34)).amount
    }
  }
}
