package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.*;
import static se.tain.Suit.*;
import static se.tain.util.LongUtils.orSum;
import static se.tain.util.LongUtils.sum;

public class FlushEvaluatorTest {

    @Test
    public void checkFlush() {
        //given
        FlushEvaluator target = new FlushEvaluator();

        //then
        assertTrue(orSum(RA, RK, RJ, RQ, RT) == target.checkFlush(
                orSum(RA * HEARTS, R2 * CLUBS),
                orSum(RK * HEARTS, RJ * HEARTS, RQ * HEARTS, RT * HEARTS, R3 * SPADES))
        );

        assertTrue(orSum(R2, R3, RK, RJ, RQ) == target.checkFlush(
                orSum(RA * HEARTS, R2 * CLUBS),
                orSum(RK * CLUBS, RJ * CLUBS, RQ * CLUBS, RT * HEARTS, R3 * CLUBS))
        );

        assertTrue(orSum(R2, RK, RJ, R3, RQ) == target.checkFlush(
                orSum(RA * HEARTS, R2 * SPADES),
                orSum(RK * SPADES, RJ * SPADES, RQ * SPADES, RT * HEARTS, R3 * SPADES))
        );

        assertTrue(orSum(R2, R3, RJ, RQ, RK) == target.checkFlush(
                orSum(RA * HEARTS, R2 * SPADES, R3 * SPADES),
                orSum(RK * SPADES, RJ * SPADES, RQ * SPADES, RT * HEARTS))
        );

        assertTrue(0 == target.checkFlush(
                orSum(RA * HEARTS, R2 * DIAMONDS, R3 * SPADES),
                orSum(RK * SPADES, RJ * SPADES, RQ * SPADES, RT * HEARTS))
        );

        assertTrue(orSum(R3, RT, RJ, RQ, RK) == target.checkFlush(
                orSum(RA * HEARTS, R2 * SPADES, R3 * SPADES),
                orSum(RK * SPADES, RJ * SPADES, RQ * SPADES, RT * HEARTS, RT * SPADES))
        );
    }

    @Test
    public void checkFlushOneHand_true() {
        //given
        FlushEvaluator target = new FlushEvaluator();

        long[] table = {RK * HEARTS, RJ * HEARTS, RQ * HEARTS, RT * HEARTS, R3 * SPADES, RA * HEARTS, R2 * CLUBS};

        //then
        assertTrue(sum(RK, RJ, RQ, RT, RA) == target.checkFlushOneHand(orSum(table)));
    }

    @Test
    public void checkFlushOneHand_true2() {
        //given
        FlushEvaluator target = new FlushEvaluator();

        long[] table = {RK * DIAMONDS, RJ * DIAMONDS, RQ * DIAMONDS, RT * DIAMONDS, R3 * SPADES, R9 * DIAMONDS, R2 * CLUBS};

        //then
        assertTrue(sum(RK | RJ | RQ | RT | R9) == target.checkFlushOneHand(orSum(table)));
    }

    @Test
    public void checkFlushOneHand_false() {
        //given
        FlushEvaluator target = new FlushEvaluator();

        long[] table = {RK * DIAMONDS, RJ * SPADES, RQ * DIAMONDS, RT * DIAMONDS, R3 * SPADES, R9 * DIAMONDS, R2 * CLUBS};

        //then
        assertTrue(0L == target.checkFlushOneHand(orSum(table)));
    }
}