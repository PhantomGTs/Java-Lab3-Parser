package lab_v1.Nodes;

import java.util.Map;

public class BinaryFunctionNode extends Node {
    private final Node left;
    private final Node right;
    private final String functionName;

    public BinaryFunctionNode(Node left, Node right, String functionName) {
        this.left = left;
        this.right = right;
        this.functionName = functionName;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getFunctionName() {
        return functionName;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        double leftValue = left.evaluate(variables);
        double rightValue = right.evaluate(variables);

        return switch (functionName) {
            case "pow" -> Math.pow(leftValue, rightValue);
            default -> throw new IllegalArgumentException("Неизвестная функция: " + functionName);
        };
    }


    @Override
    public String toString() {
        return functionName + "(" + left + ", " + right + ")";
    }
}
