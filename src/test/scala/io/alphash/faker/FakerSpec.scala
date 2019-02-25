package io.alphash.faker

import org.scalatest.{FlatSpec, Matchers}

class FakerSpec extends FlatSpec with Matchers {
  case class FakerFoo() extends Faker
  case class Foobar(x: Int)

  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val fakerFooDecoder: Decoder[Foobar] = deriveDecoder

  "Method 'getConfig'" should "return Some config" in {
    FakerFoo().config should not be(None)
  }

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

  "JSON string" should "convert to object" in {
    val s = """{"x":1}"""
    val foo = FakerFoo().objectFrom[Foobar](s)
    foo.x should be(1)
  }

  it should "throw Exception if invalid string is given" in {
    a [Exception] should be thrownBy {
      FakerFoo().objectFrom[Foobar]("foobar")
    }
  }
}
