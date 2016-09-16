package se.tain.texasholdem.evaluators;

import static se.tain.texasholdem.evaluators.EvaluatorConstantMasks.ROYAL_FLASH;

public class RoyalFlushEvaluator {

    public boolean isRoyalFlush(long handOrSum, long handSuitOrSum, long tableOrSum, long tableSuitOrSum) {

        long handTableSum = handOrSum | tableOrSum;
        long maskedHandTableSum = handTableSum & ROYAL_FLASH;

        if (5 != Long.bitCount(maskedHandTableSum)) {
            return false;
        }

        long handTableSuitSum = handSuitOrSum | tableSuitOrSum;

        for (int j = 0; j < 4; j++) {
            if (0 == (handTableSum & handSuitOrSum >> j)) {
                continue;
            }

            if (5 == Long.bitCount(handTableSuitSum >> j & maskedHandTableSum)) {
                return true;
            }
        }

        return false;
    }

    public boolean isRoyalFlushOneHand(long handOrSum, long handSuitOrSum) {

        long maskedHandSum = handOrSum & ROYAL_FLASH;

        if (5 != Long.bitCount(maskedHandSum)) {
            return false;
        }

        for (int j = 0; j < 4; j++) {
            if (0 == (handOrSum & handSuitOrSum >> j)) {
                continue;
            }

            if (5 == Long.bitCount(handSuitOrSum >> j & maskedHandSum)) {
                return true;
            }
        }

        return false;
    }

}
