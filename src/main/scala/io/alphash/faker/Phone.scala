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

import scala.annotation.tailrec
import scala.io.Source
import scala.util.Random

sealed case class PhoneFormat(
  countryCode: String,
  length: Int,
  areaCodes: Option[Seq[String]] = None,
  prefixes: Seq[String],
  format: String
)
sealed case class PhoneModel(formats: Seq[PhoneFormat])

class Phone (models: Map[String, PhoneFormat]) {
  import Phone._

  private[this] def format(template: String, numbers: Seq[String]): String = {
    @tailrec
    def loop(xs: Seq[String], acc: String): String = xs match {
      case head +: tail ⇒ loop(tail, acc.replaceFirst("X", head))
      case _ ⇒ acc
    }
    loop(numbers, template)
  }

  private[this] def getPhoneNumber(format: PhoneFormat, enableE164: Boolean = false): String = {
    val cc  = format.countryCode
    val rac = areaCodeOption(Some(cc)).getOrElse("")
    val ac  = if(enableE164) removeHeadingZero(rac) else rac
    val prefix = getRandomElement[String](format.prefixes).get
    // scalastyle:off
    val tail = (1 to format.length - prefix.length).map(_ ⇒ Random.nextInt(10)).mkString
    // scalastyle:on
    s"${cc}${ac}${prefix}${tail}"
  }

  private[this] def randomPhoneFormat = getRandomElement[PhoneFormat](models.map(_._2).toSeq).get

  private[this] def removeHeadingZero(s: String): String =
    if (s.startsWith("0")) removeHeadingZero(s.drop(1).mkString) else s

  // It generates phone numbers of type: "+65 9012-3456" or "+1 (201) 123-4567"
  def phoneNumber(countryCode: Option[String] = None): String = countryCode match {
    case Some(cc) ⇒ models.get(cc).map { phoneFormat ⇒
      format(phoneFormat.format, getPhoneNumber(phoneFormat).map(_.toString))
    }.getOrElse(throw new Exception(s"Unsupported country code: $cc"))
    case None ⇒
      val phoneFormat = randomPhoneFormat
      format(phoneFormat.format, getPhoneNumber(phoneFormat).map(_.toString))
  }

  def countryCode: String = getRandomElement[String](models.map(_._1).toSeq).get

  def areaCodeOption(countryCode: Option[String] = None): Option[String] = countryCode match {
    case Some(cc) ⇒
      for {
        pf <- models.get(cc)
        ac <- pf.areaCodes
      } yield getRandomElement[String](ac).get
    case None ⇒ randomPhoneFormat.areaCodes.map(ac ⇒ getRandomElement[String](ac).get)
  }

  // It generates phone numbers of type: "(800) 123-4567"
  def tollFreePhoneNumber: String = {
    // scalastyle:off
    val starts = List("777", "800", "888")
    val xs = (1 to 7).map(_ ⇒ Random.nextInt(10))
    s"(${starts(Random.nextInt(2))}) ${xs.slice(0, 3).mkString}-${xs.slice(3, 7).mkString}"
    // scalastyle:on
  }

  // It generates phone numbers of type: "+71234567891"
  def e164PhoneNumber(countryCode: Option[String] = None): String = countryCode match {
    case Some(cc) ⇒ models.get(cc).map { f ⇒
      s"+${getPhoneNumber(f, true)}"
    }.getOrElse(throw new Exception(s"Unsupported country code: $cc"))
    case None ⇒ s"+${getPhoneNumber(randomPhoneFormat, true)}"
  }
}

object Phone extends Faker {
  private lazy val model: PhoneModel = {
    import io.circe.Decoder
    import io.circe.generic.semiauto.deriveDecoder

    implicit val phoneFormatDecoder: Decoder[PhoneFormat] = deriveDecoder
    implicit val phoneModelDecoder: Decoder[PhoneModel]   = deriveDecoder

    val s = Source.fromInputStream(getResource("phone", "en.json")).mkString
    objectFrom[PhoneModel](s)
  }

  lazy val models: Map[String, PhoneFormat] = model.formats.map(pf ⇒ pf.countryCode -> pf).toMap

  def apply(): Phone = new Phone(models)
}
