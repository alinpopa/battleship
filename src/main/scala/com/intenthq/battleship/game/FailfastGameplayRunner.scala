package com.intenthq.battleship.game

import org.springframework.stereotype.Component

@Component
class FailfastGameplayRunner extends GameplayRunner{

  override def run(board: Board, actions: List[Action]): Board = {
    actions.foldLeft(board)((board, action) => actionRunner(action, board))
  }

  private def actionRunner(action: Action, board: Board): Board = {
    val ships = board.ships.map(ship => applyAction(board, ship, action))
    Board(board.rows, board.cols, ships)
  }

  private def applyAction(board: Board, ship: Ship, action: Action): Ship = {
    if(ship.position == action.position){
      action match {
        case MoveShip(_, movements) => validate(board, transition(ship, movements))
        case Shoot(_) => ship.sink
      }
    }
    else ship
  }

  private def transition(ship: Ship, movements: List[Movement]): Ship = {
    movements.foldLeft(ship){
      (ship, movement) =>
        movement match {
          case Move => ship.move
          case Left => Ship(ship.position, ship.orientation.left, ship.state)
          case Right => Ship(ship.position, ship.orientation.right, ship.state)
        }
    }
  }

  private def validatePositionOnBoard(board: Board, ship: Ship): Ship = {
    if(ship.position.x <= 0 ||
      ship.position.x > board.rows ||
      ship.position.y <= 0 ||
      ship.position.y > board.cols) throw new GameRunException(s"Wrong position for ship $ship")
    else ship
  }

  private def validateConflictPosition(board: Board, ship: Ship): Ship = {
    val existingShip = board.ships.find(_.position == ship.position)
    existingShip match {
      case None => ship
      case Some(boardShip) => {
        if(boardShip.state == Floating) throw new GameRunException(s"Existing floating ship at this position: $ship")
        else ship
      }
    }
  }

  private def validate(board: Board, ship: Ship): Ship = {
    val validator = List(
      (ship: Ship) => validatePositionOnBoard(board, ship),
      (ship: Ship) => validateConflictPosition(board, ship)
    ).reduceLeft(_.andThen(_))
    validator(ship)
  }
}
