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
