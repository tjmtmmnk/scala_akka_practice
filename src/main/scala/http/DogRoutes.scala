package http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MethodDirectives
import dao.DogDao
import model.Dog

import scala.concurrent.ExecutionContextExecutor

trait DogRoutes extends SprayJsonSupport {
  this: DogDao =>
  implicit val dispatcher: ExecutionContextExecutor
  val routes = pathPrefix("dogs") {
    /* /dogs/... */
    pathEnd {
      /* /dogs */
      get {
        complete(getAll)
      } ~ post { //'~' : 2つのルートを結ぶルートを返す。第1の経路が要求を拒絶した場合、第2の経路は要求に応じるチャンスが与えられる
        //entity : 指定した型にアンマーシャリング
        entity(as[Dog]) { dog => //dog : Dog("name",Some(id))
          complete {
            create(dog).map { result => HttpResponse(entity = "dog has been saved successfully") }
          }
        }
      }
    } ~ /* /dogs/black */
      pathPrefix("black") {
        get {
          complete(getByColor("black"))
        }
      } ~ /* /dogs/[number]/...*/
      path(IntNumber) { id => //path: 先頭の/を消費したあと、残りの一致しないパスに指定されたIntNumberを適用する
        get {
          complete(getById(id))
        } ~ put {
          entity(as[Dog]) { dog =>
            complete {
              val newDog = Dog(dog.name, dog.color, Option(id))
              update(newDog).map { result => HttpResponse(entity = "dog has been updated successfully") }
            }
          }
        } ~ MethodDirectives.delete {
          complete {
            delete(id).map { result => HttpResponse(entity = "dog has been deleted successfully") }
          }
        }
      }
  }
}