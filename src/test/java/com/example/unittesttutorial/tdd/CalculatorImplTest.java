package com.example.unittesttutorial.tdd;

import com.example.unittesttutorial.simplecalculator.CalculatorImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CalculatorImplTest {
    private CalculatorImpl calculator;
    @Test
    public void givenAAndBWhenAddThenC() {
        //Arrange
        calculator = new CalculatorImpl();
        int a = 7;
        int b = 2;
        int c = 9;

        //Act
        int result = calculator.add(a,b);

        //Assert
        assertThat(result).isEqualTo(c);
    }
    @Test
    public void givenAAndBWhenSubtractThenC() {
        //Arrange
        calculator = new CalculatorImpl();
        int a = 10;
        int b = 4;
        int c = 6;

        //Act
        int result = calculator.subtract(a,b);

        //Assert
        assertThat(result).isEqualTo(c);

    }
    @Test
    public void givenAAndBWhenMultiplyThenC() {
        //Arrange
        calculator = new CalculatorImpl();
        int a = 8;
        int b = 6;
        int c = 48;

        //Act
        int result = calculator.multiply(a,b);

        //Assert
        assertThat(result).isEqualTo(c);

    }

    @Test
    public void givenAAndBWhenDivideThenC() {
        //Arrange
        calculator = new CalculatorImpl();
        int a = 8;
        int b = 2;
        int c = 4;

        //Act
        int result = calculator.divide(a,b);

        //Assert
        assertThat(result).isEqualTo(c);

    }
    @Test
    public void givenAAndBWhenDivideByZeroThenException() {
        //Arrange
        CalculatorImpl calculator = new CalculatorImpl();
        int a = 5;
        int b = 0;

        //Assert
        assertThatThrownBy(() -> calculator.divide(a,b))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("/ by zero");
    }
}
