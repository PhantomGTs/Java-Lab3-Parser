package lab_v1;

import java.util.Map;

public class ConstantNode implements Node {
    private double value;

    public ConstantNode(double value) {
        this.value = value;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        return value;
    }

    @Override
    public String getName() {
        return ""; // Постоянные значения не имеют имени
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public Node getLeft() {
        return null; // Константы не являются бинарными операциями
    }

    @Override
    public Node getRight() {
        return null; // Константы не являются бинарными операциями
    }

    @Override
    public String getOperator() {
        return ""; // Константы не являются операторами
    }

    @Override
    public String getFunctionName() {
        return ""; // Константы не являются функциями
    }

    @Override
    public Node getArgument() {
        return null; // Константы не являются аргументами функций
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
