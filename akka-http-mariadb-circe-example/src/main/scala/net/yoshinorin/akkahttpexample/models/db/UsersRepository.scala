package net.yoshinorin.akkahttpexample.models.db

import net.yoshinorin.akkahttpexample.services.QuillProvider

trait UsersRepository {

  def findByName(name: String): Option[Users]

}

object UsersRepository extends UsersRepository with QuillProvider {

  import ctx._

  /**
   * Find user by name
   *
   * @param name user name
   * @return
   */
  def findByName(name: String): Option[Users] = {
    run(query[Users].filter(u => u.name == lift(name))).headOption
  }

}
