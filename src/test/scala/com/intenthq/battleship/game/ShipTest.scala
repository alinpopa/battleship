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

  it should "do nothing when the ship is already sunk" in {
    val ship = Ship(Position(1, 1), North, Sunk)

    ship.sink.state should be (Sunk)
  }
}
