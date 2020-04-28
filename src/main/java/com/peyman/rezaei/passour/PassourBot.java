package com.peyman.rezaei.passour;

import static java.util.Collections.emptySet;

import com.github.chen0040.jrl.ttt.Move;
import com.google.common.collect.Sets;
import com.peyman.rezaei.passour.Card.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
public abstract class PassourBot {

  @Getter
  private final Set<Card> hand;
  @Getter
  private final Set<Card> stack;
  @Getter
  private final List<Play> plays;

  @Setter(AccessLevel.PACKAGE)
  private int sour;

  protected List<Move> moves = new ArrayList<>();

  protected static Random random = new Random(42);

  protected double[] reward = new double[3];

  @Getter
  private final int id;

  public PassourBot(int id) {
    this.id = id;
    hand = new HashSet<>();
    stack = new HashSet<>();
    plays = new ArrayList<>();

    reward[0] = -3;
    reward[1] = 100;
    reward[2] = -100;
  }

  public abstract void act(PassourGame game);

  public abstract void updateStrategy(PassourGame game);

  public int getState() {
    return State.builder()
        .sour(sour)
        .stack(stack)
        .build()
        .getStateIndex();
  }

  public List<Play> getPossiblePlays(Set<Card> table) {
    var possiblePlays = new PossiblePlays();
    var combinations = new HashSet<Set<Card>>();
    for (int i = 1; i <= table.size(); i++) {
      //noinspection UnstableApiUsage
      combinations.addAll(Sets.combinations(table, i));
    }
    for (Card card : hand) {
      var noWinPlay = Play.builder().player(this).card(card).wonCards(emptySet()).build();
      possiblePlays.add(noWinPlay);
      // Jack
      if (card.getNumber() == 11) {
        var allCardsButPics = table
            .stream()
            .filter(card1 -> card.getNumber() <= 11)
            .collect(Collectors.toSet());
        if (!allCardsButPics.isEmpty()) {
          allCardsButPics.add(card);
        }
        possiblePlays.remove(noWinPlay);
        possiblePlays.add(
            Play.builder()
                .player(this)
                .card(card)
                .wonCards(allCardsButPics)
                .numberOfClubsYet(countClubs())
                .build()
        );
      } else {
        for (var combination : combinations) {
          // Queen and King
          if (card.getNumber() > 11 && combination.size() == 1) {
            Card c = combination.iterator().next();
            if (card.getNumber() == c.getNumber()) {
              possiblePlays.remove(noWinPlay);
              possiblePlays.add(
                  Play.builder()
                      .player(this)
                      .card(card)
                      .wonCards(Set.of(card, c))
                      .numberOfClubsYet(countClubs())
                      .build()
              );
            }
          }
          //other cards
          if (card.getNumber() <= 11) {
            int sum = combination.stream().map(Card::getNumber).reduce(Integer::sum).orElse(0);
            if (card.getNumber() + sum == 11) {
              possiblePlays.remove(noWinPlay);
              possiblePlays.add(
                  Play.builder()
                      .player(this)
                      .card(card)
                      .wonCards(Sets.union(combination, Set.of(card)))
                      .numberOfClubsYet(countClubs())
                      .build()
              );
            }
          }
        }
      }
    }
    return possiblePlays;
  }

  protected void play(Play play, PassourGame game) {
    getPlays().add(play);
    hand.remove(play.getCard());
    stack.addAll(play.getWonCards());
    game.getTable().add(play.getCard());
    game.getTable().removeAll(play.getWonCards());
    if (!play.getWonCards().isEmpty()) {
      game.setLastWinner(this);
    }
    if (play.isSour()) {
      sour++;
    }
    if (hand.isEmpty()) {
      if (game.canBePlayed()) {
        hand.addAll(game.dealHand());
      } else {
        if (this.equals(game.getLastWinner())) {
          stack.addAll(game.getTable());
        }
      }
    }

  }

  public int getPoints() {
    Integer points = plays.stream().map(Play::getPoints).reduce(Integer::sum).orElse(0);
    log.info("Player {} has {} points", id, points);
    return points;
  }

  private int countClubs() {
    return (int) hand.stream().map(Card::getColor).filter(color -> color == Color.CLUB).count();
  }

  public void reset() {
    hand.clear();
    stack.clear();
    moves.clear();
    plays.clear();
    sour = 0;
  }
}
