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

import java.net.InetAddress

import scala.math._
import scala.math.BigInt.probablePrime
import scala.util.Random

class Internet(tld: Seq[String], urlFormats: Seq[(String*) ⇒ String]) {
  import Internet._

  private[this] val USR_LEN: Int = 7

  def email: String = s"${Random.alphanumeric.take(USR_LEN).mkString}@${domainName}"

  def macAddress: String = {
    val macAddr: Array[Byte] = Array.ofDim[Byte](6) // scalastyle:off
    Random.nextBytes(macAddr) // scalastyle:on

    //zeroing last 2 bytes to make it unicast and locally adminstrated
    macAddr(0) = (macAddr(0) & 254.toByte).toByte

    val sb = new StringBuilder(18) // scalastyle:off
    macAddr.foreach { b ⇒ // scalastyle:on
      if (sb.length > 0) sb.append(":")
      sb.append(f"${b}%02x")
    }
    sb.toString
  }

  def domainName: String =
    s"${Random.alphanumeric.take(USR_LEN).mkString}.${getRandomElement[String](tld).get}"

  def url: String = {
    val f = getRandomElement[(String*) ⇒ String](urlFormats).get
    if (f(Seq("?")).count(_ == '?') == 1) f(Seq(domainName))
    else f(Seq(domainName, username))
  }

  def username: String = Random.alphanumeric.take(USR_LEN).mkString

  def password: String = {
    val rand = new Random()
    (1 to 16).map(x => rand.nextPrintableChar).mkString
  }

  // This could generate an IP like "255.255.255.255"
  def ipv4: String = {
    val rand = new Random()
    // scalastyle:off
    def loop(n: Int, acc: Seq[Int]): Seq[Int] = n match {
      case x if x > 0 ⇒
        if (acc.isEmpty) loop(n - 1, acc :+ (1 + rand.nextInt(255)))
        else loop(n - 1, acc :+ rand.nextInt(256))
      case _ ⇒ acc
    }
    loop(4, Seq()).mkString(".")
    // scalastyle:on
  }

  def ipv6: String = {
    // scalastyle:off
    def bigIntToByteArray(n: BigInt, size: Int = 16): Array[Byte] = {
      val a = n.toByteArray
      val leadingLength = math.max(size - a.length, 0)
      Array.ofDim[Byte](leadingLength) ++ a
    }

    def bigIntToIPv6(ipv6Num : BigInt) : String = {
      val address = InetAddress.getByAddress(bigIntToByteArray(ipv6Num))
      address.toString.replaceFirst("/", "")
    }

    bigIntToIPv6(probablePrime(100, Random))
    // scalastyle:on
  }
}

object Internet extends Faker {
  lazy val tld = Seq("com", "biz", "info", "io", "net", "org", "ru")
  lazy val urlFormats: Seq[(String*) ⇒ String] = Seq(
    s ⇒ s"http://www.${s(0)}/",
    s ⇒ s"https://www.${s(0)}/",
    s ⇒ s"http://${s(0)}/",
    s ⇒ s"https://${s(0)}/",
    s ⇒ if (s.length > 1) s"http://www.${s(0)}/${s(1)}" else s"http://www.${s(0)}/${s(0)}",
    s ⇒ if (s.length > 1) s"https://www.${s(0)}/${s(1)}" else s"https://www.${s(0)}/${s(0)}",
    s ⇒ if (s.length > 1) s"http://${s(0)}/${s(1)}" else s"http://${s(0)}/${s(0)}",
    s ⇒ if (s.length > 1) s"https://${s(0)}/${s(1)}" else s"https://${s(0)}/${s(0)}",
    s ⇒ if (s.length > 1) s"http://${s(0)}/${s(1)}.html" else s"http://${s(0)}/${s(0)}.html",
    s ⇒ if (s.length > 1) s"https://${s(0)}/${s(1)}.html" else s"https://${s(0)}/${s(0)}.html",
    s ⇒ if (s.length > 1) s"http://${s(0)}/${s(1)}.php" else s"http://${s(0)}/${s(0)}.php",
    s ⇒ if (s.length > 1) s"https://${s(0)}/${s(1)}.php" else s"https://${s(0)}/${s(0)}.php"
  )

  def apply(): Internet = new Internet(tld, urlFormats)
}
