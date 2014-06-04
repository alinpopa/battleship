package com.intenthq.battleship.game

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

@Service
class BattleshipGameService @Autowired() (commandsParser: CommandsParser, gamePlayRunner: GameplayRunner) extends GameService {
  override def execute(input: String): String = {
    val gamePlay = commandsParser.parse(input)
    gamePlayRunner.run(gamePlay.createBoard, gamePlay.actions).ships.map{
      ship =>
        val shipInfo = s"(${ship.position.x}, ${ship.position.y}, ${ship.orientation.shortName})"
        if(ship.state == Sunk)  s"$shipInfo SUNK"
        else shipInfo
    }.mkString("\n")
  }
}
