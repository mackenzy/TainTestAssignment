package se.tain.texasholdem;

import org.jetbrains.annotations.NotNull;
import se.tain.Card;
import se.tain.HandRankName;
import se.tain.Player;
import se.tain.WinnerDeterminator;
import se.tain.texasholdem.evaluators.*;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static se.tain.HandRankName.*;

public class TexasHoldemDeterminator implements WinnerDeterminator {

    private final Comparator<EvaluationResult> EV_RES_KICKER_COMPARATOR =
            (l, r) -> {
                int val = Long.compare(r.getEvaluatedValue(), l.getEvaluatedValue());
                return val != 0 ? val : Long.compare(r.getKickerValue(), l.getKickerValue());
            };

    @FunctionalInterface
    private interface GetWinners {
        List<Player> apply(List<Player> p);
    }

    private final RoyalFlushEvaluator royalFlushEv;
    private final FourOfAKindEvaluator fourOfAKindEv;
    private final StraightFlushEvaluator straightFlushEv;
    private final FullHouseEvaluator fullHouseEv;
    private final FlushEvaluator flushEv;
    private final StraightEvaluator straightEv;
    private final ThreeOfAKindEvaluator threeOfAKindEv;
    private final TwoPairEvaluator twoPairEv;
    private final PairEvaluator pairEv;
    private final HighCardEvaluator highCardEv;
    private final KickerEvaluator kickerEv;

    private final GetWinners[] getWinners = new GetWinners[]{
            this::defineRoyalFlushWinners,
            this::defineStraightFlushWinners,
            this::defineFourOfAKindWinners,
            this::defineFullHouseWinners,
            this::defineFlushWinners,
            this::defineStraightWinners,
            this::defineThreeOfAKindWinners,
            this::defineTwoPairWinners,
            this::definePairWinners,
            this::defineHighCardWinners
    };

    public TexasHoldemDeterminator(RoyalFlushEvaluator royalFlushEv,
                                   FourOfAKindEvaluator fourOfAKindEv,
                                   StraightFlushEvaluator straightFlushEv,
                                   FullHouseEvaluator fullHouseEv,
                                   FlushEvaluator flushEv,
                                   StraightEvaluator straightEv,
                                   ThreeOfAKindEvaluator threeOfAKindEv,
                                   TwoPairEvaluator twoPairEv,
                                   PairEvaluator pairEv,
                                   HighCardEvaluator highCardEv,
                                   KickerEvaluator kickerEv) {
        this.royalFlushEv = royalFlushEv;
        this.fourOfAKindEv = fourOfAKindEv;
        this.straightFlushEv = straightFlushEv;
        this.fullHouseEv = fullHouseEv;
        this.flushEv = flushEv;
        this.straightEv = straightEv;
        this.threeOfAKindEv = threeOfAKindEv;
        this.twoPairEv = twoPairEv;
        this.pairEv = pairEv;
        this.highCardEv = highCardEv;
        this.kickerEv = kickerEv;
    }

    private long tableRankSum;
    private long tableRankOrSum;
    private long tableSuitSum;
    private long tableSuitOrSum;

    @Override
    public List<Player> define(Card inputCard, List<Player> players) {
        addCardToTableState(inputCard);

        for (GetWinners getWinner : getWinners) {
            List<Player> winners = getWinner.apply(players);
            if (winners.size() > 0) return winners;
        }

        return emptyList();
    }

    @NotNull
    protected List<Player> defineRoyalFlushWinners(@NotNull List<Player> players) {
        if (royalFlushEv.isRoyalFlushOneHand(tableRankOrSum, tableSuitOrSum)) {
            return mapHandRankName(players, HandRankName.ROYAL_FLUSH);
        }

        List<Player> rfs = players.stream()
                .filter(p -> royalFlushEv.isRoyalFlush(p.getHoleRankOrSum(), p.getHoleSuitOrSum(), tableRankOrSum, tableSuitOrSum))
                .collect(toList());

        if (rfs.size() >= 1) return mapHandRankName(rfs, HandRankName.ROYAL_FLUSH);
        return emptyList();
    }

