package model

import connection.MYSQLDBImpl
import spray.json.DefaultJsonProtocol

trait DogTable extends DefaultJsonProtocol {
  self: MYSQLDBImpl => //自分型アノテーション

  import driver.api._ //MYSQLDBImplで作ったdriverからimport

  implicit lazy val dogFormat = jsonFormat2(Dog) //この2はDogクラスのコンストラクタ引数が2個であることを示している
  implicit lazy val dogListFormat = jsonFormat1(DogList) //implicit修飾子をつけるのはライブラリの仕様に対応するため

  class DogTable(tag: Tag) extends Table[Dog](tag, "dog") { //Table[T] -> T : テーブルに挿入するデータの型
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc) //PrimaryKey : このcolumnが主キーであることを示す。　AutoInc : 自動インクリメント
    val name = column[String]("name") //テーブルのコラムは全てcolumnを通して作成する

    /*Every table requires a '*' method contatining a default projection.
     *This describes what you get back when you return rows (in the form of a table object) from a query
     *
     * <> : When executing these queries you have to think of the result row of a query as a Scala tuple
     *Dog.unapply : コンストラクタ引数をOption型のタプルで返す
    * */
    def * = (name, id.?) <> (Dog.tupled, Dog.unapply)
  }
}

case class Dog(name: String, id: Option[Int] = None)

case class DogList(dogs: List[Dog])