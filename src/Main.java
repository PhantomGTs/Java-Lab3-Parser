import java.util.*;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        Node f = parser.parse("(x + 1) * (x + 1) + (x + 1) * 3 + (x + 1) * (x + 1)"); // Вызов функции pow
        Map<String, Double> variables = Map.of("x", 2.0, "y", 2.0); // Задаем значения переменных
        double result = f.evaluate(variables); // Вычисляем результат
        System.out.println("Результат: " + result); // Выводим результат

        Simplifier simplifier = new Simplifier();
        Node g = simplifier.simplify(f);
        System.out.println("Упрощенное выражение: " + g.evaluate(variables));

        DotExporter exporter = new DotExporter();
        String dotFormat = exporter.export(g); // Используем упрощенное дерево
        System.out.println(dotFormat);
    }
}
