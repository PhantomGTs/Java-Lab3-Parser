import java.util.ArrayList;
import java.util.List;

public class Simplifier {
    public static Node simplify(Node expression) {
        List<Node> uniqueNodes = new ArrayList<>();
        return simplifyExpression(expression, uniqueNodes);
    }

    private static Node simplifyExpression(Node expression, List<Node> uniqueNodes) {
        // Проверяем, есть ли уже упрощенный узел для текущего узла
        for (Node node : uniqueNodes) {
            if (areEquivalent(expression, node)) {
                return node;
            }
        }

        Node simplifiedNode = null;

        if (expression instanceof BinaryOperationNode) {
            BinaryOperationNode binaryNode = (BinaryOperationNode) expression;
            Node left = simplifyExpression(binaryNode.getLeft(), uniqueNodes);
            Node right = simplifyExpression(binaryNode.getRight(), uniqueNodes);
            simplifiedNode = new BinaryOperationNode(left, right, binaryNode.getOperator());
        } else if (expression instanceof UnaryOperationNode) {
            UnaryOperationNode unaryNode = (UnaryOperationNode) expression;
            Node operand = simplifyExpression(unaryNode.getOperand(), uniqueNodes);
            simplifiedNode = new UnaryOperationNode(operand, unaryNode.getOperator());
        } else if (expression instanceof FunctionNode) {
            FunctionNode functionNode = (FunctionNode) expression;
            Node[] arguments = functionNode.getArguments();
            Node[] simplifiedArguments = new Node[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                simplifiedArguments[i] = simplifyExpression(arguments[i], uniqueNodes);
            }
            simplifiedNode = new FunctionNode(functionNode.getName(), simplifiedArguments);
        } else {
            simplifiedNode = expression;
        }

        // После упрощения, проверяем, не создали ли мы уже такой узел ранее
        for (Node node : uniqueNodes) {
            if (areEquivalent(simplifiedNode, node)) {
                return node;
            }
        }

        // Если такой узел еще не встречался, добавляем его в список уникальных узлов
        uniqueNodes.add(simplifiedNode);
        return simplifiedNode;
    }

    // Метод для проверки эквивалентности двух узлов
    private static boolean areEquivalent(Node node1, Node node2) {
        if (node1 == node2) {
            return true;
        }

        if (node1.getClass() != node2.getClass()) {
            return false;
        }

        // В зависимости от типа узла, определите логику сравнения
        if (node1 instanceof ConstantNode) {
            double value1 = ((ConstantNode) node1).getValue();
            double value2 = ((ConstantNode) node2).getValue();
            return value1 == value2;
        } else if (node1 instanceof VariableNode) {
            String name1 = ((VariableNode) node1).getName();
            String name2 = ((VariableNode) node2).getName();
            return name1.equals(name2);
        } else if (node1 instanceof UnaryOperationNode) {
            UnaryOperationNode unaryNode1 = (UnaryOperationNode) node1;
            UnaryOperationNode unaryNode2 = (UnaryOperationNode) node2;
            Node operand1 = unaryNode1.getOperand();
            Node operand2 = unaryNode2.getOperand();
            String operator1 = unaryNode1.getOperator();
            String operator2 = unaryNode2.getOperator();
            return operator1.equals(operator2) && areEquivalent(operand1, operand2);
        } else if (node1 instanceof BinaryOperationNode) {
            BinaryOperationNode binaryNode1 = (BinaryOperationNode) node1;
            BinaryOperationNode binaryNode2 = (BinaryOperationNode) node2;
            Node left1 = binaryNode1.getLeft();
            Node left2 = binaryNode2.getLeft();
            Node right1 = binaryNode1.getRight();
            Node right2 = binaryNode2.getRight();
            String operator1 = binaryNode1.getOperator();
            String operator2 = binaryNode2.getOperator();
            return operator1.equals(operator2) && areEquivalent(left1, left2) && areEquivalent(right1, right2);
        } else if (node1 instanceof FunctionNode) {
            FunctionNode functionNode1 = (FunctionNode) node1;
            FunctionNode functionNode2 = (FunctionNode) node2;
            String name1 = functionNode1.getName();
            String name2 = functionNode2.getName();
            if (!name1.equals(name2)) {
                return false;
            }
            Node[] arguments1 = functionNode1.getArguments();
            Node[] arguments2 = functionNode2.getArguments();
            if (arguments1.length != arguments2.length) {
                return false;
            }
            for (int i = 0; i < arguments1.length; i++) {
                if (!areEquivalent(arguments1[i], arguments2[i])) {
                    return false;
                }
            }
            return true;
        } else {
            return node1.equals(node2);
        }
    }
}
