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
