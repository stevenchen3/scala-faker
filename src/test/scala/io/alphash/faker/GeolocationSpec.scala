package io.alphash.faker

import org.scalatest.{FlatSpec, Matchers}

class GeolocationSpec extends FlatSpec with Matchers {
  "A geo" should "have latitute and longitude" in {
    Geolocation().latitute / 1.0 should not be(0)
    Geolocation().longitude / 1.0 should not be(0)
  }
}
