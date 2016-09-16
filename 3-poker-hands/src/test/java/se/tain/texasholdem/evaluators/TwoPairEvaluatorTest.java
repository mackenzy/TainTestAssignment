package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.*;
import static se.tain.util.LongUtils.sum;

public class TwoPairEvaluatorTest {

    @Test
    public void checkTwoPairs() {
        //given
        TwoPairEvaluator target = new TwoPairEvaluator();

        long[] hole = {RA, RK};
        long[] table = {RK, RT, R3, RA};

        //then
        assertTrue(2 * RA + 2 * RK == target.checkTwoPairs(sum(hole), sum(table)));


        hole = new long[]{R2};
        table = new long[]{RA, RK, RK, R2};

        //then
        assertTrue(2 * RK + 2 * R2 == target.checkTwoPairs(sum(hole), sum(table)));


        hole = new long[]{R2, RK};
        table = new long[]{RA, RK, R3, R2};

        //then
        assertTrue(2 * RK + 2 * R2 == target.checkTwoPairs(sum(hole), sum(table)));


        hole = new long[]{R2, R2, R3};
        table = new long[]{RA, RJ, RJ};

        //then
        assertTrue(2 * R2 + 2 * RJ == target.checkTwoPairs(sum(hole), sum(table)));

        hole = new long[]{R2, R3, R4};
        table = new long[]{RA, R2, R3, R4, RT};

        //then
        assertTrue(2 * R3 + 2 * R4 == target.checkTwoPairs(sum(hole), sum(table)));
    }

    @Test
    public void checkTwoPairs_false() {
        //given
        TwoPairEvaluator target = new TwoPairEvaluator();

        long[] hole = {R2, R7};
        long[] table = {RA, RJ, RJ};

        //then
        assertTrue(0L == target.checkTwoPairs(sum(hole), sum(table)));

    }

    @Test
    public void checkTwoPairsOneHand_true() {
        //given
        TwoPairEvaluator target = new TwoPairEvaluator();

        long[] table = {RK, RT, R3, RA, RA, RK};

        //then
        assertTrue(2 * RA + 2 * RK == target.checkTwoPairsOneHand(sum(table)));
    }

    @Test
    public void checkTwoPairsOneHand_true2() {
        //given
        TwoPairEvaluator target = new TwoPairEvaluator();

        long[] table = {RK, RT, R3, RA, RA, RK, R3, R3};

        //then
        assertTrue(2 * RA + 2 * RK == target.checkTwoPairsOneHand(sum(table)));
    }

    @Test
    public void checkTwoPairsOneHand_false() {
        //given
        TwoPairEvaluator target = new TwoPairEvaluator();

        long[] table = {RK, RT, R3, RA, R3};

        //then
        assertTrue(0L == target.checkTwoPairsOneHand(sum(table)));
    }


}