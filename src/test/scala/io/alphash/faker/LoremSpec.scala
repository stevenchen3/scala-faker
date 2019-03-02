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

class LoremSpec extends FlatSpec with Matchers {
  "A word" should "from the given word list" in {
    Lorem.wordList should contain(Lorem().word)
  }

  "A sentence" should "contain 6 words" in {
    val sentence = Lorem().sentence
    sentence.endsWith(".") should be(true)
    val words = sentence.take(sentence.length - 1).split(" ")
    words.size should be(6)
    words(0)(0).isUpper should be(true)
    words.foreach { w ⇒
      Lorem.wordList should contain(w.toLowerCase)
    }
  }

  "A paragraph" should "contain 10 sentences" in {
    val p = Lorem().paragraph
    p.endsWith(".") should be(true)
    val sentences = p.split("\\.")
    sentences.size should be(10)
    sentences.foreach { s ⇒
      val words =
        if (s.startsWith(" ")) s.replaceFirst(" ", "").split(" ")
        else s.split(" ")
      words.size should be(6)
      words(0)(0).isUpper should be(true)
      words.foreach { w ⇒
        Lorem.wordList should contain(w.toLowerCase)
      }
    }
  }
}
