package com.example.unittesttutorial.service;

import com.example.unittesttutorial.entity.Student;
import com.example.unittesttutorial.entity.StudentDto;
import com.example.unittesttutorial.entity.StudentView;
import com.example.unittesttutorial.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public StudentView create(StudentDto dto) {
        dto.setName(dto.getName().toUpperCase());
        Student student = mapper.map(dto,Student.class);
        student.setCreationDate(new Date());
        Student saved = studentRepository.save(student);
        return mapper.map(saved,StudentView.class);
    }

    @Override
    public StudentView getStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        if (student.getAge() > 18) {
            return mapper.map(student,StudentView.class);
        } else {
            throw new RuntimeException("You didn't pass the age requirement");
        }
    }

}
