import java.util.*;

import parser.*;
import dot.*;

public class Main {
    public static void main(String[] args) {

        // Создание выражения из строки
        String expressionStr = "x * (x + 1) * (x + 1)";
        System.out.println("Original expression: " + expressionStr);

        // Парсинг выражения
        Parser parser = new Parser(expressionStr);
        Expression expression = parser.parse();
        System.out.println("Parsed expression: " + expression.toString());

        // Создание карты переменных
        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 5.0);

        // Упрощение выражения
        ExpressionSimplifier simplifier = new ExpressionSimplifier();
        Expression simplifiedExpression = simplifier.simplify(expression, variables);
        System.out.println("Simplified expression: " + simplifiedExpression.toString());

        // Вычисление значения упрощенного выражения
        double result = simplifiedExpression.evaluate(variables);
        System.out.println("Result for x=5: " + result);

/*        //String expression = "cos(x) + sin(x)";
        String expression = "pow(x, y)";
        //String expression = "Pi(";
        Parser parser = new Parser(expression);
        Expression parsedExpression = parser.parse();
        System.out.println("Parsed expression: " + parsedExpression.toString());

        // Пример вычисления значения выражения для заданных переменных
        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 2.0);
        variables.put("y", 2.0);
        double result = parsedExpression.evaluate(variables);
        System.out.println("Result for x= " + variables.get("x") + ", y=" + variables.get("y") + ": " + result);*/
    }
}