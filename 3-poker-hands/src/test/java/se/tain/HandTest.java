package se.tain;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.R2;
import static se.tain.Rank.RA;
import static se.tain.Suit.CLUBS;
import static se.tain.Suit.HEARTS;

public class HandTest {

    @Test
    public void valueOf() {
        //given
        String inputString = "2CAH";

        //then
        List<Card> cards = Hand.valueOf(inputString).getCards();

        assertTrue(2 == cards.size());

        Card card = cards.get(0);
        assertTrue(R2 == card.getRank());
        assertTrue(R2*CLUBS == card.getSuit());

        card = cards.get(1);
        assertTrue(RA == card.getRank());
        assertTrue(RA*HEARTS == card.getSuit());
    }

}