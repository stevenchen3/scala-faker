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
