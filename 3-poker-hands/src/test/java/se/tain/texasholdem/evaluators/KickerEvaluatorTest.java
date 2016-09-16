package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.*;
import static se.tain.util.LongUtils.orSum;

public class KickerEvaluatorTest {

    @Test
    public void checkKickers4OfAKind_highCardInHands() {
        //given
        KickerEvaluator target = new KickerEvaluator();

        //then
        assertTrue(RA == target.checkKickers4OfAKind(
                4 * RK,
                orSum(RA, RK),
                orSum(RK, RK, RK, RT, R3, R2))
        );
    }

    @Test
    public void checkKickers4OfAKind_highCardOnTable() {
        //given
        KickerEvaluator target = new KickerEvaluator();

        long fourKind = 4 * RJ;
        long[] hole = {R9, RJ};
        long[] table = {RJ, RJ, RJ, RK, R3, R2};

        //then
        assertTrue(RK == target.checkKickers4OfAKind(fourKind, orSum(hole), orSum(table)));
    }

    @Test
    public void checkKickers3OfAKind_highCardInHands() {
        //given
        KickerEvaluator target = new KickerEvaluator();

        long threeKind = 3 * RK;
        long[] hole = {RA, RK};
        long[] table = {RK, RK, RT, R3, R2};

        //then
        assertTrue(RA + RT == target.checkKickers3OfAKind(threeKind, orSum(hole), orSum(table)));
    }

    @Test
    public void checkKickers3OfAKind_highCardOnTable() {
        //given
        KickerEvaluator target = new KickerEvaluator();

        long threeKind = 3 * RQ;
        long[] hole = {R9, RQ, RQ};
        long[] table = {RQ, RK, RT, R3, R2};

        //then
        assertTrue(RK + RT == target.checkKickers3OfAKind(threeKind, orSum(hole), orSum(table)));
    }

    @Test
    public void checkKickers2Pair_highCardInHands() {
        //given
        KickerEvaluator target = new KickerEvaluator();

        long twoPair = 2 * RQ + 2 * R8;
        long[] hole = {RJ, RQ, RQ};
        long[] table = {RT, R8, R8, R3, R2};

        //then
        assertTrue(RJ == target.checkKickers2Pair(twoPair, orSum(hole), orSum(table)));
    }

    @Test
    public void checkKickers2Pair_highCardOnTable() {
        //given
        KickerEvaluator target = new KickerEvaluator();

        long twoPair = 2 * RJ + 2 * RA;
        long[] hole = {RJ, R7};
        long[] table = {RT, RA, RA, RJ, R3, R2};

        //then
        assertTrue(RT == target.checkKickers2Pair(twoPair, orSum(hole), orSum(table)));
    }

    @Test
    public void checkKickers2Pair_OnePair_highCardInHands() {
        //given
        KickerEvaluator target = new KickerEvaluator();

        long twoPair = 2 * RJ;
        long[] hole = {RJ, RT};
        long[] table = {R7, R8, RJ, R3, R2};

        //then
        assertTrue(RT == target.checkKickers2Pair(twoPair, orSum(hole), orSum(table)));
    }

    @Test
    public void checkKickers2Pair_OnePair_highCardOnTable() {
        //given
        KickerEvaluator target = new KickerEvaluator();

        long twoPair = 2 * RT;
        long[] hole = {RT, R9};
        long[] table = {R7, RQ, RT, R3, R2};

        //then
        assertTrue(RQ == target.checkKickers2Pair(twoPair, orSum(hole), orSum(table)));
    }
}