package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.*;
import static se.tain.util.LongUtils.sum;

public class PairEvaluatorTest {

    @Test
    public void checkTwoOfAKind() {
        //given
        PairEvaluator target = new PairEvaluator();

        long[] hole = {RA, RK};
        long[] table = {RK, RT, R3};

        //then
        assertTrue(2 * RK == target.checkPair(sum(hole), sum(table)));

        hole = new long[]{R2};
        table = new long[]{RA, RK, R3, R2};

        //then
        assertTrue(2 * R2 == target.checkPair(sum(hole), sum(table)));
    }

    @Test
    public void checkTwoOfAKind_low() {
        //given
        PairEvaluator target = new PairEvaluator();

        long[] hole = {R3, R2};
        long[] table = {RK, RK, RT, R2};

        //then
        assertTrue(2 * R2 == target.checkPair(sum(hole), sum(table)));
    }

    @Test
    public void checkTwoOfAKind_low2() {
        //given
        PairEvaluator target = new PairEvaluator();

        long[] hole = {R3, R3};
        long[] table = {RA, RK, RK, RT};

        //then
        assertTrue(2 * R3 == target.checkPair(sum(hole), sum(table)));
    }

    @Test
    public void checkTwoOfAKind_false() {
        //given
        PairEvaluator target = new PairEvaluator();

        long[] hole = {R2};
        long[] table = {RA, RK, R3};

        //then
        assertTrue(0L == target.checkPair(sum(hole), sum(table)));
    }

    @Test
    public void checkOneHand() {
        //given
        PairEvaluator target = new PairEvaluator();

        long[] table = {RA, RK, R3, R4, R4};

        //then
        assertTrue(2 * R4 == target.checkPairOneHand(sum(table)));
    }

    @Test
    public void checkOneHand_false() {
        //given
        PairEvaluator target = new PairEvaluator();

        long[] table = {RA, RK, R3, R4};

        //then
        assertTrue(0L == target.checkPairOneHand(sum(table)));
    }

}