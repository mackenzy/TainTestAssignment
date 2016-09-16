package se.tain;

public abstract class Rank {

    public static final long R2 =    1L;
    public static final long R3 =    1L << 4;
    public static final long R4 =    1L << 8;
    public static final long R5 =    1L << 12;
    public static final long R6 =    1L << 16;
    public static final long R7 =    1L << 20;
    public static final long R8 =    1L << 24;
    public static final long R9 =    1L << 28;
    public static final long RT =    1L << 32;
    public static final long RJ =    1L << 36;
    public static final long RQ =    1L << 40;
    public static final long RK =    1L << 44;
    public static final long RA =    1L << 48;

    public static long char2Rank(char letter) {
        switch (letter) {
            case '2': return R2;
            case '3': return R3;
            case '4': return R4;
            case '5': return R5;
            case '6': return R6;
            case '7': return R7;
            case '8': return R8;
            case '9': return R9;
            case 'T': return RT;
            case 'J': return RJ;
            case 'Q': return RQ;
            case 'K': return RK;
            case 'A': return RA;
        }

        throw new IllegalArgumentException("Unexpected rank!");
    }
}
