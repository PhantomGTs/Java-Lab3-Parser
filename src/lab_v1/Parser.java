package lab_v1;

import java.util.*;

public class Parser {
    private Node createNode(String token) {
        if (token.matches("[a-zA-Z]+")) {
            return new VariableNode(token);
        } else {
            return new ConstantNode(Double.parseDouble(token));
        }
    }

    private Node applyOperator(char operator, Stack<Node> nodes) {
        Node right = nodes.pop();
        Node left = nodes.isEmpty() ? null : nodes.pop(); // Добавляем проверку на пустой стек

        // Проверяем операнды на null
        if (left == null || right == null) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return new OperatorNode(left, right, String.valueOf(operator));
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    public Node parse(String expression) {
        Stack<Character> ops = new Stack<>();
        Stack<Node> nodes = new Stack<>();
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (Character.isDigit(c) || Character.isLetter(c) || c == '.') {
                token.append(c);
            } else {
                if (token.length() > 0) {
                    nodes.push(createNode(token.toString()));
                    token.setLength(0);
                }

                if (c == '(') {
                    ops.push(c);
                } else if (c == ')') {
                    while (!ops.isEmpty() && ops.peek() != '(') {
                        nodes.push(applyOperator(ops.pop(), nodes));
                    }
                    if (!ops.isEmpty()) {
                        ops.pop(); // Удаляем '('
                    }

                    // Check for function call
                    if (!ops.isEmpty() && Character.isLetter(ops.peek())) {
                        char function = ops.pop();
                        if (function == 'c') {
                            nodes.push(new FunctionNode("cos", new Node[] { nodes.pop() }));
                        } else if (function == 'w') { // Обработка функции pow
                            Node right = nodes.pop();
                            Node left = nodes.pop();
                            nodes.push(new BinaryFunctionNode(left, right, "pow"));
                            ops.pop(); // Удаляем 'w'
                        }
                    }
                } else {
                    while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                        nodes.push(applyOperator(ops.pop(), nodes));
                    }
                    ops.push(c);
                }
            }
        }

        if (token.length() > 0) {
            nodes.push(createNode(token.toString()));
        }

        while (!ops.isEmpty()) {
            nodes.push(applyOperator(ops.pop(), nodes));
        }

        return nodes.pop();
    }
}