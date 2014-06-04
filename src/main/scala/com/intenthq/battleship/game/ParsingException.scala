package com.intenthq.battleship.game

class ParsingException(message: String) extends RuntimeException{
  override def getMessage = message
}
