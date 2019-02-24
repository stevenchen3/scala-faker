package io.alphash.faker

import org.scalatest._

class FakerSpec extends FlatSpec with Matchers {
  case class FakerFoo() extends Faker

  "Method 'getResource(String, String): InputStream'" should "return not null InputStream" in {
    (FakerFoo().getResource("person", "en.json") != null) should be (true)
  }

  it should "throw Exception if either resource 'name' or 'file' is invalid" in {
    a [Exception] should be thrownBy {
      FakerFoo().getResource("foo", "bar")
    }
  }

  "Method 'getRandomElement[T](: Seq[T]):T'" should "return an element" in {
    FakerFoo().getRandomElement[Int](List(1)).get should be(1)
  }

  it should "return some element" in {
    FakerFoo().getRandomElement[Int](List(1, 2, 3, 4, 5)) should not be(None)
  }

  it should "return None if the given Seq is empty" in {
    FakerFoo().getRandomElement[Int](List()) should be(None)
  }
}
