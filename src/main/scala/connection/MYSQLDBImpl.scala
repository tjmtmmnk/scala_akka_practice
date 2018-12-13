package connection

import slick.driver.MySQLDriver

trait MYSQLDBImpl {
  val driver = MySQLDriver

  import driver.api._

  val db: Database = MYSQLDB.connectionPool
}

private[connection] object MYSQLDB {

  import slick.driver.MySQLDriver.api._

  val connectionPool = Database.forConfig("mysql") //application.confのmysqlを呼んでいる
}