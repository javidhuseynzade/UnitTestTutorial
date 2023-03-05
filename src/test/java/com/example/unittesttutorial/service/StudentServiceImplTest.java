package com.example.unittesttutorial.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.unittesttutorial.entity.Student;
import com.example.unittesttutorial.entity.StudentDto;
import com.example.unittesttutorial.entity.StudentView;
import com.example.unittesttutorial.repository.StudentRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Captor
    private ArgumentCaptor<Student> studentArgumentCaptor;

    private StudentService studentService;

    @BeforeEach
    public void beforeEach() {
        ModelMapper modelMapper = new ModelMapper();
        studentService = new StudentServiceImpl(studentRepository, modelMapper);
    }

    @Test
    public void whenGetStudentThenReturnStudentView() {
        //Arrange
        Student student = Student.builder()
                .id(2L)
                .name("Aslan")
                .age(20)
                .build();
        when(studentRepository.findById(2L)).thenReturn(Optional.of(student));

        //Act
        StudentView view = studentService.getStudent(2L);
        //Assert
        assertThat(view.getId()).isEqualTo(2L);
        assertThat(view.getName()).isEqualTo("Aslan");
        assertThat(view.getAge()).isEqualTo(20);
    }

    @Test
    public void whenNoStudentThenThrowStudentNotFound() {
        //Act & Assert
        assertThatThrownBy(() -> studentService.getStudent(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Student not found");
    }

    @Test
    public void whenGetStudentWithAgeLessThan18ThenThrowException() {
        //Arrange
        Student student = Student.builder()
                .id(2L)
                .name("Aslan")
                .age(15)
                .build();
        when(studentRepository.findById(2L)).thenReturn(Optional.of(student));

        //Act & Assert
        assertThatThrownBy(() -> studentService.getStudent(2L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("You didn't pass the age requirement");
    }

    @Test
    void whenCreateStudentThenNameUppercase() {
        // Arrange
        StudentDto dto = StudentDto.builder().name("aslan").age(20).build();
        Student student = new Student();
        student.setName("ASLAN");
        student.setAge(20);
        when(studentRepository.save(any())).thenReturn(student);

        //Act
        studentService.create(dto);

        //Assert
        verify(studentRepository, times(1)).save(studentArgumentCaptor.capture());
        Student result = studentArgumentCaptor.getValue();
        assertThat(result.getName()).isEqualTo(student.getName());
        assertThat(result.getAge()).isEqualTo(student.getAge());
    }

    @Test
    void whenCreateStudentThenReturnStudentView() {
        //Arrange
        StudentDto studentDto = StudentDto.builder().name("aslan").age(20).build();
        Student student = Student.builder().name("ASLAN").age(20).build();
        when(studentRepository.save(any())).thenReturn(student);
        //Act
        StudentView view = studentService.create(studentDto);
        //Assert
        assertThat(view.getName()).isEqualTo("ASLAN");
        assertThat(view.getAge()).isEqualTo(20);
    }

}