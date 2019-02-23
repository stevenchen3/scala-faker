package io.alphash.faker

import java.io.InputStream

import scala.util.{Failure, Success, Try}

trait Faker {
  def getResource(name: String, file: String): InputStream =
    Try(getClass.getResourceAsStream(s"/locales/$name/$file")) match {
      case Success(s) ⇒ s
      case Failure(_) ⇒ throw new Exception(s"Error loading invalid resource /locales/$name/$file")
    }
}
