import java.util.Map;
import java.util.Stack;

public class Parser {

    public Node parse(String expression) {
        // Убираем пробелы
        expression = expression.replaceAll("\\s+", "");

        // Токенизация выражения
        Stack<Node> nodes = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                int start = i;
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    i++;
                }
                nodes.push(new ConstantNode(Double.parseDouble(expression.substring(start, i))));
                continue;
            } else if (Character.isLowerCase(c)) {
                int start = i;
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    i++;
                }
                String variable = expression.substring(start, i);
                nodes.push(new VariableNode(variable));
                continue;
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    nodes.push(createOperatorNode(operators.pop(), nodes.pop(), nodes.pop()));
                }
                operators.pop();
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    nodes.push(createOperatorNode(operators.pop(), nodes.pop(), nodes.pop()));
                }
                operators.push(c);
            } else if (Character.isUpperCase(c)) {
                int start = i;
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    i++;
                }
                String constant = expression.substring(start, i);
                nodes.push(new ConstantNode(getConstantValue(constant)));
                continue;
            }
            i++;
        }

        while (!operators.isEmpty()) {
            nodes.push(createOperatorNode(operators.pop(), nodes.pop(), nodes.pop()));
        }

        return nodes.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
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
                return -1;
        }
    }

    private Node createOperatorNode(char operator, Node right, Node left) {
        switch (operator) {
            case '+':
                return new OperatorNode(left, right, "+");
            case '-':
                return new OperatorNode(left, right, "-");
            case '*':
                return new OperatorNode(left, right, "*");
            case '/':
                return new OperatorNode(left, right, "/");
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    private double getConstantValue(String constant) {
        switch (constant) {
            case "E":
                return Math.E;
            case "PI":
                return Math.PI;
            case "PHI":
                return 1.61803398875; // Число Фи
            default:
                throw new IllegalArgumentException("Unknown constant: " + constant);
        }
    }
}
