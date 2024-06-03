import java.util.Map;

public class BinaryOperationNode implements Node {
    private final Node left;
    private final Node right;
    private final String operator;

    public BinaryOperationNode(Node left, Node right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        double leftValue = left.evaluate(variables);
        double rightValue = right.evaluate(variables);
        switch (operator) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                if (rightValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return leftValue / rightValue;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    @Override
    public String getName() {
        return operator; // Реализуйте этот метод с учетом ваших требований
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
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}
