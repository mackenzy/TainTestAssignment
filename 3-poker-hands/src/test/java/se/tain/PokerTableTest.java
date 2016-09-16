package se.tain;

import se.tain.texasholdem.TexasHoldemDeterminator;
import se.tain.texasholdem.evaluators.*;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;

public class PokerTableTest {

    public static void main(String[] args) {
        List<String> сardDeck = asList(
                "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "TH", "JH", "QH", "KH", "AH",
                "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "TD", "JD", "QD", "KD", "AD",
                "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "TC", "JC", "QC", "KC", "AC",
                "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "TS", "JS", "QS", "KS", "AS"
        );
        Collections.shuffle(сardDeck);
        Collections.shuffle(сardDeck);
        Collections.shuffle(сardDeck);
        Collections.shuffle(сardDeck);
        Collections.shuffle(сardDeck);


        Iterator<String> it = сardDeck.stream().iterator();

        List<Player> players = asList(
                new Player( "Pete", it.next() + it.next()),
                new Player( "John", it.next() + it.next()),
                new Player( "Bob", it.next() + it.next())
        );
        players.stream().map(p->p.getPlayerName()+'['+p.getHoleCards()+']').forEachOrdered(System.out::println);


        Stream<String> consoleInputDealerStream = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED),
                false
        );


        PokerTable testTable = new PokerTable(
                new TexasHoldemDeterminator(
                        new RoyalFlushEvaluator(),
                        new FourOfAKindEvaluator(),
                        new StraightFlushEvaluator(),
                        new FullHouseEvaluator(),
                        new FlushEvaluator(),
                        new StraightEvaluator(),
                        new ThreeOfAKindEvaluator(),
                        new TwoPairEvaluator(),
                        new PairEvaluator(),
                        new HighCardEvaluator(),
                        new KickerEvaluator())
        );

        testTable.deal(
                consoleInputDealerStream,
                players
        ).forEach( System.out::println );
    }

}