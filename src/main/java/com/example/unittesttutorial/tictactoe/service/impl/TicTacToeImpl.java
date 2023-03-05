package com.example.unittesttutorial.tictactoe.service.impl;

import com.example.unittesttutorial.tictactoe.entity.Step;
import com.example.unittesttutorial.tictactoe.repository.StepRepository;
import com.example.unittesttutorial.tictactoe.service.TicTacToe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicTacToeImpl implements TicTacToe {

    private static final int size = 3;

    private final StepRepository stepRepository;

    public String play(int x, int y) {
        checkAxis(x, 'X');
        checkAxis(y, 'Y');
        Character[][] board = loadSteps();
        char lastPlayer = nextPlayer();
        setBox(board, x, y, lastPlayer);
        if (isWin(x, y, board, lastPlayer)) {
            return lastPlayer + " is the winner";
        } else if (isDraw(board)) {
            return "The result is draw";
        } else {
            return "No winner";
        }
    }

    private Character[][] loadSteps() {
        Character[][] board = {
                {'\0', '\0', '\0'},
                {'\0', '\0', '\0'},
                {'\0', '\0', '\0'}
        };
        stepRepository.findAll()
                .forEach(step -> board[step.getXCord()][step.getYCord()] = step.getPlayer());
        printBoard(board);
        return board;
    }

    private void checkAxis(int axis, char cord) {
        if (axis < 1 || axis > 3) {
            throw new RuntimeException(cord + " is outside of the board");
        }
    }

    private void setBox(Character[][] board, int x, int y, char player) {
        if (board[x - 1][y - 1] != '\0') {
            throw new RuntimeException("Box is occupied");
        } else {
            board[x - 1][y - 1] = player;
            saveMove(x - 1, y - 1, player);
        }
    }

    public char nextPlayer() {
        int size = stepRepository.findAll().size();
        if (size == 0) {
            return 'X';
        }
        if (stepRepository.findAll().get(size - 1).getPlayer() == 'X') {
            return 'O';
        }
        return 'X';
    }

    private boolean isWin(int x, int y, Character[][] board, char lastPlayer) {
        int playerTotal = lastPlayer * 3;
        char horizontal, vertical, diagonal1, diagonal2;
        horizontal = vertical = diagonal1 = diagonal2 = '\0';
        for (int i = 0; i < size; i++) {
            horizontal += board[x - 1][i];
            vertical += board[i][y - 1];
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private void saveMove(int x, int y, char player) {
        Step step = new Step();
        step.setXCord(x);
        step.setYCord(y);
        step.setPlayer(player);
        stepRepository.save(step);
    }

    private void printBoard(Character[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '\0') {
                    System.out.print("- ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
