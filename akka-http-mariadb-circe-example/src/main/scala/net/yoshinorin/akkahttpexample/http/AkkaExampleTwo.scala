package net.yoshinorin.akkahttpexample.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import net.yoshinorin.akkahttpexample.services.UsersService
import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn

object AkkaExampleTwo extends App with UsersService {

  implicit val actorSystem: ActorSystem = ActorSystem("akkahttpexample")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher

  val route = get {
    pathEndOrSingleSlash {
      complete(HttpEntity(ContentTypes.`application/json`, "{\"message\": \"Hello Akka HTTP!!\"}"))
    } ~ pathPrefix("users") {
      pathPrefix(".+".r) { userName =>
        getUser(userName) match {
          case Some(u) => complete(HttpEntity(ContentTypes.`application/json`, s"$u"))
          case _ => complete(HttpEntity(ContentTypes.`application/json`, "{\"message\": \"Not Found\"}"))
        }
      }
    }
  }

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 9000)
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => actorSystem.terminate())

}
