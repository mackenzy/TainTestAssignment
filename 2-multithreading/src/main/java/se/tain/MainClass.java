package se.tain;

public class MainClass {

    public static void main(String[] args) throws InterruptedException {

        int n = Integer.valueOf(args[0]);
        int executionCount = Integer.valueOf(args[1]);
        int threadPoolSize = Integer.valueOf(args[2]);

        FibCalc fibCalc = new FibCalcImpl();
        PerformanceTester tester = new PerformanceTesterImpl();

        PerformanceTestResult ptr = tester.runPerformanceTest(()->fibCalc.fib(n), executionCount, threadPoolSize);


        System.out.printf("Total: %10d ", ptr.getTotalTime());
        System.out.printf("Min: %10d ", ptr.getMinTime());
        System.out.printf("Max: %10d%n", ptr.getMaxTime());
    }
}
