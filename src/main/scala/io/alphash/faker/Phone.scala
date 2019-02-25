package io.alphash.faker

import scala.util.Random

class Phone {
  // It generates phone numbers of type: "201-886-0269"
  def phoneNumber: String = {
    // scalastyle:off
    val xs = (1 to 10).map(_ ⇒ Random.nextInt(10))
    s"${xs.slice(0, 3).mkString}-${xs.slice(3, 6).mkString}-${xs.slice(6, 10).mkString}"
    // scalastyle:on
  }

  // It generates phone numbers of type: "(888) 123-4567"
  def tollFreePhoneNumber: String = {
    // scalastyle:off
    val starts = List("777", "888")
    val xs = (1 to 7).map(_ ⇒ Random.nextInt(10))
    s"(${starts(Random.nextInt(2))}) ${xs.slice(0, 3).mkString}-${xs.slice(3, 7).mkString}"
    // scalastyle:on
  }

  // It generates phone numbers of type: "+71234567891"
  def e164PhoneNumber: String = {
    // scalastyle:off
    val starts = List("7", "8")
    val xs = (1 to 10).map(_ ⇒ Random.nextInt(10))
    s"+${starts(Random.nextInt(2))}${xs.mkString}"
    // scalastyle:on
  }
}

object Phone {
  def apply(): Phone = new Phone()
}
