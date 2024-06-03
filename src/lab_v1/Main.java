package lab_v1;

import java.util.*;

public class Main {
    public static void main(String[] args)
    {
//        Parser parser = new Parser();
//        Node f = parser.parse("(x + 1) * (x + 1) + (x + 1) * 3 + (x + 1) * (x + 1)"); // Вызов функции pow
//        Map<String, Double> variables = Map.of("x", 2.0, "y", 2.0); // Задаем значения переменных
//        double result = f.evaluate(variables); // Вычисляем результат
//        System.out.println("Результат: " + result); // Выводим результат
//
//        Simplifier simplifier = new Simplifier();
//        Node g = simplifier.simplify(f);
//        System.out.println("Упрощенное выражение: " + g.evaluate(variables));
//
//        DotExporter exporter = new DotExporter();
//        String dotFormat = exporter.export(g); // Используем упрощенное дерево
//        System.out.println(dotFormat);


        Parser parser = new Parser();
        Node f = parser.parse("(x + 1) * (x + 1)");
        Node g = Simplifier.simplify(f);

        Map<String, Double> variables = Map.of("x", 2.0);

        double resultF = f.evaluate(variables);
        double resultG = g.evaluate(variables);

        System.out.println("Вычисленное значение f: " + resultF);
        System.out.println("Вычисленное значение g: " + resultG);

        // Создаем объект DotExporter после упрощения выражения
        DotExporter dotG = new DotExporter();

        System.out.println("DOT-граф f:");
        System.out.println(dotG.export(f));

        System.out.println("DOT-граф g:");
        System.out.println(dotG.export(g));
    }
}
