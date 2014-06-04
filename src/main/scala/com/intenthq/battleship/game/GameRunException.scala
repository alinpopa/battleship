package com.intenthq.battleship.game

class GameRunException(message: String) extends RuntimeException{
  override def getMessage = message
}
