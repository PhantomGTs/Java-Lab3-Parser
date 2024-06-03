package lab_v1;

import java.util.Map;

public class VariableNode implements Node {
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

    // Добавленные методы из интерфейса lab_v1.Node, которые необходимо реализовать
    @Override
    public double getValue() {
        return 0; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public Node getLeft() {
        return null; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public Node getRight() {
        return null; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public String getOperator() {
        return null; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public String getFunctionName() {
        return null; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public Node getArgument() {
        return null; // Реализуйте этот метод с учетом ваших требований
    }

    @Override
    public String toString() {
        return name;
    }
}
