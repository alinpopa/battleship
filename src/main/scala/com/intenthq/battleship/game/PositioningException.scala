package com.intenthq.battleship.game

class PositioningException(message: String) extends RuntimeException{
  override def getMessage = message
}
