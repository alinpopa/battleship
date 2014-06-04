package com.intenthq.battleship.game

import org.scalatest.{Matchers, FlatSpec}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ParenthesesCommandsParserTest extends FlatSpec with Matchers {
  it should "parse a gameplay having only one moving ship command" in {
    val parenthesesCommandsParser = new ParenthesesCommandsParser
    val gameplay = """(5, 6)
                      (3, 4, N) (2, 5, E)
                      (3, 3) LMLM"""
    val expectedResult = Gameplay(
      InitBoard(5, 6),
      InitShips(List(Ship(Position(3, 4), North), Ship(Position(2, 5), East))),
      List(
        MoveShip(Position(3, 3), List(Left, Move, Left, Move))
      )
    )

    parenthesesCommandsParser.parse(gameplay) should be (expectedResult)
  }

  it should "parse a gameplay having multiple moving and shooting commands" in {
    val parenthesesCommandsParser = new ParenthesesCommandsParser
    val gameplay = """(5, 6)
                      (3, 2, N) (2, 4, E)
                      (5, 6)
                      (3, 4)
                      (3, 3) LMLM
                      (4, 2)"""
    val expectedResult = Gameplay(
      InitBoard(5, 6),
      InitShips(List(Ship(Position(3, 2), North), Ship(Position(2, 4), East))),
      List(Shoot(Position(5, 6)),
           Shoot(Position(3, 4)),
           MoveShip(Position(3, 3), List(Left, Move, Left, Move)),
           Shoot(Position(4, 2)))
    )

    parenthesesCommandsParser.parse(gameplay) should be (expectedResult)
  }

  it should "parse a gameplay by trimming the commands" in {
    val parenthesesCommandsParser = new ParenthesesCommandsParser
    val gameplay = """  (5, 6)
                      (3, 4, N) (2, 5, E)
                      (3, 3) LMLM"""
    val expectedResult = Gameplay(
      InitBoard(5, 6),
      InitShips(List(Ship(Position(3, 4), North), Ship(Position(2, 5), East))),
      List(
        MoveShip(Position(3, 3), List(Left, Move, Left, Move))
      )
    )

    parenthesesCommandsParser.parse(gameplay) should be (expectedResult)
  }

  it should "fail parsing when the gameplay is missing all the commands" in {
    val parenthesesCommandsParser = new ParenthesesCommandsParser
    val gameplay = "(5, 6)\n(4, 2, N) (5, 3, E)"

    an [ParsingException] should be thrownBy {
      parenthesesCommandsParser.parse(gameplay)
    }
  }

  it should "fail parsing when the gameplay contains a command having an invalid orientation" in {
    val parenthesesCommandsParser = new ParenthesesCommandsParser
    val gameplay = "(5, 6)\n(2, 2, N) (3, 3, F)\n(2, 3)"

    an [ParsingException] should be thrownBy {
      parenthesesCommandsParser.parse(gameplay)
    }
  }

  it should "fail parsing when the gameplay contains a command having wrong spacing" in {
    val parenthesesCommandsParser = new ParenthesesCommandsParser
    val gameplay = "(5, 6)\n(4, 4, N) (5, 5,E)\n(5, 5)"

    an [ParsingException] should be thrownBy {
      parenthesesCommandsParser.parse(gameplay)
    }
  }

  it should "fail parsing when the gameplay contains wrong action movement" in {
    val parenthesesCommandsParser = new ParenthesesCommandsParser
    val gameplay = "(5, 6)\n(1, 2, N) (2, 3, E)\n(2, 3)\n(3, 3) LMLA"

    an [ParsingException] should be thrownBy {
      parenthesesCommandsParser.parse(gameplay)
    }
  }

  it should "fail parsing when positioning of the ships doesn't fit the board size" in {
    val parenthesesCommandsParser = new ParenthesesCommandsParser
    val gameplay = "(5, 6)\n(6, 7, N) (8, 3, E)\n(5, 6)\n(3, 3) LML"

    an [PositioningException] should be thrownBy {
      parenthesesCommandsParser.parse(gameplay)
    }
  }

  it should "fail parsing when having two ships on the same position" in {
    val parenthesesCommandsParser = new ParenthesesCommandsParser
    val gameplay = "(5, 6)\n(2, 3, N) (3, 4, E) (2, 3, E)\n(5, 6)\n(3, 3) LML"

    an [PositioningException] should be thrownBy {
      parenthesesCommandsParser.parse(gameplay)
    }
  }

  it should "fail parsing when giving invalid positioning for the ships setup" in {
    val parenthesesCommandsParser = new ParenthesesCommandsParser
    val gameplay = "(5, 6)\n(1, 3, N) (2, 0, E)\n(5, 6)\n(3, 3) LML"

    an [InvalidPositionException] should be thrownBy {
      parenthesesCommandsParser.parse(gameplay)
    }
  }
}