    @NotNull
    protected List<Player> defineStraightFlushWinners(@NotNull List<Player> players) {
        long tableSF = straightFlushEv.checkStraightFlushOneHand(tableRankOrSum, tableSuitOrSum);

        EvaluationResult[] results = players.stream()
                .map(p -> new EvaluationResult(p,
                        straightFlushEv.checkStraightFlush(p.getHoleRankOrSum(), p.getHoleSuitOrSum(), tableRankOrSum, tableSuitOrSum),
                        0L)
                ).filter(r -> 0L != r.getEvaluatedValue())
                .sorted((l, r) -> Long.compare(r.getEvaluatedValue(), l.getEvaluatedValue()))
                .toArray(EvaluationResult[]::new);

        long maxVal = results.length > 0 ? results[0].getEvaluatedValue() : 0L;
        if (tableSF > maxVal) return mapHandRankName(players, STRAIGHT_FLUSH);

        return mapHandRankName(
                returnResults(results, maxVal),
                STRAIGHT_FLUSH
        );
    }

    @NotNull
    protected List<Player> defineFourOfAKindWinners(@NotNull List<Player> players) {
        Long fourOnTable = fourOfAKindEv.checkFourOfAKindOneHand(tableRankSum);

        EvaluationResult[] results = players.stream().map(p -> {
            long evRes = fourOfAKindEv.checkFourOfAKind(p.getHoleRankSum(), tableRankSum);
            return new EvaluationResult(p,
                    evRes,
                    kickerEv.checkKickers4OfAKind(evRes, p.getHoleRankOrSum(), tableRankOrSum)
            );
        }).filter(r -> 0L != r.getEvaluatedValue())
                .sorted(EV_RES_KICKER_COMPARATOR)
                .toArray(EvaluationResult[]::new);

        long maxVal = results.length > 0 ? results[0].getEvaluatedValue() : 0L;

        if (fourOnTable > maxVal) {
            return mapHandRankName(
                    defineWinnersByKickers(
                            players,
                            p -> new EvaluationResult(p,
                                    0L,
                                    kickerEv.checkKickers4OfAKind(fourOnTable, p.getHoleRankOrSum(), tableRankOrSum)
                            )
                    ),
                    FOUR_OF_A_KIND
            );
        }

        return mapHandRankName(
                returnResultsWithKicker(results, maxVal),
                FOUR_OF_A_KIND
        );
    }

    @NotNull
    protected List<Player> defineFullHouseWinners(@NotNull List<Player> players) {
        long fullHouseOnTable = fullHouseEv.checkFullHouseOneHand(tableRankSum);

        EvaluationResult[] results = players.stream()
                .map(p -> new EvaluationResult(p,
                        fullHouseEv.checkFullHouse(p.getHoleRankSum(), tableRankSum),
                        0L)
                ).filter(r -> 0L != r.getEvaluatedValue())
                .sorted((l, r) -> Long.compare(r.getEvaluatedValue(), l.getEvaluatedValue()))
                .toArray(EvaluationResult[]::new);

        long maxVal = results.length > 0 ? results[0].getEvaluatedValue() : 0L;

        if (fullHouseOnTable > maxVal) {
            return mapHandRankName(players, FULL_HOUSE);
        }

        return mapHandRankName(
                returnResults(results, maxVal),
                FULL_HOUSE
        );
    }

