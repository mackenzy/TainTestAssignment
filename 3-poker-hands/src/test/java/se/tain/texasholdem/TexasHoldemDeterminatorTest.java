package se.tain.texasholdem;

import org.junit.Test;
import se.tain.HandRankName;
import se.tain.Player;
import se.tain.texasholdem.evaluators.*;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static se.tain.Card.valueOf;

public class TexasHoldemDeterminatorTest {


    @Test
    public void testgetStraightWinners() {
        //given
        TexasHoldemDeterminator target = new TexasHoldemDeterminator(new RoyalFlushEvaluator(), new FourOfAKindEvaluator(), new StraightFlushEvaluator(), new FullHouseEvaluator(), new FlushEvaluator(), new StraightEvaluator(), new ThreeOfAKindEvaluator(), new TwoPairEvaluator(), new PairEvaluator(), new HighCardEvaluator(), new KickerEvaluator());
        Player p1 = new Player( "Player1", "7SJC");
        Player p2 = new Player( "Player2", "5D6C");

        List<Player> players = asList(p1, p2);

        target.addCardToTableState(valueOf("9C"));
        target.addCardToTableState(valueOf("8D"));
        target.addCardToTableState(valueOf("3C"));
        target.addCardToTableState(valueOf("7C"));
        target.addCardToTableState(valueOf("TH"));

        //then
        List<Player> plyrs = target.defineStraightWinners(players);

        assertTrue(1 == plyrs.size());
        assertEquals(p1, plyrs.get(0));

    }

    @Test
    public void testgetOnePairWinners() {
        //given
        TexasHoldemDeterminator target = new TexasHoldemDeterminator(new RoyalFlushEvaluator(), new FourOfAKindEvaluator(), new StraightFlushEvaluator(), new FullHouseEvaluator(), new FlushEvaluator(), new StraightEvaluator(), new ThreeOfAKindEvaluator(), new TwoPairEvaluator(), new PairEvaluator(), new HighCardEvaluator(), new KickerEvaluator());
        Player p1 = new Player( "Player1", "6S2C");
        Player p2 = new Player( "Player2", "8HQH");

        List<Player> players = asList(p1, p2);

        target.addCardToTableState(valueOf("JH"));
        target.addCardToTableState(valueOf("4C"));
        target.addCardToTableState(valueOf("7H"));
        target.addCardToTableState(valueOf("9C"));
        target.addCardToTableState(valueOf("9S"));

        //then
        List<Player> plyrs = target.definePairWinners(players);

        assertTrue(1 == plyrs.size());
        assertEquals(p2, plyrs.get(0));

    }

    @Test
    public void testgetFourOfAKindWinners() {
        //given
        TexasHoldemDeterminator target = new TexasHoldemDeterminator(new RoyalFlushEvaluator(), new FourOfAKindEvaluator(), new StraightFlushEvaluator(), new FullHouseEvaluator(), new FlushEvaluator(), new StraightEvaluator(), new ThreeOfAKindEvaluator(), new TwoPairEvaluator(), new PairEvaluator(), new HighCardEvaluator(), new KickerEvaluator());
        Player p1 = new Player( "Player1", "4S2H");
        Player p2 = new Player( "Player2", "3D2C");

        List<Player> players = asList(p1, p2);

        target.addCardToTableState(valueOf("QS"));
        target.addCardToTableState(valueOf("QH"));
        target.addCardToTableState(valueOf("QD"));
        target.addCardToTableState(valueOf("QC"));
        target.addCardToTableState(valueOf("5H"));

        //then
        List<Player> plyrs = target.defineFourOfAKindWinners(players);

        assertTrue(2 == plyrs.size());
        assertEquals(p1, plyrs.get(0));
        assertEquals(p2, plyrs.get(1));

    }

    @Test
    public void testDefine_3Players() {
        //given
        TexasHoldemDeterminator target = new TexasHoldemDeterminator(new RoyalFlushEvaluator(), new FourOfAKindEvaluator(), new StraightFlushEvaluator(), new FullHouseEvaluator(), new FlushEvaluator(), new StraightEvaluator(), new ThreeOfAKindEvaluator(), new TwoPairEvaluator(), new PairEvaluator(), new HighCardEvaluator(), new KickerEvaluator());
        Player p1 = new Player( "Player1", "9HAS");
        Player p2 = new Player( "Player2", "7SJH");
        Player p3 = new Player( "Player3", "4HTD");

        List<Player> players = asList(p1, p2, p3);

        target.addCardToTableState(valueOf("4C"));
        target.addCardToTableState(valueOf("4D"));
        target.addCardToTableState(valueOf("KS"));
        target.addCardToTableState(valueOf("4S"));
        target.addCardToTableState(valueOf("8C"));
        target.addCardToTableState(valueOf("6D"));
        target.addCardToTableState(valueOf("9C"));
        target.addCardToTableState(valueOf("AC"));
        target.addCardToTableState(valueOf("7D"));
        target.addCardToTableState(valueOf("6S"));
        target.addCardToTableState(valueOf("JD"));
        target.addCardToTableState(valueOf("JS"));
        target.addCardToTableState(valueOf("9S"));
        target.addCardToTableState(valueOf("QH"));
        target.addCardToTableState(valueOf("3C"));
        target.addCardToTableState(valueOf("2S"));
        target.addCardToTableState(valueOf("5H"));
        target.addCardToTableState(valueOf("6C"));
        target.addCardToTableState(valueOf("AD"));
        target.addCardToTableState(valueOf("QS"));
        target.addCardToTableState(valueOf("3D"));
        target.addCardToTableState(valueOf("3S"));
        target.addCardToTableState(valueOf("8H"));
        target.addCardToTableState(valueOf("QC"));
        target.addCardToTableState(valueOf("5S"));
        target.addCardToTableState(valueOf("2H"));
        target.addCardToTableState(valueOf("KD"));
        target.addCardToTableState(valueOf("KC"));
        target.addCardToTableState(valueOf("3H"));
        target.addCardToTableState(valueOf("5C"));
        target.addCardToTableState(valueOf("2C"));
        target.addCardToTableState(valueOf("5D"));
        target.addCardToTableState(valueOf("8S"));
        target.addCardToTableState(valueOf("JC"));
        target.addCardToTableState(valueOf("QD"));
        target.addCardToTableState(valueOf("AH"));

        //then
        List<Player> plyrs = target.define(valueOf("TC"), players);

        assertTrue(3 == plyrs.size());
        assertEquals(HandRankName.ROYAL_FLUSH, plyrs.get(0).getWonHandRankName());
        assertEquals(HandRankName.ROYAL_FLUSH, plyrs.get(1).getWonHandRankName());
        assertEquals(HandRankName.ROYAL_FLUSH, plyrs.get(2).getWonHandRankName());

    }

}