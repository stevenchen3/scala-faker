package io.alphash.faker

import org.scalatest._

class PersonSpec extends FlatSpec with Matchers {
  "A male" should "have a title as 'Mr.'" in {
    Person().titleMale should be("Mr.")
  }

  it should "have a male first name" in {
    List("Male-A", "Male-B", "Male-C") should contain (Person().firstNameMale)
  }

  "A female" should "have a title as 'Mrs.', 'Ms.' or 'Miss'" in {
    List("Mrs.", "Ms.", "Miss") should contain (Person().titleFemale)
  }

  it should "have a female first name" in {
    List("Female-A", "Female-B", "Female-C") should contain (Person().firstNameFemale)
  }

  "A person" should "have an expected last name" in {
    List("Chan", "Lee") should contain (Person().lastName)
  }

  it should "have a name contains title, first name and last name" in {
    val name = Person().name.split(" ")
    List("Mr.", "Mrs.", "Ms.", "Miss") should contain (name(0))
    List("Male-A", "Male-B", "Male-C", "Female-A", "Female-B", "Female-C") should contain (name(1))
    List("Chan", "Lee") should contain (name(2))
  }
}
