package se.tain.texasholdem.evaluators;

import static se.tain.Rank.R5;
import static se.tain.Rank.RA;
import static se.tain.texasholdem.evaluators.EvaluatorConstantMasks.STRAIGHT;

public class StraightEvaluator {

    private final long lowStraight = R5 | RA;

    public long checkStraight(long handOrSum, long tableOrSum) {

        for (int i = STRAIGHT.length - 1; i >= 0; i--) {
            if ((handOrSum & STRAIGHT[i]) == 0) {
                continue;
            }

            long straight = (handOrSum | tableOrSum) & STRAIGHT[i];
            if (Long.bitCount(straight) == 5) {

                return (straight & lowStraight) == lowStraight ? straight ^ RA : straight;
            }
        }

        return 0L;
    }

    public long checkStraightOneHand(long handOrSum) {

        for (int i = STRAIGHT.length - 1; i >= 0; i--) {
            long straight = handOrSum & STRAIGHT[i];
            if (Long.bitCount(straight) == 5) {
                return (straight & lowStraight) == lowStraight ? straight ^ RA : straight;
            }
        }

        return 0L;
    }

}
