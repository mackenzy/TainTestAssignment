package se.tain;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {

    private List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public static Hand valueOf(@NotNull String initialHandString ) {

        int length = initialHandString.length();

        List<Card> cards = new ArrayList<>(length>>1);
        for (int i = 0; i < length; i+=2) {
            cards.add(Card.valueOf(
                    initialHandString.charAt(i),
                    initialHandString.charAt(i + 1)
            ));
        }

        return new Hand(cards);
    }

    @Override
    public String toString() {
        return cards.stream().map(Card::toString).collect(Collectors.joining(","));
    }
}
