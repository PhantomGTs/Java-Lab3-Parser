package lab_v1;

import lab_v1.Dot.DotExporter;
import lab_v1.Nodes.Node;
import lab_v1.Parser.Parser;
import lab_v1.Simlifire.Simplifier;

import java.util.Map;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Parser parser = new Parser();
        //Node f = parser.parse("pow(2, x)");
        //Node f = parser.parse("(3.0 + x)");
        Node f = parser.parse("(x + 1) * (x - 1) + (x + 1) * (x + 1) + 1)");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Double> resultF = executor.submit(() -> f.evaluate(Map.of("x", 2.0)));
        Future<Double> resultG = executor.submit(() -> Simplifier.simplify(f).evaluate(Map.of("x", 2.0)));

        // Ожидание завершения задач
        double fValue = resultF.get();
        double gValue = resultG.get();

        executor.shutdown();

        System.out.println("Вычисленное значение f: " + fValue);
        System.out.println("Вычисленное значение g: " + gValue);

        DotExporter dotG = new DotExporter();

        System.out.println("DOT-граф f:");
        System.out.println(dotG.export(f));

        System.out.println("DOT-граф g:");
        System.out.println(dotG.export(Simplifier.simplify(f)));
    }
}
