package com.intenthq.battleship.game

sealed trait Orientation
case object North extends Orientation
case object South extends Orientation
case object East extends Orientation
case object West extends Orientation
object Orientation {
  def apply(orientation: String): Orientation = orientation match {
    case "N" => North
    case "S" => South
    case "E" => East
    case "W" => West
  }
}

case class Position(x: Int, y: Int)

sealed trait Movement
case object Left extends Movement
case object Right extends Movement
case object Move extends Movement
object Movement {
  def apply(movement: Char) = movement match {
    case 'L' => Left
    case 'R' => Right
    case 'M' => Move
  }
}

sealed trait ShipState
case object Sunk extends ShipState
case object Floating extends ShipState

case class Ship(position: Position, orientation: Orientation, state: ShipState) {
  def sink = state match {
    case Sunk => this
    case _ => Ship(position, orientation, Sunk)
  }
}
object Ship {
  def apply(position: Position, orientation: Orientation): Ship =
    Ship(position, orientation, Floating)
}

sealed trait Command
sealed trait Action{
  def position: Position
}
case class InitBoard(rows: Int, cols: Int) extends Command
case class InitShips(ships: List[Ship]) extends Command
case class MoveShip(position: Position, movements: List[Movement]) extends Command with Action
case class Shoot(position: Position) extends Command with Action

case class Output(ships: List[Ship])

case class Board(rows: Int, cols: Int, ships: List[Ship]) {
  def this(rows: Int, cols: Int) = this(rows, cols, List.empty[Ship])
}

case class Gameplay(initBoard: InitBoard, initShips: InitShips, actions: List[Action])
