import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        Parser parser = new Parser();
        //Node f = parser.parse("x + 2 * (y - 3)");
        Node f = parser.parse("cos(x)");
        Map<String, Double> variables = Map.of("x", 1.0, "y", 1.0);
        double result = f.evaluate(variables);
        System.out.println("Результат: " + result);

        Simplifier simplifier = new Simplifier();
        Node g = simplifier.simplify(f);
        System.out.println("Упращенное выражение: " + g.evaluate(variables));

        DotExporter exporter = new DotExporter();
        String dotFormat = exporter.export(f);
        System.out.println(dotFormat);
    }
}