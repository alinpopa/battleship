package com.intenthq.battleship.game

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FlatSpec}
import org.scalamock.scalatest.MockFactory
import org.springframework.ui.ModelMap

@RunWith(classOf[JUnitRunner])
class BattleshipGameControllerTest extends FlatSpec with Matchers with MockFactory{
  private val sampleOutput = "(1, 3, N) SUNK\n" + "(4, 1, E)"
  private val sampleInput = "(5, 5)\n" + "(1, 2, N) (3, 3, E)\n" + "(1, 2) LMLMLMLMM\n" + "(2, 3)\n" + "(3, 3) MRMMRMRRM\n" + "(1, 3)"

  it should "return the view containing the wording of exercise" in {
    val battleshipController = new BattleshipGameController(mock[GameService])
    val actual = battleshipController.battleship(new ModelMap)

    actual should be ("battleship")
  }

  it should "return the exercise view" in {
    val battleshipController = new BattleshipGameController(mock[GameService])
    val actual = battleshipController.exercise(sampleInput, new ModelMap)

    actual should be ("exercise")
  }

  it should "not return output when no input" in {
    val battleshipController = new BattleshipGameController(mock[GameService])
    val model = new ModelMap
    battleshipController.exercise(null, model)

    model.containsAttribute("output") should be (false)
  }

  it should "return output when there is an input" in {
    val battleshipController = new BattleshipGameController(mock[GameService])
    val model = new ModelMap
    battleshipController.exercise(sampleInput, model)

    model.containsAttribute("output") should be (true)
  }

  it should "return sample output for sample input" in {
    val battleshipController = new BattleshipGameController(mock[GameService])
    val model = new ModelMap
    battleshipController.exercise(sampleInput, model)
    val output = model.get("output").asInstanceOf[String]

    output should be (sampleOutput)
  }
}
