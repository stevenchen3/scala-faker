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
