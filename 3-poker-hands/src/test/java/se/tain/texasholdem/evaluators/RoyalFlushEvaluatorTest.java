package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static se.tain.Rank.*;
import static se.tain.Suit.*;
import static se.tain.util.LongUtils.orSum;

public class RoyalFlushEvaluatorTest {

    @Test
    public void checkRoyalFlush() {
        //given
        RoyalFlushEvaluator target = new RoyalFlushEvaluator();

        //then
        assertTrue(target.isRoyalFlush(
                orSum(RA, R2),
                orSum(RA*HEARTS, R2*CLUBS),
                orSum(RK, RJ, RQ, RT, R3),
                orSum(RK*HEARTS, RJ*HEARTS, RQ*HEARTS, RT*HEARTS, R3*SPADES))
        );

        assertTrue(target.isRoyalFlush(
                orSum(RA, R2),
                orSum(RA*SPADES, R2*CLUBS),
                orSum(RK, RJ, RQ, RT, R3),
                orSum(RK*SPADES, RJ*SPADES, RQ*SPADES, RT*SPADES, R3*HEARTS))
        );

        assertFalse(target.isRoyalFlush(
                orSum(RA, R2),
                orSum(RA*DIAMONDS, R2*CLUBS),
                orSum(RK, RJ, RQ, RT, R3),
                orSum(RA*SPADES, RK*SPADES, RJ*SPADES, RQ*SPADES, RT*SPADES, R3*HEARTS))
        );

        assertFalse(target.isRoyalFlush(
                orSum(RA, R2),
                orSum(RK*SPADES, R2*CLUBS),
                orSum(RK, RJ, RQ, RT, R3),
                orSum(R9*SPADES, RJ*SPADES, RQ*SPADES, RT*SPADES, R3*HEARTS))
        );
    }

    @Test
    public void isRoyalFlushOneHand_true() {
        //given
        RoyalFlushEvaluator target = new RoyalFlushEvaluator();

        long[] table = {RK, RJ, RA, RQ, RT, R3, R2};
        long[] tableSuits = {RK*HEARTS, RJ*HEARTS, RA*HEARTS, RQ*HEARTS, RT*HEARTS, R3*SPADES, R2*CLUBS};

        //then
        assertTrue(target.isRoyalFlushOneHand(orSum(table), orSum(tableSuits)));
    }

    @Test
    public void isRoyalFlushOneHand_false1() {
        //given
        RoyalFlushEvaluator target = new RoyalFlushEvaluator();

        long[] table = {RK, RJ, RA, RQ, RT, R3, R2};
        long[] tableSuits = {RK*HEARTS, RJ*CLUBS, RA*HEARTS, RQ*HEARTS, RT*HEARTS, R3*SPADES, R2*CLUBS};

        //then
        assertFalse(target.isRoyalFlushOneHand(orSum(table), orSum(tableSuits)));
    }

    @Test
    public void isRoyalFlushOneHand_false2() {
        //given
        RoyalFlushEvaluator target = new RoyalFlushEvaluator();

        long[] table = {RK, RT, RA, RQ, RT, R3, R2};
        long[] tableSuits = {RK*HEARTS, RJ*HEARTS, RT*CLUBS, RA*HEARTS, RQ*HEARTS, RT*HEARTS, R3*SPADES, R2*CLUBS};

        //then
        assertFalse(target.isRoyalFlushOneHand(orSum(table), orSum(tableSuits)));
    }
}