package com.peyman.rezaei.passour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class PassourGame {

  private final Set<Card> table;
  private final Deck deck;
  private final PassourBot player1;
  private final PassourBot player2;
  private final List<Play> plays;

  @Getter(AccessLevel.NONE)
  private final Random random;

  public PassourGame(PassourBot player1, PassourBot player2) {
    this.player1 = player1;
    this.player2 = player2;
    random = new Random();
    deck = new Deck();
    table = new HashSet<>();
    plays = new ArrayList<>();
    setTable();
    player1.getHand().addAll(dealHand());
    player2.getHand().addAll(dealHand());
  }

  void setTable() {
    int i = 0;
    while (i < 4) {
      var index = random.nextInt(deck.size());
      Card card = deck.get(index);
      if (card.getNumber() == 11) {
        continue;
      }
      table.add(card);
      deck.remove(card);
      i++;
    }
  }

  public Set<Card> dealHand() {
    var hand = new HashSet<Card>();
    if (deck.isEmpty()) {
      return hand;
    }
    for (int i = 0; i < 4; i++) {
      var index = random.nextInt(deck.size());
      Card card = deck.get(index);
      hand.add(card);
      deck.remove(card);
    }
    return hand;
  }

  public int didIWin(int id) {

    var point1 = player1.getPoints();
    var point2 = player2.getPoints();

    if (point1 > point2 && player1.getId() == id ||
        point2 > point1 && player2.getId() == id) {
      //Win
      log.info("Player {} won", id);
      return 1;
    }

    if (point1 == point2) {
      //Draw
      log.info("Players played a draw");
      return 0;
    }

    // Loss
    log.info("Player {} lost", id);
    return 2;
  }

  public int getRound() {
    return 7 - (deck.size() - 4) / 8;
  }

  public boolean canBePlayed() {
    return !deck.isEmpty() || !player1.getHand().isEmpty() || !player2.getHand().isEmpty();
  }

}
