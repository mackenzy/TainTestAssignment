package se.tain.texasholdem.evaluators;

public class KickerEvaluator {

    public long checkKickers4OfAKind(long bestHand, long handOrSum, long tableOrSum){
        return Long.highestOneBit((handOrSum | tableOrSum) ^ bestHand>>2);
    }

    public long checkKickers3OfAKind(long bestHand, long handOrSum, long tableOrSum){

        long two = (handOrSum | tableOrSum) ^ bestHand/3;
        long high = Long.highestOneBit(two);

        return Long.highestOneBit(two-high) | high;
    }

    public long checkKickers2Pair(long bestHand, long handOrSum, long tableOrSum){
        return Long.highestOneBit((handOrSum | tableOrSum) ^ bestHand>>1);
    }
}