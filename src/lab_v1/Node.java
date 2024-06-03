package lab_v1;

import java.util.Map;

public interface Node {
    double evaluate(Map<String, Double> variables);
    String getName();
    double getValue();
    Node getLeft();
    Node getRight();
    String getOperator();
    String getFunctionName();
    Node getArgument();
}
