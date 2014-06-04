package com.intenthq.battleship.game

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FlatSpec}

@RunWith(classOf[JUnitRunner])
class ShipTest extends FlatSpec with Matchers {
  it should "sink a default created ship" in {
    val ship = Ship(Position(1, 1), North)

    ship.sink.state should be (Sunk)
  }

  it should "sink a floating created ship" in {
    val ship = Ship(Position(1, 1), North, Floating)

    ship.sink.state should be (Sunk)
  }

  it should "change the state when the ship is already sunk" in {
    val ship = Ship(Position(1, 1), North, Sunk)

    ship.sink.state should be (Sunk)
  }

  it should "not move the ship when already sunk" in {
    val ship = Ship(Position(1, 1), North, Sunk)

    ship.move.orientation should be (North)
  }

  it should "increase the x value when moving and orientation is east" in {
    val ship = Ship(Position(2, 2), East, Floating)

    ship.move.position should be (Position(3, 2))
  }

  it should "decrease the x value when moving and orientation is west" in {
    val ship = Ship(Position(2, 2), West, Floating)

    ship.move.position should be (Position(1, 2))
  }

  it should "increase the y value when moving and orientation is north" in {
    val ship = Ship(Position(2, 2), North, Floating)

    ship.move.position should be (Position(2, 3))
  }

  it should "decrease the y value when moving and orientation is south" in {
    val ship = Ship(Position(2, 2), South, Floating)

    ship.move.position should be (Position(2, 1))
  }
}
