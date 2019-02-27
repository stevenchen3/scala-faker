# Scala Faker

Scala Fake Data Generator. This repository includes Scala implementation for this [Golang Faker](https://github.com/bxcodec/faker).

[![Build Status](https://travis-ci.org/stevenchen3/scala-faker.svg?branch=master)](https://travis-ci.org/stevenchen3/scala-faker)
[![Coverage Statue](https://img.shields.io/codecov/c/github/stevenchen3/scala-faker/master.svg)](https://codecov.io/gh/stevenchen3/scala-faker)
[![License](https://img.shields.io/github/license/stevenchen3/scala-faker.svg)](https://github.com/stevenchen3/faker/blob/master/LICENSE)


# Building

Check Scala style, run tests and generate coverage report

```bash
sbt ++2.12.8 scalastyle test coverage coverageReport
```


# Publish Locally

Make sure `export GPG_TTY=$(tty)` is appended to `~/.bashrc` or has been loaded to current shell.

```bash
sbt ++2.12.8 publishLocalSigned
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
* Country code
* Area code
* Toll free phone number
* E164PhoneNumber

**Price :**
* Currency
* Amount
