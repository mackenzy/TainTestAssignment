package se.tain.texasholdem.evaluators;

import static se.tain.texasholdem.evaluators.EvaluatorConstantMasks.SUIT;

public class FlushEvaluator {

    public long checkFlush(long handSuitSum, long tableSuitSum) {

        for (int i = SUIT.length - 1; i >= 0; i--) {
            if ((handSuitSum & SUIT[i]) == 0) {
                continue;
            }

            long suits = (handSuitSum | tableSuitSum) & SUIT[i];
            int bitCount = Long.bitCount(suits);

            if(bitCount < 5){
                continue;
            }

            while (bitCount-- >5){
                suits ^= suits & -suits;
            }

            return suits>>i;
        }

        return 0L;
    }

    public long checkFlushOneHand(long handSuitSum) {

        for (int i = SUIT.length - 1; i >= 0; i--) {
            long suits = handSuitSum & SUIT[i];
            int bitCount = Long.bitCount(suits);

            if(bitCount < 5){
                continue;
            }

            while (bitCount-- >5){
                suits ^= suits & -suits;
            }

            return suits>>i;
        }

        return 0L;
    }

}
