package se.tain;

public class Player {
    private final String playerName;
    private final Hand holeCards;

    private long holeRankSum;
    private long holeRankOrSum;
    private long holeSuitSum;
    private long holeSuitOrSum;

    private String wonHandRankName;

    public Player(String playerName, String initialHandString) {
        this.playerName = playerName;
        this.holeCards = Hand.valueOf(initialHandString);

        initState();
    }

    @Override
    public String toString() {
        return playerName + "("+wonHandRankName+")";
    }

    private void initState() {
        for(Card card : holeCards.getCards()){
            long rank = card.getRank();
            long suit = card.getSuit();

            holeRankSum += rank;
            holeRankOrSum |= rank;
            holeSuitSum += suit;
            holeSuitOrSum |= suit;
        }
    }

    public long getHoleRankSum() {
        return holeRankSum;
    }

    public long getHoleRankOrSum() {
        return holeRankOrSum;
    }

    public long getHoleSuitSum() {
        return holeSuitSum;
    }

    public long getHoleSuitOrSum() {
        return holeSuitOrSum;
    }

    public void setWonHandRankName(String wonHandRankName) {
        this.wonHandRankName = wonHandRankName;
    }

    public String getWonHandRankName() {
        return wonHandRankName;
    }

    public Hand getHoleCards() {
        return holeCards;
    }

    public String getPlayerName() {
        return playerName;
    }
}
