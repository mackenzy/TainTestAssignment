package se.tain.texasholdem.evaluators;

import static se.tain.texasholdem.evaluators.EvaluatorConstantMasks.PAIR;

public class TwoPairEvaluator {

    public long checkTwoPairs(long handSum, long tableSum) {

        long twoPair = (handSum + tableSum) & PAIR;
        long high = Long.highestOneBit(twoPair);

        if(high == 0L){
            return 0L;
        }

        long low = Long.highestOneBit(twoPair - high);

        if(low == 0L){
            return 0L;
        }

        twoPair = high|low;
        if(0L == (handSum & (twoPair|twoPair>>1))){
            return 0L;
        }

        return twoPair;
    }

    public long checkTwoPairsOneHand(long handSum) {

        long twoPair = handSum & PAIR;
        long high = Long.highestOneBit(twoPair);

        if(high == 0L){
            return 0L;
        }

        long low = Long.highestOneBit(twoPair - high);

        if(low == 0L){
            return 0L;
        }

        return high|low;
    }
}
