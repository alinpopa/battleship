package com.intenthq.battleship.game

trait GameplayRunner {
  def run(board: Board, actions: List[Action]): Board
}
