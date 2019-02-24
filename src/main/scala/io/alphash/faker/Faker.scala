package io.alphash.faker

import java.io.InputStream

import scala.util.Random

trait Faker {
  def getResource(name: String, file: String): InputStream = Option {
    getClass.getResourceAsStream(s"/locales/$name/$file")
  }.getOrElse {
    throw new Exception(s"Error loading invalid resource /locales/$name/$file")
  }

  def getRandomElement[T](xs: Seq[T]): Option[T] =
    if (xs.isEmpty) None else Some(xs(Random.nextInt(xs.size)))
}
