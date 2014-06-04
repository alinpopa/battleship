package com.intenthq.battleship.game

import scala.util.Try

class ParenthesesCommandsParser extends ComandsParser{
  private val positionCommand = """\((\d+),\s(\d+)\)""".r
  private val initShipsCommand = """\((\d+),\s(\d+),\s([NWSE])\)""".r
  private val movementsCommand = """\((\d+),\s(\d+)\)\s([RLM]+)""".r
  private val commandsSplitter = """\n"""
  private val movementsSplitter = """(?=\()"""

  override def parse(input: String): Gameplay = {
    val gameplay = input.split(commandsSplitter)
    if (gameplay.size <= 2)
      throw new ParsingException("wrong gameplay format")
    else
      validate(parseGameplay(gameplay.toList))
  }

  private def parseGameplay(gameplay: List[String]): Gameplay = {
    val initialBoard :: shipsSetup :: actions = gameplay
    Gameplay(initBoard = parseInitialBoard(initialBoard),
             initShips = parseShipsSetup(shipsSetup),
             actions = parseActions(actions))
  }

  private def parseInitialBoard(initialBoard: String): InitBoard = {
    try {
      val positionCommand(initX, initY) = initialBoard
      InitBoard(initX.toInt, initY.toInt)
    } catch {
      case _:MatchError => throw new ParsingException(s"wrong format for the initial board command: $initialBoard")
    }
  }

  private def parseShipsSetup(shipsSetup: String): InitShips = {
    try{
      val initialShips = shipsSetup.split(movementsSplitter)
      val ships = initialShips.filterNot(_.trim.isEmpty).map{
        ship =>
          val initShipsCommand(initX, initY, orientation) = ship.trim
          Ship(Position(initX.toInt, initY.toInt), Orientation(orientation))
      }.toList
      InitShips(ships)
    } catch {
      case _:MatchError => throw new ParsingException(s"wrong format for the initial ships configuration: $shipsSetup")
    }
  }

  private def parseActions(actions: List[String]): List[Action] = {
    for(action <- actions) yield {
      val trimmedAction = action.trim
      tryMoveAction(trimmedAction).orElse {
        tryShootAction(trimmedAction)
      }.getOrElse {
        throw new ParsingException(s"wrong action format: $action")
      }
    }
  }

  private def tryMoveAction(action: String): Try[Action] = {
    Try{
      val movementsCommand(x, y, movements) = action
      MoveShip(Position(x.toInt, y.toInt), movements.map(movement => Movement(movement)).toList)
    }
  }

  private def tryShootAction(action: String): Try[Action] = {
    Try{
      val positionCommand(x, y) = action
      Shoot(Position(x.toInt, y.toInt))
    }
  }

  private def validate(gameplay: Gameplay): Gameplay = {
    List(validateInitShipsPosition,
         validateActionsPosition,
         validateSamePositionShips).foldLeft(gameplay){
      (gameplay, validation) =>
        validation(gameplay)
    }
  }

  private val validateInitShipsPosition: Gameplay => Gameplay = (gameplay) => {
    gameplay.initShips.ships.foreach{
      ship =>
        if(ship.position.x > gameplay.initBoard.rows || ship.position.y > gameplay.initBoard.cols)
          throw new PositioningException(s"Ship position is off the board: $ship")
    }
    gameplay
  }

  private val validateActionsPosition: Gameplay => Gameplay = (gameplay) => {
    gameplay.actions.foreach{
      action =>
        if(action.position.x > gameplay.initBoard.rows || action.position.y > gameplay.initBoard.cols)
          throw new PositioningException(s"Action position is off the board: $action")
    }
    gameplay
  }

  private val validateSamePositionShips: Gameplay => Gameplay = (gameplay) => {
    val positions = gameplay.initShips.ships.map(_.position)
    if(positions.distinct.size != positions.size)
      throw new PositioningException("Multiple ships on the same position")
    gameplay
  }
}
