package parser;

import java.util.*;
import java.lang.*;

public class Expression {
    private List<Expression> terms;
    private double value;
    private String variable;
    private Expression argument1;
    private Expression argument2;
    private boolean isFunction;

    public Expression(double value) {
        this.value = value;
    }

    public Expression(String variable) {
        this.variable = variable;
    }

    public Expression(String variable, Expression argument1) {
        this.variable = variable;
        this.argument1 = argument1;
        this.isFunction = true;
    }

    public Expression(String variable, Expression argument1, Expression argument2) {
        this.variable = variable;
        this.argument1 = argument1;
        this.argument2 = argument2;
        this.isFunction = true;
    }

    public Expression(List<Expression> terms) {
        this.terms = terms;
    }

    public List<Expression> getTerms() {
        return terms;
    }

    public double getValue() {
        return value;
    }

    public String getVariable() {
        return variable;
    }

    public Expression getArgument1() {
        return argument1;
    }

    public Expression getArgument2() {
        return argument2;
    }

    public boolean isFunction() {
        return isFunction;
    }

    public double evaluate(Map<String, Double> variables) {
        if (isFunction) {
            double argValue1 = argument1.evaluate(variables);
            if (variable.equals("pow")) {
                double argValue2 = argument2.evaluate(variables);
                return Math.pow(argValue1, argValue2);
            }
            switch (variable) {
                case "sin":
                    return Math.sin(argValue1);
                case "cos":
                    return Math.cos(argValue1);
                case "tan":
                    return Math.tan(argValue1);
                default:
                    throw new IllegalArgumentException("Unknown function: " + variable);
            }
        } else if (terms != null) {
            double result = 0.0;
            for (Expression term : terms) {
                result += term.evaluate(variables);
            }
            return result;
        } else if (variable != null) {
            return variables.getOrDefault(variable, 0.0);
        } else {
            return value;
        }
    }

    public String toString() {
        if (isFunction) {
            if (variable.equals("pow")) {
                return variable + "(" + argument1.toString() + ", " + argument2.toString() + ")";
            }
            return variable + "(" + argument1.toString() + ")";
        } else if (terms != null) {
            StringBuilder builder = new StringBuilder();
            for (Expression term : terms) {
                builder.append(term.toString());
            }
            return builder.toString();
        } else if (variable != null) {
            return variable;
        } else {
            return Double.toString(value);
        }
    }
}
