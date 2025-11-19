package in.ashar.junit_learn.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @MockitoBean
    private OperatorProvider operatorProvider;

    @BeforeEach
    void setUp() {
        calculator = new Calculator(operatorProvider);
    }

    @AfterEach
    void tearDown() {
        calculator = null;
    }

    @ParameterizedTest
    @CsvSource({
            //expected, num1, num2
            "3, 1, 2",
            "-5, -10, 5",
            "7, 10, -3"
    })
    void addTwoNumbers(int expected, int num1, int num2) {

        Assertions.assertEquals(expected, calculator.addTwoNumbers(num1,num2));
        System.out.println("addition passed for : "+ num1 +" + "+ num2+ " = "+ expected);
    }

    @Test
    void subtractTwoNumbers() {

        Assertions.assertEquals(-3, calculator.subtractTwoNumbers(3,6));
        System.out.println("substraction passed");
    }

    @Test
    void multiplyTwoNumbers() {
        Assertions.assertEquals(4, calculator.multiplyTwoNumbers(2,2));
        System.out.println("multiplication passed");
    }

    @Test
    void divideTwoNumbers() {
        Assertions.assertEquals(1, calculator.divideTwoNumbers(2,2));
        System.out.println("division passed");
    }
}