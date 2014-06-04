package com.intenthq.battleship.game

import org.scalatest.{Matchers, FlatSpec}
import org.scalamock.scalatest.MockFactory
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BattleshipGameServiceTest extends FlatSpec with Matchers with MockFactory{
  it should "return a valid string when no exception is thrown" in {
    val actions = List(MoveShip(Position(1, 2), List(Right, Move)))
    val gameplay = Gameplay(initBoard = InitBoard(7, 7),
                            initShips = InitShips(List(
                              Ship(Position(1, 2), North),
                              Ship(Position(3, 4), South)
                            )),
                            actions = actions)
    val board = gameplay.createBoard
    val commandsParser = mock[CommandsParser]
    val gameplayRunner = mock[GameplayRunner]
    (commandsParser.parse _).expects("some input").returning(gameplay)
    (gameplayRunner.run _).expects(board, actions).returning(board)
    val battleshipGameService = new BattleshipGameService(commandsParser, gameplayRunner)

    battleshipGameService.execute("some input") should be ("(1, 2, N)\n(3, 4, S)")
  }

  it should "return a valid string showing that a ship has sunk" in {
    val actions = List(MoveShip(Position(1, 2), List(Right, Move)))
    val gameplay = Gameplay(initBoard = InitBoard(7, 7),
      initShips = InitShips(List(
        Ship(Position(1, 2), North),
        Ship(Position(3, 4), South, Sunk)
      )),
      actions = actions)
    val board = gameplay.createBoard
    val commandsParser = mock[CommandsParser]
    val gameplayRunner = mock[GameplayRunner]
    (commandsParser.parse _).expects("some input").returning(gameplay)
    (gameplayRunner.run _).expects(board, actions).returning(board)
    val battleshipGameService = new BattleshipGameService(commandsParser, gameplayRunner)

    battleshipGameService.execute("some input") should be ("(1, 2, N)\n(3, 4, S) SUNK")
  }
}
