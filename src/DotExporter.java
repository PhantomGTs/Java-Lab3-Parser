public class DotExporter {
    private int nodeId = 0;

    public String export(Node root) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        exportNode(root, sb, generateNodeId());
        sb.append("}");
        return sb.toString();
    }

    private void exportNode(Node node, StringBuilder sb, String nodeName) {
        if (node instanceof ConstantNode) {
            ConstantNode constantNode = (ConstantNode) node;
            sb.append(String.format("%s [label=\"%s\"];\n", nodeName, constantNode.getValue()));
        } else if (node instanceof VariableNode) {
            VariableNode variableNode = (VariableNode) node;
            sb.append(String.format("%s [label=\"%s\"];\n", nodeName, variableNode.getName()));
        } else if (node instanceof OperatorNode) {
            OperatorNode operatorNode = (OperatorNode) node;
            sb.append(String.format("%s [label=\"%s\"];\n", nodeName, operatorNode.getOperator()));
            String leftChildId = generateNodeId();
            String rightChildId = generateNodeId();
            exportNode(operatorNode.getLeft(), sb, leftChildId);
            exportNode(operatorNode.getRight(), sb, rightChildId);
            sb.append(String.format("%s -> %s;\n", nodeName, leftChildId));
            sb.append(String.format("%s -> %s;\n", nodeName, rightChildId));
        } else if (node instanceof FunctionNode) {
            FunctionNode functionNode = (FunctionNode) node;
            sb.append(String.format("%s [label=\"%s\"];\n", nodeName, functionNode.getFunctionName()));
            String argumentChildId = generateNodeId();
            exportNode(functionNode.getArgument(), sb, argumentChildId);
            sb.append(String.format("%s -> %s;\n", nodeName, argumentChildId));
        } else if (node instanceof BinaryFunctionNode) {
            BinaryFunctionNode functionNode = (BinaryFunctionNode) node;
            sb.append(String.format("%s [label=\"%s\"];\n", nodeName, functionNode.getFunctionName()));
            String leftChildId = generateNodeId();
            String rightChildId = generateNodeId();
            exportNode(functionNode.getLeft(), sb, leftChildId);
            exportNode(functionNode.getRight(), sb, rightChildId);
            sb.append(String.format("%s -> %s;\n", nodeName, leftChildId));
            sb.append(String.format("%s -> %s;\n", nodeName, rightChildId));
        }
    }

    private String generateNodeId() {
        return "n" + (nodeId++);
    }
}
