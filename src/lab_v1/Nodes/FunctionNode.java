package lab_v1.Nodes;

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
            case "pow":
                int exponent = (int) Math.round(argumentValue);
                return Math.pow(argumentValue, exponent);
            default:
                throw new UnsupportedOperationException("Неизвестная функция " + functionName);
        }
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }


    // Метод, возвращающий массив аргументов.
    public Node[] getArguments() {
        return new Node[]{argument};
    }

    @Override
    public String toString() {
        return functionName + "(" + argument + ")";
    }
}
