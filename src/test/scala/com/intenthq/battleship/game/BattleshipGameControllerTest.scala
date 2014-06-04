package com.intenthq.battleship.game

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FlatSpec}
import org.scalamock.scalatest.MockFactory
import org.springframework.ui.ModelMap

@RunWith(classOf[JUnitRunner])
class BattleshipGameControllerTest extends FlatSpec with Matchers with MockFactory{
  it should "return the view containing the wording of exercise" in {
    val battleshipController = new BattleshipGameController(mock[GameService])
    val actual = battleshipController.battleship(new ModelMap)

    actual should be ("battleship")
  }
}
