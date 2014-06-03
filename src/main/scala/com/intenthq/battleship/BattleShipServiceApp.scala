package com.intenthq.battleship

import org.springframework.stereotype.Component

@Component
class BattleShipServiceApp extends BattleShipService {
  override def name = "This is a simple app"
}

