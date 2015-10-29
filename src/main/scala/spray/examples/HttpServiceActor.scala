package spray.examples

import akka.actor._
import spray.routing.HttpService
import spray.http.HttpHeaders
import spray.routing.HttpService
import spray.http.AllOrigins
import spray.http.HttpHeaders
import spray.http.HttpMethods

class HttpReceiverActor extends Actor with HttpService {
  def actorRefFactory = context
  def receive = runRoute(staticPaths)

  def staticPaths =
    compressResponseIfRequested() {
      respondWithCorsHeaders(
        optionalCookie("SomeCookie") { cookie ⇒
          path("") {
            getFromDirectory("src/main/web/index.html")
          } ~ {
            getFromDirectory("src/main/web")
          }
        })
    }

  def respondWithCorsHeaders =
    respondWithHeaders(
      HttpHeaders.`Access-Control-Allow-Origin`(AllOrigins),
      HttpHeaders.`Access-Control-Allow-Methods`(HttpMethods.GET, HttpMethods.POST),
      HttpHeaders.`Access-Control-Allow-Headers`("Content-Type, Accept"),
      HttpHeaders.Connection("Keep-Alive"))
}



  
