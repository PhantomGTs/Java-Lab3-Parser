package lab_v1.Nodes;

import java.util.Map;

public class VariableNode extends Node {
    private String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        return variables.getOrDefault(name, 0.0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
