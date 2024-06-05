package lab_v1.Nodes;

import java.util.Map;

public abstract class Node {
    public abstract double evaluate(Map<String, Double> variables);
    public String getName() { return ""; }
    public double getValue() { return 0; }
    public Node getLeft() { return null; }
    public Node getRight() { return null; }
    public String getOperator() { return ""; }
    public String getFunctionName() { return ""; }
    public Node getArgument() { return null; }
}