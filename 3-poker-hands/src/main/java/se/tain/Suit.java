package se.tain;

public abstract class Suit {
    public static final byte HEARTS    = 1;
    public static final byte DIAMONDS  = 2;
    public static final byte CLUBS     = 4;
    public static final byte SPADES    = 8;

    public static byte char2Suit(char letter){
        switch (letter){
            case 'H' : return HEARTS;
            case 'D' : return DIAMONDS;
            case 'C' : return CLUBS;
            case 'S' : return SPADES;
        }

        throw new IllegalArgumentException("Unexpected suit!");
    }
}
