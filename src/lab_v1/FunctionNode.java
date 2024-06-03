package lab_v1;

import java.util.Map;

public class FunctionNode extends Node {
    private Node argument;
    private String functionName;

    public FunctionNode(Node argument, String functionName) {
        this.argument = argument;
        this.functionName = functionName;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        double argumentValue = argument.evaluate(variables);
        switch (functionName) {
            case "cos":
                return Math.cos(argumentValue);
            default:
                throw new UnsupportedOperationException("Unknown function: " + functionName);
        }
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public Node getArgument() {
        return argument;
    }

    // Метод, возвращающий массив аргументов.
    // Для этой реализации массив содержит только один аргумент.
    public Node[] getArguments() {
        return new Node[] { argument };
    }

    @Override
    public String toString() {
        return functionName + "(" + argument + ")";
    }
}
