package se.tain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PerformanceTesterImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void checkIncomingParams_Ex_Task() {
        //given
        Runnable task = null;
        int executionCount = 1, threadPoolSize = 1;
        PerformanceTesterImpl target = new PerformanceTesterImpl();

        //then
        target.checkIncomingParams(task, executionCount, threadPoolSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncomingParams_Ex_ExecutionCount() {
        //given
        Runnable task = ()->{};
        int executionCount = 0, threadPoolSize = 1;
        PerformanceTesterImpl target = new PerformanceTesterImpl();

        //then
        target.checkIncomingParams(task, executionCount, threadPoolSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncomingParams_Ex_ThreadPoolSize() {
        //given
        Runnable task = ()->{};
        int executionCount = 1, threadPoolSize = 0;
        PerformanceTesterImpl target = new PerformanceTesterImpl();

        //then
        target.checkIncomingParams(task, executionCount, threadPoolSize);
    }

    @Test
    public void checkIncomingParams() {
        //given
        Runnable task = ()->{};
        int executionCount = 1, threadPoolSize = 1;
        PerformanceTesterImpl target = new PerformanceTesterImpl();

        //then
        target.checkIncomingParams(task, executionCount, threadPoolSize);
    }

    @Test
    public void processResults() throws ExecutionException, InterruptedException {
        //given
        PerformanceTesterImpl target = new PerformanceTesterImpl();

        Future<Long> f = mock(Future.class);
        List<Future<Long>> list = Arrays.asList(f, f, f, f, f, f, f, f, f);

        //when
        when(f.get()).thenReturn(2L, 1L, 4L, 0L, 3L, 5L, 6L, 10L, 1L);

        //then
        PerformanceTestResult result = target.processResults(list);

        assertEquals(0L, result.getMinTime());
        assertEquals(10L, result.getMaxTime());
        assertEquals(32L, result.getTotalTime());
    }

}