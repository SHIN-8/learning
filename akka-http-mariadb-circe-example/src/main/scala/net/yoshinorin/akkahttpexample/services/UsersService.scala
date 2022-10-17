package net.yoshinorin.akkahttpexample.services

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder
import net.yoshinorin.akkahttpexample.models.db.{Users, UsersRepository}

trait UsersService {

  implicit val encodeUser: Encoder[Users] = deriveEncoder[Users]

  /**
   * Get user
   *
   * @param userName user name
   * @return
   */
  def getUser(userName: String): Option[Users] = {
    UsersRepository.findByName(userName)
  }

}
