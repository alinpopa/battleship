package com.intenthq.battleship;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orig")
public class BattleshipController {

	public static final String OUTPUT_ATT = "output";

    @RequestMapping("/battleship")
    public String battleship(ModelMap model) {
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
