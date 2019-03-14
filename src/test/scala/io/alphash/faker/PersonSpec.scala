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

import org.scalatest.{FlatSpec, Matchers}

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

  it should "have a name contains first name and last name" in {
    (1 to 100).foreach { _ ⇒
      val name = Person().name.split(" ")
      val xs = List("Male-A", "Male-B", "Male-C", "Female-A", "Female-B", "Female-C")
      xs should contain (name(0))
      List("Chan", "Lee") should contain (name(1))
    }
  }

  it should "have a name contains title, first name and last name" in {
    (1 to 100).foreach { _ ⇒
      val name = Person().nameWithTitle.split(" ")
      List("Mr.", "Mrs.", "Ms.", "Miss") should contain (name(0))
      val xs = List("Male-A", "Male-B", "Male-C", "Female-A", "Female-B", "Female-C")
      xs should contain (name(1))
      List("Chan", "Lee") should contain (name(2))
    }
  }
}
