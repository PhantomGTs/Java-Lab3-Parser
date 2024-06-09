package lab_v1.Parser;

import lab_v1.Nodes.*;

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
        Node left = nodes.isEmpty() ? null : nodes.pop();

        if (left == null || right == null) {
            throw new IllegalArgumentException("Недоступное выражение");
        }

        return new OperatorNode(left, right, String.valueOf(operator));
    }

    // Приоритет оператора
    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3; // Установим приоритет для оператора '^'
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
                        ops.pop();
                    }

                    if (!ops.isEmpty() && ops.peek() == 'p') {
                        ops.pop(); // Удаляем 'p'
                        if (i + 2 < expression.length() && expression.substring(i + 1, i + 3).equals("ow")) {
                            i += 2; // Пропускаем 'ow'

                            while (!ops.isEmpty() && precedence(ops.peek()) >= precedence('^')) {
                                nodes.push(applyOperator(ops.pop(), nodes));
                            }
                            ops.push('^'); // Добавляем '^' в стек операций

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
