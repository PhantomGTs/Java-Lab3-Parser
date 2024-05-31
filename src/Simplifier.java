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
            Node newNode = new OperatorNode(left, right, opNode.getOperator());
            seenNodes.put(nodeKey, newNode);
            return newNode;
        } else if (node instanceof FunctionNode) {
            FunctionNode funcNode = (FunctionNode) node;
            Node arg = simplifyNode(funcNode.getArgument());
            Node newNode = new FunctionNode(arg, funcNode.getFunctionName());
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
        } else {
            throw new IllegalArgumentException("Unknown node type");
        }
    }
}
