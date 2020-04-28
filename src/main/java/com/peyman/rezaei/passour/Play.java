package com.peyman.rezaei.passour;

import com.peyman.rezaei.passour.Card.Color;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(of = {"card", "wonCards"})
@Getter
@ToString
public class Play {

  private final PassourBot player;
  private final Card card;
  private final Set<Card> wonCards;
  private final int numberOfClubsYet;
  private final boolean sour;
  private final int points;

  @Setter
  private int index;

  @Builder
  public Play(PassourBot player, Card card, Set<Card> wonCards, int numberOfClubsYet, boolean sour) {
    this.player = player;
    this.card = card;
    this.wonCards = wonCards;
    this.numberOfClubsYet = numberOfClubsYet;
    this.sour = sour;
    points = calculatePoints();
  }

  private int calculatePoints() {
    // Jacks and Aces
    var result = (int) wonCards.stream().map(Card::getNumber).filter(n -> n == 1 || n == 11).count();

    //10 Diamond
    result += wonCards.stream()
        .filter(c -> Card.of(10, Color.DIAMOND).equals(c))
        .map(c -> 3)
        .findAny()
        .orElse(0);

    // 2 Clubs
    result += wonCards.stream()
        .filter(c -> Card.of(2, Color.CLUB).equals(c))
        .map(c -> 2)
        .findAny()
        .orElse(0);

    // Clubs
    var clubCount = wonCards.stream()
        .map(Card::getColor)
        .map(Color::getIndex)
        .filter(i -> i == 0)
        .count();
    if (clubCount + numberOfClubsYet <= 7) {
      result += clubCount;
    }

    if (sour) {
      result += 5;
    }

    return result;
  }

}
