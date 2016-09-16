package se.tain;

import org.jetbrains.annotations.NotNull;

import static se.tain.Rank.char2Rank;
import static se.tain.Suit.char2Suit;

public class Card {
    private final String name;
    private final long rank;
    private final long suit;

    public Card(String name, long rank, long suit) {
        this.name = name;
        this.rank = rank;
        this.suit = suit;
    }

    public long getRank() {
        return rank;
    }

    public long getSuit() {
        return suit;
    }

    public static Card valueOf(@NotNull String s) {
        return valueOf(s.charAt(0), s.charAt(1));
    }

    public static Card valueOf(char c0, char c1) {
        long rank = char2Rank(c0);
        return new Card(
                String.valueOf(c0).concat(String.valueOf(c1)),
                rank,
                rank*char2Suit(c1)
        );
    }

    @Override
    public String toString() {
        return name;
    }
}
