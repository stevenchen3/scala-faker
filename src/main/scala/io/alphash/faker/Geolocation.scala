package io.alphash.faker

import scala.util.Random

class Geolocation {
  def latitute: Double = Random.nextDouble * 180 - 90
  def longitude: Double = Random.nextDouble * 360 - 180
}

object Geolocation {
  def apply(): Geolocation = new Geolocation()
}
