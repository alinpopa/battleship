package com.intenthq.battleship.game

class MovementException(message: String) extends RuntimeException{
  override def getMessage = message
}
