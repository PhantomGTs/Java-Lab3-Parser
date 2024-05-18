package parser;

import java.util.*;


public class ExpressionSimplifier {
    public Expression simplify(Expression expression) {
        return simplify(expression, Collections.emptyMap());
    }

    public Expression simplify(Expression expression, Map<String, Double> variables) {
        if (expression.isFunction()) {
            if ("pow".equals(expression.getVariable())) {
                List<Expression> simplifiedArgs = new ArrayList<>();
                for (Expression arg : expression.getTerms()) {
                    simplifiedArgs.add(simplify(arg, variables));
                }
                return new Expression(expression.getVariable(), simplifiedArgs.get(0), simplifiedArgs.get(1));
            } else {
                List<Expression> singleArg = new ArrayList<>();
                singleArg.add(simplify(expression.getArgument1(), variables));
                return new Expression(expression.getVariable(), singleArg.get(0));
            }
        } else if (expression.getTerms() != null) {
            return simplifyTerms(expression.getTerms(), variables);
        } else {
            return expression;
        }
    }

    private Expression simplifyTerms(List<Expression> terms, Map<String, Double> variables) {
        Map<String, Double> termValues = new LinkedHashMap<>();

        // Вычисляем значения для каждого терма
        for (Expression term : terms) {
            double value = term.evaluate(variables);
            String termStr = computeTermString(term, variables); // Вычисляем строковое представление терма
            termValues.put(termStr, termValues.getOrDefault(termStr, 0.0) + value); // Используем значение по умолчанию 0.0
        }

        // Создаем список упрощенных термов
        List<Expression> simplifiedTerms = new ArrayList<>();
        for (Map.Entry<String, Double> entry : termValues.entrySet()) {
            double value = entry.getValue();
            if (Math.abs(value) > 0.000001) {
                simplifiedTerms.add(new Expression(value));
            }
        }

        // Если у нас остались какие-то упрощенные термы, возвращаем новое выражение, иначе возвращаем ноль
        Expression result = simplifiedTerms.isEmpty() ? new Expression(0.0) : (simplifiedTerms.size() == 1 ? simplifiedTerms.get(0) : new Expression(simplifiedTerms));
        System.out.println("Simplified terms: " + result);
        return result;
    }

    private String computeTermString(Expression term, Map<String, Double> variables) {
        StringBuilder termStr = new StringBuilder();
        for (Map.Entry<String, Double> entry : variables.entrySet()) {
            String variable = entry.getKey();
            double value = entry.getValue();
            termStr.append(variable).append(value);
        }
        return termStr.toString();
    }
}