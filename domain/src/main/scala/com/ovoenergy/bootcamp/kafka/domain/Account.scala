package com.ovoenergy.bootcamp.kafka.domain

import com.ovoenergy.bootcamp.kafka.domain.Account.AccountId

case class Account(id: AccountId, owner: CustomerId, contractAddress: PostalAddress, billingAddress: PostalAddress)

object Account {

  case class AccountId(value: String) extends AnyVal

}