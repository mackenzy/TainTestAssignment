package se.tain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FibCalcImplTest {


    @Test
    public void fib() {
        //given
        FibCalc target = new FibCalcImpl();

        //then
        assertEquals(1L,                    target.fib(1));
        assertEquals(1L,                    target.fib(2));
        assertEquals(2L,                    target.fib(3));
        assertEquals(3L,                    target.fib(4));
        assertEquals(5L,                    target.fib(5));
        assertEquals(39088169L,             target.fib(38));
        assertEquals(72723460248141L,       target.fib(68));
        assertEquals(1293530146158671551L,  target.fib(94));
    }
}