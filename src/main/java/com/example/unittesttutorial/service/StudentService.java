package com.example.unittesttutorial.service;

import com.example.unittesttutorial.entity.StudentDto;
import com.example.unittesttutorial.entity.StudentView;

public interface StudentService {

    StudentView create(StudentDto dto);
    StudentView getStudent(Long id);

}
