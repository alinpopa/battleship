package com.intenthq.battleship.game

trait CommandsParser {
  def parse(input: String): Gameplay
}
