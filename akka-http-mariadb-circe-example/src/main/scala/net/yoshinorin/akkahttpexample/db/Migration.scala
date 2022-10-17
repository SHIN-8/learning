package net.yoshinorin.akkahttpexample.db

import org.flywaydb.core.Flyway
import com.typesafe.config.{Config, ConfigFactory}
import net.yoshinorin.akkahttpexample.services.QuillProvider

object Migration extends App with QuillProvider {

  import ctx._

  val configuration: Config = ConfigFactory.load()

  val url: String = configuration.getString("db.ctx.dataSource.url")
  val user: String = configuration.getString("db.ctx.dataSource.user")
  val password: String = configuration.getString("db.ctx.dataSource.password")

  probe("CREATE DATABASE akkaexample")

  val flyway = Flyway
    .configure()
    .dataSource(url, user, password)
    .load()

  flyway.migrate

}