    @NotNull
    protected List<Player> defineFlushWinners(@NotNull List<Player> players) {
        long flushOnTable = flushEv.checkFlushOneHand(tableSuitSum);

        EvaluationResult[] results = players.stream()
                .map(p -> new EvaluationResult(p,
                        flushEv.checkFlush(p.getHoleSuitSum(), tableSuitSum),
                        0L)
                ).filter(r -> 0L != r.getEvaluatedValue())
                .sorted((l, r) -> Long.compare(r.getEvaluatedValue(), l.getEvaluatedValue()))
                .toArray(EvaluationResult[]::new);

        long maxVal = results.length > 0 ? results[0].getEvaluatedValue() : 0L;
        if (flushOnTable > maxVal) return mapHandRankName(players, FLUSH);

        return mapHandRankName(
                returnResults(results, maxVal),
                FLUSH
        );
    }

    @NotNull
    protected List<Player> defineStraightWinners(@NotNull List<Player> players) {
        long straightOnTable = straightEv.checkStraightOneHand(tableRankOrSum);

        EvaluationResult[] results = players.stream()
                .map(p -> new EvaluationResult(p,
                        straightEv.checkStraight(p.getHoleRankOrSum(), tableRankOrSum),
                        0L)
                ).filter(r -> 0L != r.getEvaluatedValue())
                .sorted((l, r) -> Long.compare(r.getEvaluatedValue(), l.getEvaluatedValue()))
                .toArray(EvaluationResult[]::new);

        long maxVal = results.length > 0 ? results[0].getEvaluatedValue() : 0L;
        if (straightOnTable > maxVal) return mapHandRankName(players, STRAIGHT);

        return mapHandRankName(
                returnResults(results, maxVal),
                STRAIGHT
        );
    }

    @NotNull
    protected List<Player> defineThreeOfAKindWinners(@NotNull List<Player> players) {
        long threeOfAKindOnTable = threeOfAKindEv.checkThreeOfAKindOneHand(tableRankSum);

        EvaluationResult[] results = players.stream().map(p -> {
            long evRes = threeOfAKindEv.checkThreeOfAKind(p.getHoleRankSum(), tableRankSum);
            return new EvaluationResult(p,
                    evRes,
                    kickerEv.checkKickers3OfAKind(evRes, p.getHoleRankOrSum(), tableRankOrSum)
            );
        }).filter(r -> 0L != r.getEvaluatedValue())
                .sorted(EV_RES_KICKER_COMPARATOR)
                .toArray(EvaluationResult[]::new);

        long maxVal = results.length > 0 ? results[0].getEvaluatedValue() : 0L;

        if (threeOfAKindOnTable > maxVal) {
            return mapHandRankName(
                    defineWinnersByKickers(
                            players,
                            p -> new EvaluationResult(p,
                                    0L,
                                    kickerEv.checkKickers3OfAKind(threeOfAKindOnTable, p.getHoleRankOrSum(), tableRankOrSum)
                            )
                    ),
                    THREE_OF_A_KIND
            );
        }

        return mapHandRankName(
                returnResultsWithKicker(results, maxVal),
                THREE_OF_A_KIND
        );
    }

    @NotNull
    protected List<Player> defineTwoPairWinners(@NotNull List<Player> players) {
        long twoPairOnTable = twoPairEv.checkTwoPairsOneHand(tableRankSum);

        EvaluationResult[] results = players.stream().map(p -> {
            long evRes = twoPairEv.checkTwoPairs(p.getHoleRankSum(), tableRankSum);
            return new EvaluationResult(p,
                    evRes,
                    kickerEv.checkKickers2Pair(evRes, p.getHoleRankOrSum(), tableRankOrSum)
            );
        }).filter(r -> 0L != r.getEvaluatedValue())
                .sorted(EV_RES_KICKER_COMPARATOR)
                .toArray(EvaluationResult[]::new);

        long maxVal = results.length > 0 ? results[0].getEvaluatedValue() : 0L;

        if (twoPairOnTable > maxVal) {
            return mapHandRankName(
                    defineWinnersByKickers(
                            players,
                            p -> new EvaluationResult(p,
                                    0L,
                                    kickerEv.checkKickers2Pair(twoPairOnTable, p.getHoleRankOrSum(), tableRankOrSum)
                            )
                    ),
                    TWO_PAIR
            );
        }

        return mapHandRankName(
                returnResultsWithKicker(results, maxVal),
                TWO_PAIR
        );
    }

