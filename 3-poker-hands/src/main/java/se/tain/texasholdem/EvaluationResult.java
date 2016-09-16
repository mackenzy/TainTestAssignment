package se.tain.texasholdem;

import se.tain.Player;

public class EvaluationResult {

    private final Player player;
    private final long evaluatedValue;
    private final long kickerValue;

    public EvaluationResult(Player player, long evaluatedValue, long kickerValue) {
        this.player = player;
        this.evaluatedValue = evaluatedValue;
        this.kickerValue = kickerValue;
    }

    public Player getPlayer() {
        return player;
    }

    public long getEvaluatedValue() {
        return evaluatedValue;
    }

    public long getKickerValue() {
        return kickerValue;
    }
}
