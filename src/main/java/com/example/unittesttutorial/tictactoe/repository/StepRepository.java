package com.example.unittesttutorial.tictactoe.repository;

import com.example.unittesttutorial.tictactoe.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Step,Long> {

}
