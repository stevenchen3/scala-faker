package io.alphash.faker

import scala.util.Random

class Price(currencyOpt: Option[String] = None, amountOpt: Option[Double] = None) {
  import Price._

  private[this] def precision(value: Double, pre: Int): Double = {
    val div = Math.pow(10, pre)
    ((value * div).toLong).toDouble / div
  }

  def currency: String = currencyOpt match {
    case Some(c) ⇒
      if (currencies.contains(c)) c else throw new Exception(s"Invalid currency ${c}")
    case None    ⇒ getRandomElement[String](currencies).get
  }

  def amount: Double = amountOpt match {
    case Some(a) ⇒ if (a > 0) a else throw new Exception("Amount cannot be negative")
    case None    ⇒
      val rand = new Random()
      precision(Math.pow(10, rand.nextInt(8)) * rand.nextDouble, rand.nextInt(2) + 1)
  }

  def amountWithCurrency: String = s"${currency} ${amount}"
}

object Price extends Faker {
  lazy val currencies = Seq(
    "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG",
    "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND",
    "BOB", "BOV", "BRL", "BSD", "BTN", "BWP", "BYN", "BZD", "CAD",
    "CDF", "CHE", "CHF", "CHW", "CLF", "CLP", "CNY", "COP", "COU",
    "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD",
    "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GHS",
    "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG",
    "HUF", "IDR", "ILS", "INR", "IQD", "IRR", "ISK", "JMD", "JOD",
    "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD",
    "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL",
    "MGA", "MKD", "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK",
    "MXN", "MXV", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR",
    "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG",
    "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG",
    "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "SSP", "STN", "SVC",
    "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD",
    "TWD", "TZS", "UAH", "UGX", "USD", "USN", "UYI", "UYU", "UYW",
    "UZS", "VES", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XBA",
    "XBB", "XBC", "XBD", "XCD", "XDR", "XOF", "XPD", "XPF", "XPT",
    "XSU", "XTS", "XUA", "XXX", "YER", "ZAR", "ZMW", "ZWL",
  )

  def apply(currencyOpt: Option[String] = None, amountOpt: Option[Double] = None): Price =
    new Price(currencyOpt, amountOpt)
}
