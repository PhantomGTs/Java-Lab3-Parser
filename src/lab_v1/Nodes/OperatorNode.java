package lab_v1.Nodes;

import java.util.Map;

public class OperatorNode extends Node {
    private Node left;
    private Node right;
    private String operator;

    public OperatorNode(Node left, Node right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public double evaluate(Map<String, Double> variables)
    {
        switch (operator)
        {
            case "+":
                return left.evaluate(variables) + right.evaluate(variables);
            case "-":
                return left.evaluate(variables) - right.evaluate(variables);
            case "*":
                return left.evaluate(variables) * right.evaluate(variables);
            case "/":
                return left.evaluate(variables) / right.evaluate(variables);
            case "(": // Обработка оператора '('
                return right.evaluate(variables); // Просто возвращаем результат правого узла
            case ",":
                // return right.evaluate(variables); // Просто возвращаем результат правого узла для запятой
                return Math.pow(left.evaluate(variables), right.evaluate(variables)); // Возводим левый узел в степень, заданную правым узлом
            default:
                throw new UnsupportedOperationException("Unknown operator: " + operator);
        }
    }

    // Переопределяем методы, возвращающие левый и правый узлы, и оператор
    @Override
    public Node getLeft() {
        return left;
    }

    @Override
    public Node getRight() {
        return right;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}
