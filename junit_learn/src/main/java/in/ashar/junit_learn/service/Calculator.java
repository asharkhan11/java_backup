package in.ashar.junit_learn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.BinaryOperator;

@Service
@RequiredArgsConstructor
public class Calculator {

    private final OperatorProvider operatorProvider;

    public int addTwoNumbers(int num1, int num2){
        BinaryOperator<Integer> operator = operatorProvider.getOperator("+");
        return operator.apply(num1, num2);
    }

    public int subtractTwoNumbers(int num1, int num2){
        BinaryOperator<Integer> operator = operatorProvider.getOperator("-");
        return operator.apply(num1, num2);
    }

    public int multiplyTwoNumbers(int num1, int num2){
        BinaryOperator<Integer> operator = operatorProvider.getOperator("*");
        return operator.apply(num1, num2);
    }

    public int divideTwoNumbers(int num1, int num2){
        BinaryOperator<Integer> operator = operatorProvider.getOperator("/");
        return operator.apply(num1, num2);
    }

}
