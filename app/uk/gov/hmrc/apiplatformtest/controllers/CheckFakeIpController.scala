/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.apiplatformtest.controllers

import java.util.UUID

import play.api.libs.json.Json
import play.api.mvc.Action
import uk.gov.hmrc.play.http.HeaderCarrier
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future
import scala.util.Random

/**
  * Used for API-2989 testing where we return a location header containing IP address which should be stripped out
  * of the header by API gateway
  */
trait CheckFakeIpController extends BaseController {

  implicit val hc: HeaderCarrier

  def handle = Action.async {
    def bit() = (Random.nextInt(250)+1).toString;
    val id = UUID.randomUUID().toString
    val fakeNino = Random.nextString(9)
    val ip = s"${bit()}.${bit()}.${bit()}.${bit()}"
    Future.successful(Ok(Json.toJson("""{ "message": "CheckFakeIp" }""")).withHeaders( (LOCATION,s"https://${ip}:8243/self-assessment/ni/$fakeNino/self-employments/$id") ))
  }
}


object CheckFakeIpController extends CheckFakeIpController {
  override implicit val hc: HeaderCarrier = HeaderCarrier()
}