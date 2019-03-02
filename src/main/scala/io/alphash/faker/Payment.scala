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

import scala.util.Random

sealed case class CreditCard(cctype: String, length: Int, prefixes: Seq[Int])

class Payment {
  import Payment._

  private[this] val RAND_INT_MAX: Int = 10

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
    s"${prefix}${(1 to card.length - prefix.size).map(_ ⇒ Random.nextInt(RAND_INT_MAX)).mkString}"
  }
}

object Payment extends Faker {
  // scalastyle:off
  lazy val creditCards: Map[String, CreditCard] = Map(
    "visa" -> CreditCard("VISA", 16, Seq(4539, 4556, 4916, 4532, 4929, 40240071, 4485, 4716, 4)),
    "mastercard"       -> CreditCard("MasterCard", 16, Seq(51, 52, 53, 54, 55)),
    "american express" -> CreditCard("American Express", 15, Seq(34, 37)),
    "discover"         -> CreditCard("Discover", 16, Seq(6011)),
    "jcb"              -> CreditCard("JCB", 16, Seq(3528, 3538, 3548, 3558, 3568, 3578, 3588)),
    "diners club"      -> CreditCard("Diners Club", 14, Seq(36, 38, 39))
  )
  // scalastyle:on
  private lazy val creditCardsSeq = creditCards.toSeq

  def apply(): Payment = new Payment()
}
