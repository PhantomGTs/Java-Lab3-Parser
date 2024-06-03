package lab_v2.Nodes;

import java.util.Map;

import lab_v2.Nodes.*;
import lab_v2.Parser.*;

public interface Node
{
    double evaluate(Map<String, Double> variables);
}
