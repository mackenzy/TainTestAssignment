package se.tain;

import java.util.List;

public interface WinnerDeterminator {

    List<Player> define(Card inputCard, List<Player> players);

}
