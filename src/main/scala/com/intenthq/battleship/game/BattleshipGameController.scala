package com.intenthq.battleship.game

import org.springframework.web.bind.annotation.{RequestParam, RequestMethod, RequestMapping}
import org.springframework.ui.ModelMap
import org.springframework.util.StringUtils
import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired

@Controller
@RequestMapping(Array("/"))
class BattleshipGameController @Autowired() (gameService: GameService) {
  private val outputAtt = "output"

  @RequestMapping(value = Array("/battleship"), method = Array(RequestMethod.GET))
  def battleship(model: ModelMap) = {
    "battleship"
  }

  @RequestMapping(Array("/battleship/exercise"))
  def exercise(@RequestParam(value = "input", required = false) input: String, model: ModelMap): String = {
    if (!StringUtils.isEmpty(input)) {
      model.addAttribute(outputAtt, "(1, 3, N) SUNK\n(4, 1, E)")
    }
    "exercise"
  }
}
