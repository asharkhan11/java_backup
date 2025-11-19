package in.ashar.junit_learn.service;

import org.springframework.stereotype.Service;

import java.util.function.BinaryOperator;

@Service
public class OperatorProvider {

    public BinaryOperator<Integer> getOperator(String op) {
        return switch (op) {
            case "+" -> Integer::sum;
            case "-" -> (a, b) -> a - b;
            case "*" -> (a, b) -> a * b;
            case "/" -> (a, b) -> a / b;
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }
}
