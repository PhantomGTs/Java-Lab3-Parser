package lab_v1;

import java.util.Map;

class BinaryFunctionNode extends Node {
    private Node left, right;
    private String functionName;

    public BinaryFunctionNode(Node left, Node right, String functionName) {
        this.left = left;
        this.right = right;
        this.functionName = functionName;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        double leftValue = left.evaluate(variables);
        double rightValue = right.evaluate(variables);

        switch (functionName) {
            case "pow":
                return Math.pow(leftValue, rightValue);
            default:
                throw new IllegalArgumentException("Unknown function: " + functionName);
        }
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
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public String toString() {
        return functionName + "(" + left + ", " + right + ")";
    }
}
