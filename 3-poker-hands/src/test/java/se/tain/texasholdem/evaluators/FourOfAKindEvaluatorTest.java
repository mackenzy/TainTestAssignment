package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.*;
import static se.tain.util.LongUtils.sum;

public class FourOfAKindEvaluatorTest {

    @Test
    public void checkFourOfAKind() {
        //given
        FourOfAKindEvaluator target = new FourOfAKindEvaluator();

        //then
        assertTrue(4 * RK == target.checkFourOfAKind(
                sum(RA, RK),
                sum(RK, RK, RK, RT, R3))
        );

        assertTrue(4 * R2 == target.checkFourOfAKind(
                sum(R2),
                sum(RA, RK, RK, RK, RK, R3, R2, R2, R2))
        );

        assertTrue(4 * R2 == target.checkFourOfAKind(
                sum(R2, R2),
                sum(RA, RK, RK, RK, RK, R3, R2, R2))
        );

        assertTrue(4 * R2 == target.checkFourOfAKind(
                sum(R2, R2, R2),
                sum(RA, RK, RK, RK, RK, R3, R2))
        );

        assertTrue(4 * R2 == target.checkFourOfAKind(
                sum(R2, R2, R2, R2),
                sum(RA, RK, RK, RK, RK, R3))
        );
    }

    @Test
    public void checkFourOfAKind_false() {
        //given
        FourOfAKindEvaluator target = new FourOfAKindEvaluator();

        long[] hole = {R2};
        long[] table = {RA, RK, RK, RK, RK, R3};

        //then
        assertTrue(0L == target.checkFourOfAKind(sum(hole), sum(table)));
    }

    @Test
    public void checkFourOfAKindOneHand_true() {
        //given
        FourOfAKindEvaluator target = new FourOfAKindEvaluator();

        long[] table = {RJ, RJ, RJ, RJ, R3};

        //then
        assertTrue(4 * RJ == target.checkFourOfAKindOneHand(sum(table)));
    }

    @Test
    public void checkFourOfAKindOneHand_false() {
        //given
        FourOfAKindEvaluator target = new FourOfAKindEvaluator();

        long[] table = {RJ, RJ, RJ, R3};

        //then
        assertTrue(0L == target.checkFourOfAKindOneHand(sum(table)));
    }
}