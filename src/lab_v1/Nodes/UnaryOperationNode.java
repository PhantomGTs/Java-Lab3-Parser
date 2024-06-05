package lab_v1.Nodes;

import java.util.Map;

public class UnaryOperationNode extends Node {
    private final Node operand;
    private final char operator;

    public UnaryOperationNode(Node operand, char operator) {
        this.operand = operand;
        this.operator = operator;
    }

    public Node getOperand() {
        return operand;
    }

    @Override
    public String getOperator() {
        return String.valueOf(operator);
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        double operandValue = operand.evaluate(variables);
        switch (operator) {
            case '-':
                return -operandValue;
            default:
                throw new IllegalArgumentException("Недоступный оператор: " + operator);
        }
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public Node getLeft() {
        return null;
    }

    @Override
    public Node getRight() {
        return null;
    }

    @Override
    public String getFunctionName() {
        return "";
    }

    @Override
    public Node getArgument() {
        return operand;
    }

    @Override
    public String toString() {
        return operator + operand.toString();
    }
}
