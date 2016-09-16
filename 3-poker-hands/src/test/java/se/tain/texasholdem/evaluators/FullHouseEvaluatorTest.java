package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.*;
import static se.tain.util.LongUtils.sum;

public class FullHouseEvaluatorTest {

    @Test
    public void checkFullHouse() {
        //given
        FullHouseEvaluator target = new FullHouseEvaluator();

        //then
        assertTrue(3 * RK + 2 * RA == target.checkFullHouse(
                sum(RA, RK),
                sum(RK, RK, RA, R3))
        );

        assertTrue(0 != target.checkFullHouse(
                sum(R2, RA),
                sum(RA, RK, RK, RK, R3))
        );

        assertTrue(3 * RT + 2 * R8 == target.checkFullHouse(
                sum(R8, RT),
                sum(RA, RT, RT, R3, R8))
        );

        assertTrue(3 * RT + 2 * R8 == target.checkFullHouse(
                sum(R8, RT, R8, R7),
                sum(RA, RT, RT, R3, R8, R7, R7))
        );

        assertTrue(3 * RT + 2 * R8 == target.checkFullHouse(
                sum(R8, RT, R8, R7),
                sum(RA, RT, RT, R3, R8, R7, R7))
        );

        assertTrue(0 == target.checkFullHouse(
                sum(R8, RT, R8, R7),
                sum(RA, RT, R3, R7))
        );
    }

    @Test
    public void checkFullHouseOneHand_true() {
        //given
        FullHouseEvaluator target = new FullHouseEvaluator();

        long[] table = {RK, RK, RA, R3, RK, RA};

        //then
        assertTrue(3 * RK + 2 * RA == target.checkFullHouseOneHand(sum(table)));
    }

    @Test
    public void checkFullHouseOneHand_false() {
        //given
        FullHouseEvaluator target = new FullHouseEvaluator();

        long[] table = {RK, RA, R3, RK, RA};

        //then
        assertTrue(0L == target.checkFullHouseOneHand(sum(table)));
    }
}