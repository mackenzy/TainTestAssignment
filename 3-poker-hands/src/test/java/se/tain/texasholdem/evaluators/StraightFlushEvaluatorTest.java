package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.*;
import static se.tain.Suit.*;
import static se.tain.util.LongUtils.orSum;

public class StraightFlushEvaluatorTest {

    @Test
    public void checkStraightFlush() {
        //given
        StraightFlushEvaluator target = new StraightFlushEvaluator();

        long[] hole = {R9, R2};
        long[] table = {RK, RJ, RQ, RT, R3};

        long[] holeSuits = {R9 * HEARTS, R2 * CLUBS};
        long[] tableSuits = {RK * HEARTS, RJ * HEARTS, RQ * HEARTS, RT * HEARTS, R3 * SPADES};

        //then
        assertTrue(orSum(R9, RT, RJ, RQ, RK) == target.checkStraightFlush(orSum(hole), orSum(holeSuits), orSum(table), orSum(tableSuits)));


        hole = new long[]{R9, R2};
        table = new long[]{RK, RJ, RQ, RT, R3};

        holeSuits = new long[]{R9 * SPADES, R2 * CLUBS};
        tableSuits = new long[]{RK * SPADES, RJ * SPADES, RQ * SPADES, RT * SPADES, R3 * HEARTS};

        //then
        assertTrue(orSum(R9, RT, RJ, RQ, RK) == target.checkStraightFlush(orSum(hole), orSum(holeSuits), orSum(table), orSum(tableSuits)));


        hole = new long[]{RA, R2};
        table = new long[]{RK, RJ, RQ, RT, R3};

        holeSuits = new long[]{RA * DIAMONDS, R2 * CLUBS};
        tableSuits = new long[]{RA * SPADES, RK * SPADES, RJ * SPADES, RQ * SPADES, RT * SPADES, R3 * HEARTS};

        //then
        assertTrue(0 == target.checkStraightFlush(orSum(hole), orSum(holeSuits), orSum(table), orSum(tableSuits)));
    }

    @Test
    public void checkStraightFlushOneHand_High() {
        //given
        StraightFlushEvaluator target = new StraightFlushEvaluator();

        long[] table = {RK, RJ, RQ, RT, R3, R9};
        long[] tableSuits = {RK * CLUBS, RJ * CLUBS, RQ * CLUBS, RT * CLUBS, R3 * SPADES, R9 * CLUBS};

        //then
        assertTrue(RK + RJ + RQ + RT + R9 == target.checkStraightFlushOneHand(orSum(table), orSum(tableSuits)));
    }

    @Test
    public void checkStraightFlushOneHand_Low() {
        //given
        StraightFlushEvaluator target = new StraightFlushEvaluator();

        long[] table = {RA, R2, R3, R4, R5, R5};
        long[] tableSuits = {RA * DIAMONDS, R2 * DIAMONDS, R3 * DIAMONDS, R4 * DIAMONDS, R5 * DIAMONDS, R5 * CLUBS};

        //then
        assertTrue(R2 + R3 + R4 + R5 == target.checkStraightFlushOneHand(orSum(table), orSum(tableSuits)));
    }

    @Test
    public void checkStraightFlushOneHand_false1() {
        //given
        StraightFlushEvaluator target = new StraightFlushEvaluator();

        long[] table = {RK, RJ, RQ, RT, R3, R8};
        long[] tableSuits = {RK * CLUBS, RJ * CLUBS, RQ * CLUBS, RT * CLUBS, R3 * SPADES, R8 * CLUBS};

        //then
        assertTrue(0L == target.checkStraightFlushOneHand(orSum(table), orSum(tableSuits)));
    }

    @Test
    public void checkStraightFlushOneHand_false2() {
        //given
        StraightFlushEvaluator target = new StraightFlushEvaluator();

        long[] table = {RA, R2, R3, R4, R5, R5};
        long[] tableSuits = {RA * DIAMONDS, R2 * DIAMONDS, R3 * DIAMONDS, R4 * DIAMONDS, R5 * SPADES, R5 * CLUBS};

        //then
        assertTrue(0L == target.checkStraightFlushOneHand(orSum(table), orSum(tableSuits)));
    }
}