package com.ovoenergy.bootcamp.kafka.common

import com.ovoenergy.bootcamp.kafka.domain.Arbitraries
import org.scalacheck.Arbitrary

trait Randoms {

  def randomSuchThat[A: Arbitrary](condition: A => Boolean): A = Arbitrary.arbitrary[A].suchThat(condition).sample.get

  def random[A: Arbitrary]: A = randomSuchThat[A](_ => true)

}

object Randoms extends Randoms
