# Scala Faker

Scala Fake Data Generator. This repository includes Scala implementation for
[Golang Faker](https://github.com/bxcodec/faker).

[![Build Status](https://travis-ci.org/stevenchen3/scala-faker.svg?branch=master)](https://travis-ci.org/stevenchen3/scala-faker)
[![Coverage Statue](https://img.shields.io/codecov/c/github/stevenchen3/scala-faker/master.svg)](https://codecov.io/gh/stevenchen3/scala-faker)
[![License](https://img.shields.io/github/license/stevenchen3/scala-faker.svg)](https://github.com/stevenchen3/faker/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.stevenchen3/scala-faker_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.stevenchen3/scala-faker_2.12)



# Get Started

**Supported Scala versions**
* Scala 2.11
* Scala 2.12

This library is available at Maven Central Repository. To use the latest stable version, append the
following to your `build.sbt` and replace `LATEST_VERSION` with the latest version published, which
should be [![Maven Central](https://img.shields.io/maven-central/v/com.github.stevenchen3/scala-faker_2.12.svg)](
https://maven-badges.herokuapp.com/maven-central/com.github.stevenchen3/scala-faker_2.12)

```scala
libraryDependencies += "com.github.stevenchen3" %% "scala-faker" % "LATEST_VERSION"
```


## Build Current `master`

Check scalastyle, run tests and generate coverage report across all supported Scala versions:

```bash
sbt +scalastyle +scalafixcheck +coverage +test +coverageReport
```


## Publish Locally

Make sure `export GPG_TTY=$(tty)` (this fixes `gpg: signing failed: Inappropriate ioctl for device`
error, checkout [here](https://github.com/keybase/keybase-issues/issues/2798) to see why) is
appended to `~/.bashrc` or has been loaded to current shell.

```bash
source ~/.bashrc
sbt +publishLocalSigned
```


## Examples

```scala

import io.alphash.faker._

val number = Phone().phoneNumber()
println(s"$number") // output some phone number like, "+1 (202) 737-6022"

val name = Person().name
println(s"$name")  // e.g., "Blanca Spencer"

val price = Price().amountWithCurrency
println(s"$price") // e.g., "CAD 42545.1"

val ip = Internet().ipv4
println(s"$ip")    // e.g., "89.51.74.211"

...

```


# Available Fakers

It currently supports the following fakers:

**Datetime :**
* Datetime
* Time

**Geolocation :**
* Latitude and Longitude

**Internet :**
* Email
* Mac address
* Domain name
* URL
* Username
* IP Address (IPv4, IPv6 )
* Password

**Lorem :**
* Word
* Sentence
* Paragraph

**Payment :**
* Credit Card Type (VISA, MASTERCARD, AMERICAN EXPRESS, DISCOVER, JCB, DINERS CLUB)
* Credit Card Number

**Person :**
* Title male
* Title female
* First name
* First name male
* First name female
* Last name
* Name with title
* Name (first last)

**Phone :**
* Phone number
* Country code (US and Singapore)
* Area code
* Toll free phone number
* E164PhoneNumber

**Price :**
* Currency
* Amount
