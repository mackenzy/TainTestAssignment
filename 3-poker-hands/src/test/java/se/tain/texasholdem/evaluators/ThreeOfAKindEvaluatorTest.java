package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.*;
import static se.tain.util.LongUtils.sum;

public class ThreeOfAKindEvaluatorTest {

    @Test
    public void checkThreeOfAKind() {
        //given
        ThreeOfAKindEvaluator target = new ThreeOfAKindEvaluator();

        long[] hole = {RA, RK};
        long[] table = {RK, RK, RT, R3};

        //then
        assertTrue(3 * RK == target.checkThreeOfAKind(sum(hole), sum(table)));


        hole = new long[]{R2};
        table = new long[]{RA, RK, RK, RK, RK, R3};

        //then
        assertTrue(0L == target.checkThreeOfAKind(sum(hole), sum(table)));


        hole = new long[]{R2};
        table = new long[]{RA, RK, RK, RK, RK, R3, R2, R2};

        //then
        assertTrue(3 * R2 == target.checkThreeOfAKind(sum(hole), sum(table)));


        hole = new long[]{R2, R2};
        table = new long[]{RA, RK, RK, RK, R3, R2};

        //then
        assertTrue(3 * R2 == target.checkThreeOfAKind(sum(hole), sum(table)));


        hole = new long[]{R2, R2, R2};
        table = new long[]{RA, RK, RK, RK, R3};

        //then
        assertTrue(3 * R2 == target.checkThreeOfAKind(sum(hole), sum(table)));
    }

    @Test
    public void checkThreeOfAKindOneHand() {
        //given
        ThreeOfAKindEvaluator target = new ThreeOfAKindEvaluator();

        long[] table = {RK, RK, RT, R3, RA, RK};

        //then
        assertTrue(3 * RK == target.checkThreeOfAKindOneHand(sum(table)));
    }

}