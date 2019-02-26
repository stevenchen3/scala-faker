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


# Available Fakers

It currently supports the following fakers:

- Datetime
- Geolocation
- Internet
- Lorem
- Payment
- Person
- Phone
- Price
