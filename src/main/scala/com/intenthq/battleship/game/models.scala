package com.intenthq.battleship.game

sealed trait Orientation {
  def left: Orientation
  def right: Orientation
  def shortName: String
}
case object North extends Orientation {
  override def left = West
  override def right = East
  override def shortName = "N"
}
case object South extends Orientation {
  override def left = East
  override def right = West
  override def shortName = "S"
}
case object East extends Orientation {
  override def left = North
  override def right = South
  override def shortName = "E"
}
case object West extends Orientation {
  override def left = South
  override def right = North
  override def shortName = "W"
}
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
  def move = state match {
    case Sunk => this
    case Floating => orientation match {
      case East => Ship(Position(position.x + 1, position.y), orientation, state)
      case West => Ship(Position(position.x - 1, position.y), orientation, state)
      case North => Ship(Position(position.x, position.y + 1), orientation, state)
      case South => Ship(Position(position.x, position.y - 1), orientation, state)
    }
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

case class Gameplay(initBoard: InitBoard, initShips: InitShips, actions: List[Action]){
  def createBoard = Board(initBoard.rows, initBoard.cols, initShips.ships)
}
