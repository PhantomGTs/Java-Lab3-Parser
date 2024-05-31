import java.util.Map;

public class BinaryFunctionNode extends Node {
    private Node leftArgument;
    private Node rightArgument;
    private String functionName;

    public BinaryFunctionNode(Node leftArgument, Node rightArgument, String functionName) {
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
        this.functionName = functionName;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        double leftValue = leftArgument.evaluate(variables);
        double rightValue = rightArgument.evaluate(variables);
        switch (functionName) {
            case "pow":
                return Math.pow(rightValue, leftValue); // Поменяли местами аргументы
            default:
                throw new UnsupportedOperationException("Unknown function: " + functionName);
        }
    }

    public Node getLeftArgument() {
        return leftArgument;
    }

    public Node getRightArgument() {
        return rightArgument;
    }

    public String getFunctionName() {
        return functionName;
    }
}
