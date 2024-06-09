package lab_v1.Simlifire;

import lab_v1.Nodes.Node; // Базовый класс
import lab_v1.Nodes.ConstantNode; // Узел, представляющий константное значение (например, число)
import lab_v1.Nodes.FunctionNode; // Узел, представляющий функцию, которая принимает один аргумент.
import lab_v1.Nodes.OperatorNode; // Узел, представляющий бинарную операцию (например, +, -, *, /).
import lab_v1.Nodes.UnaryOperationNode; // Узел, представляющий унарную операцию (например, отрицание числа -x).

import java.util.*;

public class Simplifier {
    public static Node simplify(Node expression) {
        List<Node> uniqueNodes = new ArrayList<>();
        return simplifyExpression(expression, uniqueNodes);
    }


    private static Node simplifyExpression(Node expression, List<Node> uniqueNodes) {
        // Проверяем, есть ли уже упрощенный узел для текущего узла
        for (Node node : uniqueNodes) {
            String str1 = expression.toString();
            String str2 = node.toString();
            if (expression.toString().equals(node.toString())) {
                return node;
            }
        }

        /*
        * 1. Если узел является бинарной операцией
        *   1.1 Рекурсивно упрощаются левый и правый операнды
        *   1.2 Если оба операнда константы, операция вычисляется, и создается новый узел ConstantNode
        *   1.3 В противном случае создается новый узел OperatorNode с упрощенными операндами.
        *
        * 2. Если узел является унарной операцией
        *
        * 3. Если узел является функцией
        *
        * 4. Если узел является константой (ConstantNode), он уже упрощен и добавляется в Если узел является константой (ConstantNode), он уже упрощен и добавляется в uniqueNodes
        * */

        if (expression instanceof OperatorNode) {
            OperatorNode binaryNode = (OperatorNode) expression;
            Node left = simplifyExpression(binaryNode.getLeft(), uniqueNodes);
            Node right = simplifyExpression(binaryNode.getRight(), uniqueNodes);

            if (left instanceof ConstantNode && right instanceof ConstantNode) {
                double leftValue = ((ConstantNode) left).getValue();
                double rightValue = ((ConstantNode) right).getValue();
                double result;
                switch (binaryNode.getOperator()) {
                    case "+":
                        result = leftValue + rightValue;
                        break;
                    case "-":
                        result = leftValue - rightValue;
                        break;
                    case "*":
                        result = leftValue * rightValue;
                        break;
                    case "/":
                        if (rightValue == 0) {
                            throw new ArithmeticException("Ошибка при делении на 0");
                        }
                        result = leftValue / rightValue;
                        break;
                    default:
                        throw new IllegalArgumentException("Не поддерживаемый оператор: " + binaryNode.getOperator());
                }
                ConstantNode simplifiedNode = new ConstantNode(result);
                uniqueNodes.add(simplifiedNode); // Добавляем упрощенный узел в список
                return simplifiedNode;
            } else {
                OperatorNode simplifiedNode = new OperatorNode(left, right, binaryNode.getOperator());
                uniqueNodes.add(simplifiedNode); // Добавляем упрощенный узел в список
                return simplifiedNode;
            }
        } else if (expression instanceof UnaryOperationNode) {
            UnaryOperationNode unaryNode = (UnaryOperationNode) expression;
            Node operand = simplifyExpression(unaryNode.getOperand(), uniqueNodes);
            UnaryOperationNode simplifiedNode = new UnaryOperationNode(operand, unaryNode.getOperator().charAt(0));
            uniqueNodes.add(simplifiedNode); // Добавляем упрощенный узел в список
            return simplifiedNode;
        } else if (expression instanceof FunctionNode) {
            FunctionNode functionNode = (FunctionNode) expression;
            Node[] arguments = functionNode.getArguments();
            Node[] simplifiedArguments = new Node[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                simplifiedArguments[i] = simplifyExpression(arguments[i], uniqueNodes);
            }
            FunctionNode simplifiedNode = new FunctionNode(simplifiedArguments[0], functionNode.getName());
            uniqueNodes.add(simplifiedNode); // Добавляем упрощенный узел в список
            return simplifiedNode;
        } else {
            uniqueNodes.add(expression); // Если узел не является операцией, то он уже упрощен
            return expression;
        }
    }
}
