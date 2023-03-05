package com.example.unittesttutorial.tictactoe;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.example.unittesttutorial.tictactoe.entity.Step;
import com.example.unittesttutorial.tictactoe.repository.StepRepository;
import com.example.unittesttutorial.tictactoe.service.TicTacToe;
import com.example.unittesttutorial.tictactoe.service.impl.TicTacToeImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicTacToeSpec {

    @Mock
    private StepRepository stepRepository;

    private TicTacToe ticTacToe;

    @BeforeEach
    public void before() {
        ticTacToe = new TicTacToeImpl(stepRepository);
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
        // arrange
        Step step = new Step(1L,0,1,'X');
        when(stepRepository.findAll()).thenReturn(List.of(step));

        // act & assert
        assertThatThrownBy(() -> ticTacToe.play(1,2))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Box is occupied");
    }

    @Test
    public void givenFirstTurnWhenNextPlayerThenX() {
        assertThat(ticTacToe.nextPlayer()).isEqualTo('X');
    }

    @Test
    public void givenLastTurnWasXWhenNextPlayerThenO() {
        // arrange
        Step step = new Step(1L,1,1,'X');
        when(stepRepository.findAll()).thenReturn(List.of(step));

        // act & assert
        assertThat(ticTacToe.nextPlayer()).isEqualTo('O');
    }

    @Test
    public void givenLastTurnWasOWhenNextPlayerThenX() {
        //Arrange
        Step step1 = new Step(1L,0,0,'X');
        Step step2 = new Step(2L,0,1,'O');
        when(stepRepository.findAll()).thenReturn(List.of(step1,step2));

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
        // arrange
        Step step1 = new Step(1L,0,2,'X');
        Step step2 = new Step(2L,0,1,'O');
        Step step3 = new Step(3L,1,2,'X');
        Step step4 = new Step(4L,1,1,'O');
        when(stepRepository.findAll()).thenReturn(List.of(step1,step2,step3,step4));

        // act
        String actual = ticTacToe.play(3,3); // X

        // assert
        assertThat(actual).isEqualTo("X is the winner");
    }

    @Test
    public void whenPlayAndWholeVerticalLineThenWinner() { // Horizontal
        // arrange
        Step step1 = new Step(1L,1,1,'X');
        Step step2 = new Step(2L,0,0,'O');
        Step step3 = new Step(3L,1,0,'X');
        Step step4 = new Step(4L,0,1,'O');
        Step step5 = new Step(5L,2,0,'X');
        when(stepRepository.findAll()).thenReturn(List.of(step1,step2,step3,step4,step5));

        // act
        String actual = ticTacToe.play(1,3);

        // assert
        assertThat(actual).isEqualTo("O is the winner");
    }

    @Test
    public void whenPlayAndTopBottomDiagonalLineThenWinner() {
        // arrange
        Step step1 = new Step(1L,0,0,'X');
        Step step2 = new Step(2L,0,1,'O');
        Step step3 = new Step(3L,1,1,'X');
        Step step4 = new Step(4L,2,1,'O');
        when(stepRepository.findAll()).thenReturn(List.of(step1,step2,step3,step4));

        // act
        String actual = ticTacToe.play(3,3);

        // assert
        assertThat(actual).isEqualTo("X is the winner");
    }

    @Test
    public void whenPlayAndBottomTopDiagonalLineThenWinner() {
        // arrange
        Step step1 = new Step(1L,0,0,'X');
        Step step2 = new Step(2L,2,0,'O');
        Step step3 = new Step(3L,0,1,'X');
        Step step4 = new Step(4L,1,1,'O');
        Step step5 = new Step(5L,1,0,'X');
        when(stepRepository.findAll()).thenReturn(List.of(step1,step2,step3,step4,step5));

        // act
        String actual = ticTacToe.play(1,3);

        // assert
        assertThat(actual).isEqualTo("O is the winner");
    }

    @Test
    public void whenAllBoxesAreFilledThenDraw() {
        // arrange
        Step step1 = new Step(1L,0,0,'X');
        Step step2 = new Step(2L,1,1,'O');
        Step step3 = new Step(3L,1,0,'X');
        Step step4 = new Step(4L,2,0,'O');
        Step step5 = new Step(5L,0,2,'X');
        Step step6 = new Step(6L,0,1,'O');
        Step step7 = new Step(7L,2,1,'X');
        Step step8 = new Step(8L,2,2,'O');
        when(stepRepository.findAll())
                .thenReturn(List.of(step1,step2,step3,step4,step5,step6,step7,step8));

        // act
        String actual = ticTacToe.play(2,3);

        // assert
        assertThat(actual).isEqualTo("The result is draw");
    }

}
