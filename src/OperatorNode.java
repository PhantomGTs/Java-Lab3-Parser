import java.util.Map;

public class OperatorNode implements Node {
    private Node left;
    private Node right;
    private String operator;

    public OperatorNode(Node left, Node right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        // Проверяем оператор и выполняем соответствующую операцию
        switch (operator) {
            case "+":
                return left.evaluate(variables) + right.evaluate(variables);
            case "-":
                return left.evaluate(variables) - right.evaluate(variables);
            case "*":
                return left.evaluate(variables) * right.evaluate(variables);
            case "/":
                return left.evaluate(variables) / right.evaluate(variables);
            case "(":
                return right.evaluate(variables); // Просто возвращаем результат правого узла
            case ",":
                return right.evaluate(variables); // Просто возвращаем результат правого узла для запятой
            default:
                throw new UnsupportedOperationException("Unknown operator: " + operator);
        }
    }

    @Override
    public String getName() {
        return null; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public double getValue() {
        return 0; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public Node getLeft() {
        return left;
    }

    @Override
    public Node getRight() {
        return right;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public String getFunctionName() {
        return null; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public Node getArgument() {
        return null; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public String toString() {
        if (operator.equals("(")) {
            return "(" + right.toString() + ")";
        } else if (operator.equals(",")) {
            return left.toString() + ", " + right.toString();
        } else {
            return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
        }
    }
}
