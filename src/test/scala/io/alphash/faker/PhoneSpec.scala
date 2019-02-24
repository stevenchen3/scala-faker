package io.alphash.faker

import org.scalatest.{FlatSpec, Matchers}

class PhoneSpec extends FlatSpec with Matchers {
  "A phone number" should "have the type of '201-123-4567'" in {
    val number = Phone().phoneNumber
    number.size should be(12)
    val digits = number.split("-")
    digits.size should be(3)
    digits(0).size should be(3)
    digits(1).size should be(3)
    digits(2).size should be(4)
  }

  "A toll free phone number" should "have the type of '(888) 123-4567'" in {
    val tollfree = Phone().tollFreePhoneNumber
    tollfree.size should be(14)
    val digits = tollfree.split(" ")
    List("(888)", "(777)") should contain(digits(0))
    digits(1).split("-").size should be(2)
    digits(1).split("-")(0).size should be(3)
  }

  "A E164 phone number" should "have the type of '+71234567891'" in {
    val number = Phone().e164PhoneNumber
    number.size should be(12)
    number.startsWith("+") should be(true)
    List('7', '8') should contain(number(1))
  }
}
