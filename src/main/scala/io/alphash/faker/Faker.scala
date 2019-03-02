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

import com.typesafe.config.{Config, ConfigFactory}

import io.circe._
import io.circe.parser._
import io.circe.syntax._

import java.io.InputStream

import scala.util.{Random, Try}

trait Faker {
  def config(name: String = "faker"): Option[Config] =
    Try(Some(ConfigFactory.load().getConfig(name))).getOrElse(None)

  def getResource(name: String, file: String): InputStream = Option {
    getClass.getResourceAsStream(s"/locales/$name/$file")
  }.getOrElse {
    throw new Exception(s"Error loading invalid resource /locales/$name/$file")
  }

  def getRandomElement[T](xs: Seq[T]): Option[T] =
    if (xs.isEmpty) None else Some(xs(Random.nextInt(xs.size)))

  def objectFrom[T](s: String)(implicit decoder: Decoder[T]): T = {
    decode[T](s) match {
      case Right(value) ⇒ value
      case Left(error)  ⇒ throw new Exception(error.getMessage)
    }
  }
}
