package dot;

import parser.*;

public class DOTConverter {
    private int nodeId;

    public DOTConverter() {
        nodeId = 0;
    }

    public String convert(Expression expression) {
        StringBuilder dot = new StringBuilder();
        dot.append("digraph Expression {\n");
        nodeId = 0;
        appendNode(dot, expression);
        dot.append("}\n");
        return dot.toString();
    }

    private void appendNode(StringBuilder dot, Expression expression) {
        if (expression.isFunction) {
            String functionName = expression.variable;
            if (functionName.equals("pow")) {
                appendNode(dot, expression.argument1);
                appendNode(dot, expression.argument2);
                int parentId = nodeId++;
                int leftChildId = nodeId++;
                int rightChildId = nodeId++;
                dot.append(parentId).append(" [label=\"").append(functionName).append("\"];\n");
                dot.append(parentId).append(" -> ").append(leftChildId).append(";\n");
                dot.append(parentId).append(" -> ").append(rightChildId).append(";\n");
            } else {
                appendNode(dot, expression.argument1);
                int parentId = nodeId++;
                int childId = nodeId++;
                dot.append(parentId).append(" [label=\"").append(functionName).append("\"];\n");
                dot.append(parentId).append(" -> ").append(childId).append(";\n");
            }
        } else if (expression.terms != null) {
            int parentId = nodeId++;
            dot.append(parentId).append(" [label=\"+\"];\n");
            for (Expression term : expression.terms) {
                appendNode(dot, term);
                int childId = nodeId++;
                dot.append(parentId).append(" -> ").append(childId).append(";\n");
            }
        } else {
            double value = expression.value;
            int parentId = nodeId++;
            dot.append(parentId).append(" [label=\"").append(value).append("\"];\n");
        }
    }
}
