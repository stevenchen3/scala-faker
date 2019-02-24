package io.alphash.faker

import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging

import scala.io._
import scala.util.{Failure, Random, Success, Try}

sealed case class Titles(male: Seq[String], female: Seq[String])
sealed case class FirstNames(male: Seq[String], female: Seq[String])
sealed case class PersonModel(titles: Titles, firstNames: FirstNames, lastNames: Seq[String])

class Person(model: PersonModel) {
  import Person._

  def titleMale: String = getRandomElement[String](model.titles.male)

  def titleFemale: String = getRandomElement[String](model.titles.female)

  def firstNameMale: String = getRandomElement[String](model.firstNames.male)

  def firstNameFemale: String = getRandomElement[String](model.firstNames.female)

  def lastName: String = getRandomElement[String](model.lastNames)

  def name: String = Random.nextInt(100) match {
    case x if x > 50 ⇒ s"${titleFemale} ${firstNameFemale} ${lastName}"
    case _ ⇒ s"${titleMale} ${firstNameMale} ${lastName}"
  }
}

object Person extends Faker with LazyLogging {
  private[this] lazy val locale: String =
    Try(ConfigFactory.load().getConfig("faker").getString("lang")) match {
      case Success(v) ⇒ v
      case Failure(_) ⇒
        logger.warn("Failed to load locale settings. Use default locale 'en'")
        "en"
    }

  lazy val model: PersonModel = {
    import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
    val s = Source.fromInputStream(getResource("person", s"${locale}.json")).mkString
    decode[PersonModel](s) match {
      case Right(value) ⇒ value
      case Left(error)  ⇒ throw new Exception(error.getMessage)
    }
  }

  def apply(): Person = new Person(model)
}