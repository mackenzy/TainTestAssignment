package se.tain.texasholdem.evaluators;

import static se.tain.texasholdem.evaluators.EvaluatorConstantMasks.FOUR_OF_A_KIND;

public class FourOfAKindEvaluator {

    public long checkFourOfAKind(long handSum, long tableSum) {

        long result = (handSum + tableSum) & FOUR_OF_A_KIND;

        if(result == 0L){
            return 0L;
        }

        for (int i = 0; i <= 3; i++) {

            long four = handSum & result>>i;
            if(0L != (four)){
                return Long.highestOneBit(four)<<i;
            }
        }

        return 0L;
    }

    public long checkFourOfAKindOneHand(long handSum) {
        return Long.highestOneBit(handSum & FOUR_OF_A_KIND);
    }
}