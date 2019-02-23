package io.alphash.faker

import java.io.InputStream

trait Faker {
  def getResource(name: String, file: String): InputStream = Option {
    getClass.getResourceAsStream(s"/locales/$name/$file")
  }.getOrElse {
    throw new Exception(s"Error loading invalid resource /locales/$name/$file")
  }
}
