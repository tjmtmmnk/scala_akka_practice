package dao

import connection.MYSQLDBImpl
import model.{Dog, DogTable}

import scala.concurrent.Future

trait DogDao extends DogTable with MYSQLDBImpl {

  import driver.api._

  //TableQuery : 実際のDBのテーブルを表現するために必要
  protected val dogTableQuery = TableQuery[DogTable]

  def create(dog: Dog): Future[Int] = db.run {
    dogTableAutoInc += dog
  }

  def update(dog: Dog): Future[Int] = db.run {
    dogTableQuery.filter(_.id === dog.id.get).update(dog) // === : カスタム定義されるメソッド。ここではカラムの比較に使われる
  }

  def getById(id: Int): Future[Option[Dog]] = db.run {
    dogTableQuery.filter(_.id === id).result.headOption
  }

  def getByColor(color: String): Future[Option[Dog]] = db.run {
    dogTableQuery.filter(_.color === color).result.headOption
  }

  def getAll: Future[List[Dog]] = db.run {
    dogTableQuery.to[List].result
  }

  def delete(id: Int): Future[Int] = db.run {
    dogTableQuery.filter(_.id === id).delete
  }

  def ddl = db.run {
    dogTableQuery.schema.create
  }

  protected def dogTableAutoInc = dogTableQuery returning dogTableQuery.map(_.id)
}