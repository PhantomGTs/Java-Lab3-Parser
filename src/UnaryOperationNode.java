import java.util.Map;

public class UnaryOperationNode implements Node {
    private final Node operand;
    private final String operator; // Изменение типа оператора на String

    public UnaryOperationNode(Node operand, String operator) { // Изменение аргумента оператора на String
        this.operand = operand;
        this.operator = operator;
    }

    public Node getOperand() {
        return operand;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        double operandValue = operand.evaluate(variables);
        switch (operator) {
            case "-":
                return -operandValue;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    @Override
    public String getName() {
        return operator; // Возвращаем оператор в виде строки
    }

    @Override
    public double getValue() {
        return 0; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public Node getLeft() {
        return null; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public Node getRight() {
        return null; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public String getFunctionName() {
        return ""; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public Node getArgument() {
        return operand; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public String toString() {
        return "(" + operator + " " + operand.toString() + ")";
    }
}
