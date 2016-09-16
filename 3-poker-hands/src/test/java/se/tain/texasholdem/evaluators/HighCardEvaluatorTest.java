package se.tain.texasholdem.evaluators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static se.tain.Rank.R2;
import static se.tain.Rank.RA;
import static se.tain.Rank.RK;
import static se.tain.util.LongUtils.orSum;

public class HighCardEvaluatorTest {
    @Test
    public void highCard() {
        //given
        HighCardEvaluator target = new HighCardEvaluator();

        //then
        assertTrue(RA == target.highCard(orSum(R2, RK, RA)));
    }

}