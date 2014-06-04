package com.intenthq.battleship.game

import org.scalatest.{Matchers, FlatSpec}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FailfastGameplayRunnerTest extends FlatSpec with Matchers{
  it should "ignore movement of an already sunk ship" in {
    val failfastGameplayRunner = new FailfastGameplayRunner
    val ships = List(
      Ship(Position(1, 2), North),
      Ship(Position(3, 4), East, Sunk)
    )
    val board = Board(7, 7, ships)
    val actions = List(
      MoveShip(Position(3, 4), List(Move, Left, Right, Move)),
      Shoot(Position(3, 4))
    )

    failfastGameplayRunner.run(board, actions).ships.find{
      ship => ship.position == Position(3, 4) && ship.state == Sunk
    } should be (Some(Ship(Position(3, 4), East, Sunk)))
  }

  it should "change the position of a ship for a movement action" in {
    val failfastGameplayRunner = new FailfastGameplayRunner
    val ships = List(
      Ship(Position(1, 2), North),
      Ship(Position(3, 4), East)
    )
    val board = Board(7, 7, ships)
    val actions = List(
      MoveShip(Position(3, 4), List(Move, Left, Right, Move)),
      Shoot(Position(3, 4))
    )
    val newShipLocation = Ship(Position(5, 4), East, Floating)

    failfastGameplayRunner.run(board, actions).ships.find(_ == newShipLocation) should be (Some(newShipLocation))
  }

  it should "fail when trying to move a ship off the board" in {
    val failfastGameplayRunner = new FailfastGameplayRunner
    val ships = List(
      Ship(Position(1, 2), North),
      Ship(Position(3, 4), East)
    )
    val board = Board(7, 7, ships)
    val actions = List(
      MoveShip(Position(3, 4), List(Move, Left, Right, Move, Move, Move, Move)),
      Shoot(Position(3, 4))
    )

    an [GameRunException] should be thrownBy {
      failfastGameplayRunner.run(board, actions)
    }
  }

  it should "fail when trying to move a ship to the same position as a floating ship" in {
    val failfastGameplayRunner = new FailfastGameplayRunner
    val ships = List(
      Ship(Position(1, 2), North),
      Ship(Position(3, 4), East)
    )
    val board = Board(7, 7, ships)
    val actions = List(
      MoveShip(Position(3, 4), List(Right, Move, Move, Right, Move, Move)),
      Shoot(Position(3, 4))
    )

    an [GameRunException] should be thrownBy {
      failfastGameplayRunner.run(board, actions)
    }
  }

  it should "change the position of a ship for a movement action when the board contains an already sunk ship on the same position" in {
    val failfastGameplayRunner = new FailfastGameplayRunner
    val ships = List(
      Ship(Position(1, 2), North, Sunk),
      Ship(Position(3, 4), East)
    )
    val board = Board(7, 7, ships)
    val actions = List(
      MoveShip(Position(3, 4), List(Right, Move, Move, Right, Move, Move)),
      Shoot(Position(3, 4))
    )
    val newShipLocation = Ship(Position(1, 2), West, Floating)

    failfastGameplayRunner.run(board, actions).ships.find(_ == newShipLocation) should be (Some(newShipLocation))
  }

  it should "sink an existing floating ship" in {
    val failfastGameplayRunner = new FailfastGameplayRunner
    val ships = List(
      Ship(Position(1, 2), North, Floating),
      Ship(Position(3, 4), East)
    )
    val board = Board(7, 7, ships)
    val actions = List(
      MoveShip(Position(3, 4), List(Right, Move, Move, Right, Move)),
      Shoot(Position(1, 2))
    )
    val sunkShip = Ship(Position(1, 2), North, Sunk)

    failfastGameplayRunner.run(board, actions).ships.find(_ == sunkShip) should be (Some(sunkShip))
  }

}
