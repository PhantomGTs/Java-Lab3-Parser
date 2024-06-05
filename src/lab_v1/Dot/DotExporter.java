package lab_v1.Dot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import lab_v1.Nodes.*;

public class DotExporter {
    private int nodeId = 0;
    private final HashMap<Node, String> nodeMap = new HashMap<>();
    private final Set<Node> uniqueNodes = new HashSet<>();

    public String export(Node root) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        exportNode(root, sb);
        sb.append("}");
        return sb.toString();
    }

    // Метод для получения метки узла в зависимости от его типа
    private String getNodeLabel(Node node) {
        return switch (node) {
            case OperatorNode operatorNode -> operatorNode.getOperator() + "";
            case UnaryOperationNode unaryOperationNode -> unaryOperationNode.getOperator() + "";
            case FunctionNode functionNode -> functionNode.getFunctionName();
            case BinaryFunctionNode binFunctionNode -> binFunctionNode.getFunctionName();
            case VariableNode variableNode -> variableNode.getName();
            case ConstantNode constantNode -> Double.toString(constantNode.getValue()); // Значение, а не идентификатор
            case null, default -> node.toString();
        };
    }

    private void exportNode(Node node, StringBuilder sb) {
        if (node == null) return;

        // Проверяем, был ли этот узел уже обработан
        if (!uniqueNodes.add(node)) {
            return;
        }

        String nodeName = nodeMap.computeIfAbsent(node, k -> generateNodeId());

        // Выводим узел только один раз с меткой
        sb.append(String.format("%s [label=\"%s\"];\n", nodeName, getNodeLabel(node)));

        if (node instanceof OperatorNode) {
            OperatorNode operatorNode = (OperatorNode) node;
            String leftChildId = exportChildNode(operatorNode.getLeft(), sb);
            String rightChildId = exportChildNode(operatorNode.getRight(), sb);
            if (leftChildId != null) {
                sb.append(String.format("%s -> %s;\n", nodeName, leftChildId));
            }
            if (rightChildId != null) {
                sb.append(String.format("%s -> %s;\n", nodeName, rightChildId));
            }
        } else if (node instanceof FunctionNode) {
            FunctionNode functionNode = (FunctionNode) node;
            String argumentChildId = exportChildNode(functionNode.getArgument(), sb);
            if (argumentChildId != null) {
                sb.append(String.format("%s -> %s;\n", nodeName, argumentChildId));
            }
        } else if (node instanceof BinaryFunctionNode) {
            BinaryFunctionNode functionNode = (BinaryFunctionNode) node;
            String leftChildId = exportChildNode(functionNode.getLeft(), sb);
            String rightChildId = exportChildNode(functionNode.getRight(), sb);
            if (leftChildId != null) {
                sb.append(String.format("%s -> %s;\n", nodeName, leftChildId));
            }
            if (rightChildId != null) {
                sb.append(String.format("%s -> %s;\n", nodeName, rightChildId));
            }
        }
    }


    /*
    используем метку узла как идентификатор для связи, чтобы вместо идентификатора узла использовать его значение.
    */

    private String exportChildNode(Node child, StringBuilder sb) {
        if (child == null) return null;

        // Листовые узлы (ConstantNode и VariableNode) выводим с их значениями вместо идентификаторов
        if (child instanceof ConstantNode || child instanceof VariableNode) {
            String valueLabel = getNodeLabel(child);
            sb.append(String.format("%s [label=\"%s\"];\n", valueLabel, valueLabel));
            return valueLabel;
        }

        // Обрабатываем дочерний узел, если он не листовой
        if (!nodeMap.containsKey(child)) {
            exportNode(child, sb);
        }
        return nodeMap.get(child);
    }

    private String generateNodeId() {
        return "n" + (nodeId++);
    }
}
