package io.alphash.faker

import scala.util.Random

sealed case class CreditCard(cctype: String, length: Int, prefixes: Seq[Int])

class Payment {
  import Payment._

  def creditCardType: String = getRandomElement[(String, CreditCard)](creditCardsSeq).get._2.cctype

  def creditCardNumber(cct: Option[String] = None): String = {
    val cctype = cct match {
      case Some(t) ⇒ t
      case None    ⇒ creditCardType
    }
    val card = creditCards.get(cctype.toLowerCase).getOrElse(
      throw new Exception(s"Invalid credit card type: ${cctype}")
    )
    val prefix = getRandomElement[Int](card.prefixes).get.toString
    s"${prefix}${(1 to card.length - prefix.size).map(_ ⇒ Random.nextInt(10)).mkString}"
  }
}

object Payment extends Faker {
  lazy val creditCards: Map[String, CreditCard] = Map(
    "visa" -> CreditCard("VISA", 16, Seq(4539, 4556, 4916, 4532, 4929, 40240071, 4485, 4716, 4)),
    "mastercard"       -> CreditCard("MasterCard", 16, Seq(51, 52, 53, 54, 55)),
    "american express" -> CreditCard("American Express", 15, Seq(34, 37)),
    "discover"         -> CreditCard("Discover", 16, Seq(6011)),
    "jcb"              -> CreditCard("JCB", 16, Seq(3528, 3538, 3548, 3558, 3568, 3578, 3588)),
    "diners club"      -> CreditCard("Diners Club", 14, Seq(36, 38, 39))
  )
  private lazy val creditCardsSeq = creditCards.toSeq

  def apply(): Payment = new Payment()
}
