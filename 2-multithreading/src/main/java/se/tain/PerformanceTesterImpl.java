package se.tain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PerformanceTesterImpl implements PerformanceTester {

    @Override
    public PerformanceTestResult runPerformanceTest(Runnable task, int executionCount, int threadPoolSize) throws InterruptedException {
        checkIncomingParams(task, executionCount, threadPoolSize);

        ExecutorService executor = Executors.newWorkStealingPool(threadPoolSize);

        try {
            List<Callable<Long>> tasks = new ArrayList<>(threadPoolSize);

            int i = executionCount;
            while(i-- > 0){
                tasks.add(() -> {
                    long start = System.currentTimeMillis();
                    task.run();
                    return System.currentTimeMillis() - start;
                });
            }

            return processResults(executor.invokeAll(tasks));
        } catch (InterruptedException ie) {
            throw ie;
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }finally {
            executor.shutdown();
        }
    }

    protected void checkIncomingParams(Runnable task, int executionCount, int threadPoolSize){
        if (task == null) {
            throw new IllegalArgumentException("Runnable task is null! ");
        }

        if(1 > executionCount){
            throw new IllegalArgumentException("ExecutionCount less than 1!");
        }

        if(1 > threadPoolSize){
            throw new IllegalArgumentException("ThreadPoolSize less than 1!");
        }
    }

    protected PerformanceTestResult processResults(List<Future<Long>> results) throws ExecutionException, InterruptedException {

        long max = -1L, min = Long.MAX_VALUE, total = 0L;
        for (Future<Long> future : results) {
            long value = future.get();

            if (value > max) {
                max = value;
            }

            if (value < min) {
                min = value;
            }

            total += value;
        }

        return new PerformanceTestResult(total, min, max);
    }

}
