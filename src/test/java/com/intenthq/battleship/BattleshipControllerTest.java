package com.intenthq.battleship;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BattleshipControllerTest
{

	private static final String SOME_INPUT = "input";
	private static final String SAMPLE_INPUT = "(5, 5)\n" +
			"(1, 2, N) (3, 3, E)\n" +
			"(1, 2) LMLMLMLMM\n" +
			"(2, 3)\n" +
			"(3, 3) MRMMRMRRM\n" +
			"(1, 3)";
	private static final String SAMPLE_OUTPUT = "(1, 3, N) SUNK\n" +
			"(4, 1, E)";

	private ModelMap model = new ModelMap();

	private BattleshipController battleshipController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		battleshipController = new BattleshipController(battleShipService());
	}

	@Test
	public void battleshipShouldReturnTheViewContainingTheWordingOfTheExercise() throws Exception {
		final String actual = battleshipController.battleship(model);
		assertThat(actual, is("battleship"));
	}

	@Test
	public void exerciseShouldReturnTheExerciseView() throws Exception {
		final String actual = battleshipController.exercise(SOME_INPUT, model);
		assertThat(actual, is("exercise"));
	}

	@Test
	public void exerciseWithNoInputShouldNotReturnOutput() throws Exception {
		battleshipController.exercise(null, model);
		assertThat(model.containsAttribute(BattleshipController.OUTPUT_ATT), is(false));
	}

	@Test
	public void exerciseWithInputShouldReturnSomeOutput() throws Exception {
		battleshipController.exercise(SOME_INPUT, model);
		assertThat(model.containsAttribute(BattleshipController.OUTPUT_ATT), is(true));
	}

	@Test
	public void exerciseWithSampleInputShouldReturnSampleOutput() throws Exception {
		battleshipController.exercise(SAMPLE_INPUT, model);
		final String output = (String) model.get(BattleshipController.OUTPUT_ATT);
		assertThat(output, is(SAMPLE_OUTPUT));
	}

    private BattleShipService battleShipService(){
        return new BattleShipService(){
            @Override
            public String name(){
                return "test";
            }
        };
    }
}
