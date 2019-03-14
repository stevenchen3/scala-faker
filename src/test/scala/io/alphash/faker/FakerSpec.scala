/*
 * Copyright (c) 2019, Steven Chen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.alphash.faker

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

import org.scalatest.{FlatSpec, Matchers}

class FakerSpec extends FlatSpec with Matchers {
  case class FakerFoo() extends Faker
  case class Foobar(x: Int)

  implicit val fakerFooDecoder: Decoder[Foobar] = deriveDecoder

  "Method 'getConfig'" should "return Some config" in {
    FakerFoo().config() should not be(None)
  }

  it should "return None" in {
    FakerFoo().config("foobar") should be(None)
  }

  "Method 'getResource(String, String): InputStream'" should "return not null InputStream" in {
    (FakerFoo().getResource("person", "en.json") != null) should be (true)
  }

  it should "throw Exception if either resource 'name' or 'file' is invalid" in {
    a[Exception] should be thrownBy {
      FakerFoo().getResource("foo", "bar")
    }
  }

  "Method 'getRandomElement[T](: Seq[T]):T'" should "return an element" in {
    FakerFoo().getRandomElement[Int](List(1)).get should be(1)
  }

  it should "return some element" in {
    // scalastyle:off
    FakerFoo().getRandomElement[Int](List(1, 2, 3, 4, 5)) should not be(None)
    // scalastyle:on
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
    a[Exception] should be thrownBy {
      FakerFoo().objectFrom[Foobar]("foobar")
    }
  }
}
