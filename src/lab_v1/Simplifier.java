package lab_v1;

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

        if (expression instanceof OperatorNode) {
            OperatorNode binaryNode = (OperatorNode) expression;
            Node left = simplifyExpression(binaryNode.getLeft(), uniqueNodes);
            Node right = simplifyExpression(binaryNode.getRight(), uniqueNodes);

            // Применяем правила упрощения
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
                            throw new ArithmeticException("Division by zero");
                        }
                        result = leftValue / rightValue;
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported operator: " + binaryNode.getOperator());
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
            // Если узел не является операцией, то он уже упрощен
            uniqueNodes.add(expression);
            return expression;
        }
    }
}
