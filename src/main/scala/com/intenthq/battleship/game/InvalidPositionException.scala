package com.intenthq.battleship.game

class InvalidPositionException(message: String) extends RuntimeException{
  override def getMessage = message
}
