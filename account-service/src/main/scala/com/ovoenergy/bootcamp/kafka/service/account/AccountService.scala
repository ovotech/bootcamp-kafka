package com.ovoenergy.bootcamp.kafka.service.account

import java.util.concurrent.ConcurrentHashMap

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, HttpApp, PathMatcher1, Route}
import buildinfo.BuildInfo
import com.ovoenergy.bootcamp.kafka.common.serde._
import com.ovoenergy.bootcamp.kafka.domain.Account.AccountId
import com.ovoenergy.bootcamp.kafka.domain.{Account, CreateAccount}
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.circe.syntax._
import io.circe.{Encoder, Json}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object AccountService extends Directives with ErrorAccumulatingCirceSupport {

  type AccountRepository = ConcurrentHashMap[AccountId, Account]

  val accountIdMatcher: PathMatcher1[AccountId] = Segment.map(AccountId.apply)

  implicit val buildInfoEncoder: Encoder[BuildInfo.type] = Encoder.instance { bi =>
    Json.obj(
      "name" -> bi.name.asJson,
      "version" -> bi.version.asJson
    )
  }

  def routes(accountRepository: AccountRepository): Route =
    path("admin" / "ping") {
      complete("pong")
    } ~
      path("admin" / "info") {
        complete(BuildInfo)
      } ~
      pathPrefix("api" / "v1") {
        pathPrefix("account") {
          (pathEndOrSingleSlash & post) {
            entity(as[CreateAccount]){ createAccount =>
              val account = Account(
                AccountId.unique(),
                createAccount.acquisitionId,
                createAccount.tariff,
                createAccount.domicileAddress,
                createAccount.billingAddress
              )
              accountRepository.put(account.id, account)
              complete(StatusCodes.Created->account)
            }
          } ~
            path(accountIdMatcher) { accountId =>
              get {
                Option(accountRepository.get(accountId)).fold(complete(StatusCodes.NotFound))(complete(_))
              }
            }
        }
      }

}

