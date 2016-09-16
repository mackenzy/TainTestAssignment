package se.tain.texasholdem.evaluators;

public class HighCardEvaluator {

    public long highCard(long orSum){
        return Long.highestOneBit(orSum);
    }
}
