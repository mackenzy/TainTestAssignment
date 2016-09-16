package se.tain.texasholdem.evaluators;

import static se.tain.Rank.RA;
import static se.tain.texasholdem.evaluators.EvaluatorConstantMasks.STRAIGHT;

public class StraightFlushEvaluator {

    public long checkStraightFlush(long handOrSum, long handSuitOrSum, long tableOrSum, long tableSuitOrSum) {

        for (int i = STRAIGHT.length - 2; i >= 0; i--) {

            if (0 == (handOrSum & STRAIGHT[i])) {
                continue;
            }

            long value = handOrSum | tableOrSum;
            long maskedValue = value & STRAIGHT[i];

            if (5 != Long.bitCount(maskedValue)) {
                continue;
            }

            long handTableSuitSum = handSuitOrSum | tableSuitOrSum;

            for (int j = 0; j < 4; j++) {
                if (0 == (value & handSuitOrSum >> j)) {
                    continue;
                }

                if (5 == Long.bitCount(handTableSuitSum >> j & maskedValue)) {
                    return STRAIGHT[0] == (maskedValue & STRAIGHT[0]) ? maskedValue ^ RA : maskedValue;
                }
            }
        }

        return 0L;
    }

    public long checkStraightFlushOneHand(long handOrSum, long handSuitOrSum) {

        for (int i = STRAIGHT.length - 2; i >= 0; i--) {

            long value = handOrSum & STRAIGHT[i];

            if (5 != Long.bitCount(value)) {
                continue;
            }

            for (int j = 0; j < 4; j++) {
                long shiftedSuitOrSum = handSuitOrSum >> j;
                if (0 == (handOrSum & shiftedSuitOrSum)) {
                    continue;
                }

                if (5 == Long.bitCount(shiftedSuitOrSum & value)) {
                    return STRAIGHT[0] == (value & STRAIGHT[0]) ? value ^ RA : value;
                }
            }
        }

        return 0L;
    }
}
