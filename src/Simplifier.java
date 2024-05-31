import java.util.HashMap;
import java.util.Map;

public class Simplifier {
    private Map<String, Node> seenNodes;

    public Simplifier() {
        this.seenNodes = new HashMap<>();
    }

    public Node simplify(Node root) {
        return simplifyNode(root);
    }

    private Node simplifyNode(Node node) {
        if (node == null) {
            return null;
        }

        String nodeKey = getNodeKey(node);
        if (seenNodes.containsKey(nodeKey)) {
            return seenNodes.get(nodeKey);
        }

        if (node instanceof OperatorNode) {
            OperatorNode opNode = (OperatorNode) node;
            Node left = simplifyNode(opNode.getLeft());
            Node right = simplifyNode(opNode.getRight());
            Node newNode;
            if (left != null && right != null) {
                newNode = new OperatorNode(left, right, opNode.getOperator());
            } else if (left != null) {
                newNode = left;
            } else if (right != null) {
                newNode = right;
            } else {
                newNode = node; // Оставляем без изменений, если нет дочерних узлов
            }
            seenNodes.put(nodeKey, newNode);
            return newNode;
        } else if (node instanceof FunctionNode) {
            FunctionNode funcNode = (FunctionNode) node;
            Node arg = simplifyNode(funcNode.getArgument());
            Node newNode = new FunctionNode(arg, funcNode.getFunctionName());
            seenNodes.put(nodeKey, newNode);
            return newNode;
        } else if (node instanceof BinaryFunctionNode) {
            BinaryFunctionNode funcNode = (BinaryFunctionNode) node;
            Node leftArg = simplifyNode(funcNode.getLeftArgument());
            Node rightArg = simplifyNode(funcNode.getRightArgument());
            Node newNode = new BinaryFunctionNode(leftArg, rightArg, funcNode.getFunctionName());
            seenNodes.put(nodeKey, newNode);
            return newNode;
        } else {
            seenNodes.put(nodeKey, node);
            return node;
        }
    }

    private String getNodeKey(Node node) {
        if (node instanceof ConstantNode) {
            return "C:" + ((ConstantNode) node).getValue();
        } else if (node instanceof VariableNode) {
            return "V:" + ((VariableNode) node).getName();
        } else if (node instanceof OperatorNode) {
            OperatorNode opNode = (OperatorNode) node;
            return "O:" + opNode.getOperator() + "(" + getNodeKey(opNode.getLeft()) + "," + getNodeKey(opNode.getRight()) + ")";
        } else if (node instanceof FunctionNode) {
            FunctionNode funcNode = (FunctionNode) node;
            return "F:" + funcNode.getFunctionName() + "(" + getNodeKey(funcNode.getArgument()) + ")";
        } else if (node instanceof BinaryFunctionNode) {
            BinaryFunctionNode funcNode = (BinaryFunctionNode) node;
            return "BF:" + funcNode.getFunctionName() + "(" + getNodeKey(funcNode.getLeftArgument()) + "," + getNodeKey(funcNode.getRightArgument()) + ")";
        } else {
            throw new IllegalArgumentException("Unknown node type");
        }
    }
}
