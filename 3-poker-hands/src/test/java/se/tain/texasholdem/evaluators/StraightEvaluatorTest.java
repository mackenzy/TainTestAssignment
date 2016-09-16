package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.*;
import static se.tain.util.LongUtils.orSum;

public class StraightEvaluatorTest {

    @Test
    public void checkCards() {
        //given
        StraightEvaluator target = new StraightEvaluator();

        //then
        assertTrue(orSum(RA, RK, RQ, RJ, RT) == target.checkStraight(
                orSum(RA, R2),
                orSum(RK, RJ, RQ, RT, R3))
        );

        assertTrue(0 == target.checkStraight(
                orSum(R2),
                orSum(RA, RK, RJ, RQ, RT, R3))
        );

        assertTrue(orSum(R2, R3, R4, R5) == target.checkStraight(
                orSum(R2, R3),
                orSum(RA, R4, R5, RQ, RT, R3))
        );

        assertTrue(orSum(RA, RJ, RK, RQ, RT) == target.checkStraight(
                orSum(RT, R3),
                orSum(RA, R4, RK, RQ, RT, R3, RJ))
        );

        assertTrue(orSum(RA, RQ, RJ, RT, RK) == target.checkStraight(
                orSum(RT, R3),
                orSum(RA, R4, RK, RQ, RT, R3, RJ))
        );

        assertTrue(orSum(R6, R7, R8, R9, RT) == target.checkStraight(
                orSum(R7, R6, R8, RT),
                orSum(R4, RK, RT, R9))
        );

        assertTrue(orSum(R6, R7, R8, R9, RT) == target.checkStraight(
                orSum(R7, R6, R8, RT, R9),
                orSum(R4))
        );
    }

    @Test
    public void checkStraightOneHand_true1() {
        //given
        StraightEvaluator target = new StraightEvaluator();

        long[] table = {RK, RJ, RQ, RT, R3, RA, R2};

        //then
        assertTrue((RA | RK | RQ | RJ | RT) == target.checkStraightOneHand(orSum(table)));
    }

    @Test
    public void checkStraightOneHand_true2() {
        //given
        StraightEvaluator target = new StraightEvaluator();

        long[] table = {R2, RA, R3, R4, R5, R3, R5, RA};

        //then
        assertTrue((R2 | R3 | R4 | R5) == target.checkStraightOneHand(orSum(table)));
    }


    @Test
    public void checkStraightOneHand_false() {
        //given
        StraightEvaluator target = new StraightEvaluator();

        long[] table = {R2, RK, R3, R4, R5, R3, R5, RQ};

        //then
        assertTrue(0L == target.checkStraightOneHand(orSum(table)));
    }

    @Test
    public void checkStraightOneHand_false2() {
        //given
        StraightEvaluator target = new StraightEvaluator();

        long[] table = {RK, RJ, RQ, RT, R3, R4, R2};

        //then
        assertTrue(0L == target.checkStraightOneHand(orSum(table)));
    }

    @Test
    public void checkLowStraight_oneHand() {
        //given
        StraightEvaluator target = new StraightEvaluator();

        long[] table = {RJ, R5, RA, R3, R4, R2};

        //then
        assertTrue(orSum(R5, R3, R4, R2) == target.checkStraightOneHand(orSum(table)));
    }

    @Test
    public void checkLowStraight_oneHand2() {
        //given
        StraightEvaluator target = new StraightEvaluator();

        long[] table = {R6, R5, RA, R3, R4, R2};

        //then
        assertTrue(orSum(R6, R5, R4, R3, R2) == target.checkStraightOneHand(orSum(table)));
    }

    @Test
    public void checkLowStraight_oneHandAndTable() {
        //given
        StraightEvaluator target = new StraightEvaluator();

        //then
        assertTrue(orSum(R5, R3, R4, R2) == target.checkStraight(
                orSum(RA, R2),
                orSum(RK, R5, R4, R3, R2))
        );
    }

    @Test
    public void checkLowStraight_oneHandAndTable2() {
        //given
        StraightEvaluator target = new StraightEvaluator();

        //then
        assertTrue(orSum(R5, R3, R4, R2, R6) == target.checkStraight(
                orSum(RJ, R5),
                orSum(RA, R3, R4, R2, R6))
        );
    }
}