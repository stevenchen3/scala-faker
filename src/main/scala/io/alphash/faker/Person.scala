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

import com.typesafe.config.ConfigFactory

import scala.io.Source
import scala.util.{Failure, Random, Success, Try}

sealed case class Titles(male: Seq[String], female: Seq[String])
sealed case class FirstNames(male: Seq[String], female: Seq[String])
sealed case class PersonModel(titles: Titles, firstNames: FirstNames, lastNames: Seq[String])

class Person(model: PersonModel) {
  import Person._

  private[this] val RAND_INT_MAX: Int = 100
  private[this] val GENDER_SPLITTER: Int = 50

  def titleMale: String = getRandomElement[String](model.titles.male).get

  def titleFemale: String = getRandomElement[String](model.titles.female).get

  def firstNameMale: String = getRandomElement[String](model.firstNames.male).get

  def firstNameFemale: String = getRandomElement[String](model.firstNames.female).get

  def lastName: String = getRandomElement[String](model.lastNames).get

  def name: String = Random.nextInt(RAND_INT_MAX) match {
    case x if x > GENDER_SPLITTER ⇒ s"${firstNameFemale} ${lastName}"
    case _ ⇒ s"${firstNameMale} ${lastName}"
  }

  def nameWithTitle: String = Random.nextInt(RAND_INT_MAX) match {
    case x if x > GENDER_SPLITTER ⇒ s"${titleFemale} ${firstNameFemale} ${lastName}"
    case _ ⇒ s"${titleMale} ${firstNameMale} ${lastName}"
  }
}

object Person extends Faker {
  private lazy val locale = config().map(c ⇒ Try(c.getString("lang"))).get.getOrElse("en")

  lazy val model: PersonModel = {
    import io.circe.Decoder
    import io.circe.generic.semiauto.deriveDecoder

    implicit val titlesDecoder: Decoder[Titles] = deriveDecoder
    implicit val firstNamesDecoder: Decoder[FirstNames] = deriveDecoder
    implicit val personModelDecoder: Decoder[PersonModel] = deriveDecoder

    val s = Source.fromInputStream(getResource("person", s"${locale}.json")).mkString
    objectFrom[PersonModel](s)
  }

  def apply(): Person = new Person(model)
}
