package lab_v1.Nodes;

import java.util.Map;

public class ConstantNode extends Node {
    private double value;

    public ConstantNode(double value) {
        this.value = value;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        return value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
