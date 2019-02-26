package io.alphash.faker

import com.typesafe.config.{Config, ConfigFactory}

import io.circe._
import io.circe.parser._
import io.circe.syntax._

import java.io.InputStream

import scala.util.{Random, Try}

trait Faker {
  def config(name: String = "faker"): Option[Config] =
    Try(Some(ConfigFactory.load().getConfig(name))).getOrElse(None)

  def getResource(name: String, file: String): InputStream = Option {
    getClass.getResourceAsStream(s"/locales/$name/$file")
  }.getOrElse {
    throw new Exception(s"Error loading invalid resource /locales/$name/$file")
  }

  def getRandomElement[T](xs: Seq[T]): Option[T] =
    if (xs.isEmpty) None else Some(xs(Random.nextInt(xs.size)))

  def objectFrom[T](s: String)(implicit decoder: Decoder[T]): T = {
    decode[T](s) match {
      case Right(value) ⇒ value
      case Left(error)  ⇒ throw new Exception(error.getMessage)
    }
  }
}
