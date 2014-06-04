package com.intenthq.battleship.game

trait ComandsParser {
  def parse(input: String): Gameplay
}
