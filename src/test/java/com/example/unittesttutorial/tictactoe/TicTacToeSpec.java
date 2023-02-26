package com.example.unittesttutorial.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TicTacToeSpec {
    private TicTacToe ticTacToe;
    @BeforeEach
    public void before() {
        ticTacToe = new TicTacToe();
    }
    @Test
    public void whenXOutsideBoardThenRuntimeException() {
        //Arrange
        int x = 4;
        int y = 2;
        assertThatThrownBy(() -> ticTacToe.play(x,y))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("X is outside of the board");
    }
    @Test
    public void whenYOutsideBoardThenRuntimeException() {
        //Arrange
        int x = 1;
        int y = 6;
        assertThatThrownBy(() -> ticTacToe.play(x,y))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Y is outside of the board");
    }

    @Test
    public void whenOccupiedThenRuntimeException() {
        //Arrange
        int x = 1;
        int y = 2;
        ticTacToe.play(x,y);
        assertThatThrownBy(() -> ticTacToe.play(1,2))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("");
    }

    @Test
    public void givenFirstTurnWhenNextPlayerThenX() {
        assertThat(ticTacToe.nextPlayer()).isEqualTo('X');
    }

    @Test
    public void givenLastTurnWasXWhenNextPlayerThenO() {
        //Arrange
        int x = 1;
        int y = 1;

        //act
        ticTacToe.play(x,y);

        //assert
        assertThat(ticTacToe.nextPlayer()).isEqualTo('O');
    }

    @Test
    public void givenLastTurnWasOWhenNextPlayerThenX() {
        //Arrange
        int x = 1;
        int y = 1;
        int x2 = 2;
        int y2 = 1;

        //act
        ticTacToe.play(x,y);
        ticTacToe.play(x2,y2);

        //arrange
        assertThat(ticTacToe.nextPlayer()).isEqualTo('X');
    }

    @Test
    public void whenPlayThenNoWinner()  {
        // Arrange
        int x = 1;
        int y = 1;

        //Act
        String actual = ticTacToe.play(x,y);

        //Assert
        assertThat(actual).isEqualTo("No winner");
    }

    @Test
    public void whenPlayAndWholeHorizontalLineThenWinner() { // Vertical
        ticTacToe.play(1,3); //X
        ticTacToe.play(1,2); //O
        ticTacToe.play(2,3); //X
        ticTacToe.play(2,2); //O
        String actual = ticTacToe.play(3,3); // X
        assertThat(actual).isEqualTo("X is the winner");
    }

    @Test
    public void whenPlayAndWholeVerticalLineThenWinner() { // Horizontal
        ticTacToe.play(2,2); //X
        ticTacToe.play(1,1); //0
        ticTacToe.play(2,1); //X
        ticTacToe.play(1,2); //O
        ticTacToe.play(3,1); //X
        String actual = ticTacToe.play(1,3);
        assertThat(actual).isEqualTo("O is the winner");
    }

    @Test
    public void whenPlayAndTopBottomDiagonalLineThenWinner() {
        ticTacToe.play(1,1); //X
        ticTacToe.play(1,2); //0
        ticTacToe.play(2,2); //X
        ticTacToe.play(3,2); //0
        String actual = ticTacToe.play(3,3);
        assertThat(actual).isEqualTo("X is the winner");
    }

    @Test
    public void whenPlayAndBottomTopDiagonalLineThenWinner() {
        ticTacToe.play(1,1); //X
        ticTacToe.play(3,1); //0
        ticTacToe.play(1,2); //X
        ticTacToe.play(2,2); //0
        ticTacToe.play(2,1); //X
        String actual = ticTacToe.play(1,3);
        assertThat(actual).isEqualTo("O is the winner");
    }

    @Test
    public void whenAllBoxesAreFilledThenDraw() {
        ticTacToe.play(1,1);
        ticTacToe.play(2,2);
        ticTacToe.play(2,1);
        ticTacToe.play(3,1);
        ticTacToe.play(1,3);
        ticTacToe.play(1,2);
        ticTacToe.play(3,2);
        ticTacToe.play(2,3);
        String actual = ticTacToe.play(3,3);
        assertThat(actual).isEqualTo("The result is draw");

    }
}
