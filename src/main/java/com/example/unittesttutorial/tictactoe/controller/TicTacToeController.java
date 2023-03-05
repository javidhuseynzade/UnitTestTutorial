package com.example.unittesttutorial.tictactoe.controller;

import com.example.unittesttutorial.tictactoe.dto.Result;
import com.example.unittesttutorial.tictactoe.dto.StepDto;
import com.example.unittesttutorial.tictactoe.service.TicTacToe;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tictactoe")
@RequiredArgsConstructor
public class TicTacToeController {

    private final TicTacToe ticTacToe;

    @PostMapping("/")
    public Result saveStep(@RequestBody StepDto stepDto) {
        return new Result(ticTacToe.play(stepDto.getX(),stepDto.getY()));
    }

}
