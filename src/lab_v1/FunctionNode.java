package lab_v1;

import java.util.Map;

public class FunctionNode implements Node {
    private String functionName;
    private Node[] arguments;

    public FunctionNode(String functionName, Node[] arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public double evaluate(Map<String, Double> variables) {
        double[] argumentValues = new double[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            argumentValues[i] = arguments[i].evaluate(variables);
        }

        switch (functionName) {
            case "pow":
                if (argumentValues.length != 2) {
                    throw new IllegalArgumentException("pow function expects exactly two arguments");
                }
                return Math.pow(argumentValues[0], argumentValues[1]);
            case "cos":
                if (argumentValues.length != 1) {
                    throw new IllegalArgumentException("cos function expects exactly one argument");
                }
                return Math.cos(argumentValues[0]);
            default:
                throw new UnsupportedOperationException("Unknown function: " + functionName);
        }
    }

    public String getFunctionName() {
        return functionName;
    }

    public Node[] getArguments() {
        return arguments;
    }


    public double getValue() {
        return 0; // Implement this method according to your requirements
    }

    public Node getLeft() {
        return null; // Implement this method according to your requirements
    }

    public Node getRight() {
        return null; // Implement this method according to your requirements
    }

    public String getOperator() {
        return null; // Implement this method according to your requirements
    }

    public Node getArgument() {
        return null; // Implement this method according to your requirements
    }

    @Override
    public String getName() {
        return functionName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(functionName).append("(");
        for (int i = 0; i < arguments.length; i++) {
            sb.append(arguments[i]);
            if (i < arguments.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
