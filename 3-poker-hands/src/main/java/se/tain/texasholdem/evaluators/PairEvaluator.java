package se.tain.texasholdem.evaluators;

import static se.tain.texasholdem.evaluators.EvaluatorConstantMasks.PAIR;

public class PairEvaluator {

    public long checkPair(long handSum, long tableSum) {

        long sum = handSum + tableSum;
        long pair = sum & PAIR;

        if(pair == 0L){
            return 0L;
        }

        pair = handSum & (pair|pair>>1);

        if(0L == pair){
            return 0L;
        }

        return (pair | pair >>1 | pair <<1) & sum;
    }

    public long checkPairOneHand(long handSum) {
        return handSum & PAIR;
    }
}
