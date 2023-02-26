package com.example.unittesttutorial.tictactoe;

import com.example.unittesttutorial.tictactoe.entity.Step;
import com.example.unittesttutorial.tictactoe.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

public class TicTacToe {

    private StepRepository repository;
    private char lastPlayer;

    private static final int size = 3;
    public String play(int x, int y) {
        Character[][] board = loadSteps();
        checkAxis(x, 'X');
        checkAxis(y, 'Y');
        lastPlayer = nextPlayer();
        setBox(board,x,y,lastPlayer);
        if (isWin(x,y,board)) {
            return lastPlayer + " is the winner";
        }
        else if (isDraw(board)) {
            return "The result is draw";
        }
        else {
            return "No winner";
        }
    }
    private Character[][] loadSteps() {
        Character[][] board = {
                {'\0', '\0', '\0'},
                {'\0', '\0', '\0'},
                {'\0', '\0', '\0'}
        };
         repository.findAll().stream()
                .forEach(step -> board[step.getXCord()][step.getYCord()]=step.getPlayer());
         return board;
    }

    private void checkAxis(int axis, char cord) {
        if (axis < 1 || axis > 3) {
            throw new RuntimeException(cord + " is outside of the board");
            }
    }

    private void setBox(Character[][] board, int x, int y, char player) {
        if (board[x - 1][y - 1] != '\0') {
            throw new RuntimeException("");
        } else {
            board[x - 1][y - 1] = player;
            saveMove(x,y,player);
        }
    }

    public char nextPlayer() {
        if (lastPlayer == 'X') {
            return 'O';
        }
        return 'X';
    }

    private boolean isWin(int x, int y, Character[][] board) {
        int playerTotal = lastPlayer * 3;
        char horizontal, vertical, diagonal1, diagonal2;
        horizontal = vertical = diagonal1 = diagonal2 = '\0';
        for (int i=0; i<size; i++) {
            horizontal += board[x-1][i];
            vertical += board[i][y-1];
            diagonal1 += board[i][i];
            diagonal2 += board[i][size - i - 1];
        }
        if (horizontal == playerTotal || vertical == playerTotal
                || diagonal1 == playerTotal || diagonal2 == playerTotal) {
            return true;
        }
        return false;
    }
    private boolean isDraw(Character[][] board) {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (board[i][j] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    public void saveMove(int x, int y, char player) {
        Step step = new Step();
        step.setXCord(x);
        step.setYCord(y);
        step.setPlayer(player);
        repository.save(step);
    }
}
