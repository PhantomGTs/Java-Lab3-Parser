import java.util.Map;

public class Simplifier {
    public static Node simplify(Node expression) {
        return simplifyExpression(expression);
    }

    private static Node simplifyExpression(Node expression) {
        if (expression instanceof BinaryOperationNode) {
            BinaryOperationNode binaryNode = (BinaryOperationNode) expression;
            Node left = simplifyExpression(binaryNode.getLeft());
            Node right = simplifyExpression(binaryNode.getRight());

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
                return new ConstantNode(result);
            } else {
                // Если один из операндов не является константой, то упрощаем дальше
                return new BinaryOperationNode(left, right, binaryNode.getOperator());
            }
        } else if (expression instanceof UnaryOperationNode) {
            UnaryOperationNode unaryNode = (UnaryOperationNode) expression;
            Node operand = simplifyExpression(unaryNode.getOperand());
            return new UnaryOperationNode(operand, unaryNode.getOperator().charAt(0));

        } else {
            // Если узел не является операцией, то он уже упрощен
            return expression;
        }
    }
}
