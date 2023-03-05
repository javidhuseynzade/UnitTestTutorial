package com.example.unittesttutorial.tictactoe.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.unittesttutorial.tictactoe.dto.StepDto;
import com.example.unittesttutorial.tictactoe.service.TicTacToe;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(TicTacToeController.class)
class TicTacToeControllerTest {

    @MockBean
    private TicTacToe ticTacToe;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void whenPlayThenNoWinner() throws Exception {
        // arrange
        StepDto dto = new StepDto(1,1);
        when(ticTacToe.play(1,1)).thenReturn("No winner");

        // act
        mockMvc.perform(
                post("/tictactoe/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto))
        ).andExpect(status().isOk());
    }

}