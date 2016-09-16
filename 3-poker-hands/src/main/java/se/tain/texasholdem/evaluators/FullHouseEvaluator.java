package se.tain.texasholdem.evaluators;

import static se.tain.texasholdem.evaluators.EvaluatorConstantMasks.PAIR;

public class FullHouseEvaluator {

    public long checkFullHouse(long handSum, long tableSum) {

        long sum = handSum + tableSum;
        long three = Long.highestOneBit(sum & PAIR & sum << 1);

        if( 0 == three ){
            return 0L;
        }

        three |= three>>1;

        long two = Long.highestOneBit((sum ^ three) & PAIR);

        if(0 == two){
            return 0L;
        }

        if(0 == (handSum & three | handSum & (two | two >> 1))){
            return 0L;
        }

        return  two | three;
    }

    public long checkFullHouseOneHand(long handSum) {
        long three = Long.highestOneBit(handSum & PAIR & handSum << 1);

        if( 0 == three ){
            return 0L;
        }

        three |= three>>1;

        long two = Long.highestOneBit((handSum ^ three) & PAIR);

        if(0 == two){
            return 0L;
        }

        return  two | three;
    }
}
