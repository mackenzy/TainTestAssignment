package se.tain;

public interface PerformanceTester {

    /**
     * Runs a performance test of the given task.
     *
     * @param task           which task to do performance tests on
     * @param executionCount how many times the task should be executed in total
     * @param threadPoolSize how many threads to use
     */
    PerformanceTestResult runPerformanceTest(
            Runnable task,
            int executionCount,
            int threadPoolSize ) throws InterruptedException;
}
