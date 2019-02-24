package io.alphash.faker

import org.scalatest._

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

  "A credit card number" should "have predefined length" in {
    cards.foreach { c ⇒
      Payment().creditCardNumber(Some(c._1)).size should be(c._2)
    }
  }

  it should "have predefined prefix" in {
    cards.foreach { c ⇒
      c._4 should contain (Payment().creditCardNumber(Some(c._1)).take(c._3))
    }
  }
}