package parser;


import java.util.*;

public class Parser {
    private String expression;
    private int index;

    public Parser(String expression) {
        this.expression = expression.replaceAll("\\s", ""); // Удаление всех пробелов из выражения
        this.index = 0;
    }

    public Expression parse() {
        return parseExpression();
    }

    private Expression parseExpression() {
        List<Expression> terms = new ArrayList<>();
        terms.add(parseTerm());

        while (index < expression.length()) {
            char operator = expression.charAt(index);
            if (operator == '+' || operator == '-') {
                index++;
                terms.add(parseTerm());
            } else {
                break;
            }
        }

        return new Expression(terms);
    }

    private Expression parseTerm() {
        List<Expression> factors = new ArrayList<>();
        factors.add(parseFactor());

        while (index < expression.length()) {
            char operator = expression.charAt(index);
            if (operator == '*' || operator == '/') {
                index++;
                factors.add(parseFactor());
            } else {
                break;
            }
        }

        if (factors.size() == 1) {
            return factors.get(0);
        } else {
            return new Expression(factors);
        }
    }

    private Expression parseFactor() {
        char currentChar = expression.charAt(index);

        if (currentChar == '(') {
            index++;
            Expression exp = parseExpression(); // Переименована переменная expression
            index++;
            return exp;
        } else if (Character.isDigit(currentChar)) {
            return parseNumber();
        } else if (Character.isLetter(currentChar)) {
            return parseVariableOrFunction();
        } else {
            throw new IllegalArgumentException("Unexpected character: " + currentChar);
        }
    }

    private Expression parseNumber() {
        StringBuilder number = new StringBuilder();

        while (index < expression.length() && (Character.isDigit(expression.charAt(index)) || expression.charAt(index) == '.')) {
            number.append(expression.charAt(index));
            index++;
        }

        return new Expression(Double.parseDouble(number.toString()));
    }

    private Expression parseVariableOrFunction() {
        StringBuilder identifier = new StringBuilder();

        while (index < expression.length() && (Character.isLetter(expression.charAt(index)) || Character.isDigit(expression.charAt(index)))) {
            identifier.append(expression.charAt(index));
            index++;
        }

        if (index < expression.length() && expression.charAt(index) == '(') {
            index++;
            List<Expression> args = new ArrayList<>();
            while (expression.charAt(index) != ')') {
                args.add(parseExpression());
                if (expression.charAt(index) == ',') {
                    index++;
                }
            }
            index++;
            return new Expression(identifier.toString(), args);
        } else {
            String varName = identifier.toString();
            switch (varName) {
                case "E":
                    return new Expression(Math.E);
                case "PI":
                    return new Expression(Math.PI);
                case "PHI":
                    return new Expression(1.618033988749895);
                default:
                    return new Expression(varName);
            }
        }
    }
}