    @NotNull
    protected List<Player> definePairWinners(@NotNull List<Player> players) {
        long pairOnTable = pairEv.checkPairOneHand(tableRankSum);

        EvaluationResult[] results = players.stream()
                .map(p -> {
                    long evRes = pairEv.checkPair(p.getHoleRankSum(), tableRankSum);
                    return new EvaluationResult(p,
                            evRes,
                            kickerEv.checkKickers2Pair(evRes, p.getHoleRankOrSum(), tableRankOrSum)
                    );
                })
                .filter(r -> 0L != r.getEvaluatedValue())
                .sorted(EV_RES_KICKER_COMPARATOR)
                .toArray(EvaluationResult[]::new);


        long maxVal = results.length > 0 ? results[0].getEvaluatedValue() : 0L;

        if (pairOnTable > maxVal) {
            return mapHandRankName(
                    defineWinnersByKickers(
                            players,
                            p -> new EvaluationResult(p,
                                    0L,
                                    kickerEv.checkKickers2Pair(pairOnTable, p.getHoleRankOrSum(), tableRankOrSum)
                            )
                    ),
                    ONE_PAIR
            );
        }

        return mapHandRankName(
                returnResultsWithKicker(results, maxVal),
                ONE_PAIR
        );
    }

    @NotNull
    protected List<Player> defineHighCardWinners(@NotNull List<Player> players) {
        EvaluationResult[] results = players.stream()
                .map(p -> new EvaluationResult(p,
                        highCardEv.highCard(p.getHoleRankOrSum()),
                        0L)
                ).filter(r -> 0L != r.getEvaluatedValue())
                .sorted((l, r) -> Long.compare(r.getEvaluatedValue(), l.getEvaluatedValue()))
                .toArray(EvaluationResult[]::new);

        if (results.length > 0) {
            long maxVal = results[0].getEvaluatedValue();
            return mapHandRankName(
                    returnResults(results, maxVal),
                    HIGH_CARD
            );
        }

        return emptyList();
    }

    @NotNull
    protected List<Player> defineWinnersByKickers(List<Player> players, Function<Player, EvaluationResult> mapper) {
        return returnResultsWithKicker(players.stream()
                        .map(mapper)
                        .sorted(EV_RES_KICKER_COMPARATOR)
                        .toArray(EvaluationResult[]::new),
                0L
        );
    }

    protected void addCardToTableState(Card inputCard) {

        long rank = inputCard.getRank();
        long suit = inputCard.getSuit();

        tableRankSum += rank;
        tableRankOrSum |= rank;
        tableSuitSum += suit;
        tableSuitOrSum |= suit;
    }

    private List<Player> mapHandRankName(List<Player> players, String handRankName) {
        players.forEach(p -> p.setWonHandRankName(handRankName));
        return players;
    }

    private List<Player> returnResultsWithKicker(EvaluationResult[] results, long maxVal) {
        if (results.length == 0) return emptyList();

        long maxKickerVal = results[0].getKickerValue();
        return Stream.of(results)
                .filter(v -> v.getEvaluatedValue() == maxVal && v.getKickerValue() == maxKickerVal)
                .map(EvaluationResult::getPlayer)
                .collect(Collectors.toList());
    }

    private List<Player> returnResults(EvaluationResult[] results, long maxVal) {
        if (results.length == 0) return emptyList();

        long maxKickerVal = results[0].getKickerValue();
        return Stream.of(results)
                .filter(v -> v.getEvaluatedValue() == maxVal && v.getKickerValue() == maxKickerVal)
                .map(EvaluationResult::getPlayer)
                .collect(Collectors.toList());
    }

}
