package se.tain.util;

import java.util.stream.LongStream;

public abstract class LongUtils {

    public static long sum(long... longs){
        return LongStream.of(longs).sum();
    }

    public static long orSum(long... longs){
        return LongStream.of(longs).reduce((l1, l2)->l1|l2).getAsLong();
    }
}
