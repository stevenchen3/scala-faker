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

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

class InternetSpec extends FlatSpec with Matchers {
  "A domain name" should "have the format of '{name}.{tld}'" in {
    val d = Internet().domainName.split("\\.")
    d.size should be(2)
    Internet.tld should contain(d(1))
  }

  "An email address" should "have the format of '{username}@{domain}'" in {
    val email = Internet().email
    email.split("@").size should be(2)
    """(?=[^\s]+)(?=(\w+)@([\w\.]+))""".r.findFirstIn(email) should not be(None)
  }

  "A URL" should "have protocol, domain and tld" in {
    (1 to 100).foreach { _ ⇒
      val url = Internet().url
      """(http|https)://(.*)\.([a-z]+)""".r.findFirstIn(url) should not be(None)
    }
  }

  "A username" should "have 7 alphanumeric characters" in {
    Internet().username.matches("[a-zA-Z0-9]{7}") should be (true)
  }

  "A password" should "have 16 characters" in {
    Internet().password.size should be (16)
  }

  "An IPv4 address" should "have the format of '{0-255}.{0-255}.{0-255}.{0-255}'" in {
    val ip = Internet().ipv4
    """.*(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3}).*""".r.findFirstIn(ip) should not be(None)
    Try(Some(InetAddress.getByName(ip))).getOrElse(None) should not be(None)
  }

  "An IPv6 address" should "be able to convert to InetAddress object" in {
    val ip = Internet().ipv6
    Try(Some(InetAddress.getByName(ip))).getOrElse(None) should not be(None)
  }

  "An upper case MAC address" should "match regex '(([0-9A-F]{2}[:-]){5}([0-9A-F]{2}))'" in {
    val mac = Internet().macAddress.toUpperCase
    "(([0-9A-F]{2}[:-]){5}([0-9A-F]{2}))".r.findFirstIn(mac) should not be(None)
  }
}
