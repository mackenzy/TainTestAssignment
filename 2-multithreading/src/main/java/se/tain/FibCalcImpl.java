package se.tain;

public class FibCalcImpl implements FibCalc {

    @Override
    public long fib(int n) {
        return fibIter(0L, 1L, 0L, 1L, n);
    }

    private long fibIter(long a, long b, long p, long q, int n) {
        if (n == 0) {
            return a;
        }

        if ((n & 1) == 0) {
            return fibIter(a, b, p*p + q*q, 2 * p*q + q*q, n >> 1);
        }

        return fibIter(a*p + b*q, a*q + b*q + b*p, p, q, n-1);
    }
}
