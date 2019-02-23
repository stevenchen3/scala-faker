package io.alphash.faker

import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging

import scala.io._
import scala.util.{Failure, Random, Success, Try}

case class Titles(male: Seq[String], female: Seq[String])
case class FirstNames(male: Seq[String], female: Seq[String])
case class PersonModel(titles: Titles, firstNames: FirstNames, lastNames: Seq[String])

class Person(model: PersonModel) {
  private[this] val rand = new Random()

  private[this] def getRandomElement(xs: Seq[String]): String = xs(rand.nextInt(xs.size))

  def titleMale: String = getRandomElement(model.titles.male)

  def titleFemale: String = getRandomElement(model.titles.female)

  def firstNameMale: String = getRandomElement(model.firstNames.male)

  def firstNameFemale: String = getRandomElement(model.firstNames.female)

  def lastName: String = getRandomElement(model.lastNames)

  def name: String = rand.nextInt(100) match {
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
