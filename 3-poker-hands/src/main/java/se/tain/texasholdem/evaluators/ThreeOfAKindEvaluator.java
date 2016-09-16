package se.tain.texasholdem.evaluators;

import static se.tain.texasholdem.evaluators.EvaluatorConstantMasks.PAIR;

public class ThreeOfAKindEvaluator {

    public long checkThreeOfAKind(long handSum, long tableSum) {

        long sum = handSum + tableSum;
        long three = sum & PAIR & sum << 1;

        three |= three>>1;

        three = handSum & three;

        if (0L == three){
            return 0L;
        }

        return (three | three>>1 | three<<1) & sum;
    }

    public long checkThreeOfAKindOneHand(long handSum) {

        long three = handSum & PAIR & handSum << 1;

        return three | three >>1;
    }
}

