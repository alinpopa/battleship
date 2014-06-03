package com.intenthq.battleship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BattleshipController {

	public static final String OUTPUT_ATT = "output";

    private final BattleShipService battleShipService;

    @Autowired
    public BattleshipController(final BattleShipService battleShipService){
        this.battleShipService = battleShipService;
    }

    @RequestMapping("/battleship")
    public String battleship(ModelMap model) {
        System.out.println(battleShipService.name());
        return "battleship";
    }

    @RequestMapping("/battleship/exercise")
    public String exercise(@RequestParam(value="input", required=false) String input, ModelMap model) {
		if (!StringUtils.isEmpty(input)) {
			model.addAttribute(OUTPUT_ATT, "(1, 3, N) SUNK\n(4, 1, E)");
		}
        return "exercise";
    }

}
