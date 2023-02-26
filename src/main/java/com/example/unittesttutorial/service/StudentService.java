package com.example.unittesttutorial.service;

import com.example.unittesttutorial.entity.StudentDto;
import com.example.unittesttutorial.entity.StudentView;
import com.example.unittesttutorial.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
public interface StudentService {
    StudentView create(StudentDto dto);
    StudentView getStudent(Long id);
}
