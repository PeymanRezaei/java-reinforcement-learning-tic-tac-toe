package com.peyman.rezaei.passour;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Builder
public class State {

  private static List<Set<Card>> possibleStates;

  @Getter
  private static final Set<Card> pointCards = Set.of(
      Card.of(0),
      Card.of(1),
      Card.of(2),
      Card.of(3),
      Card.of(4),
      Card.of(5),
      Card.of(6),
      Card.of(7),
      Card.of(8),
      Card.of(9),
      Card.of(10),
      Card.of(11),
      Card.of(12),
      Card.of(13),
      Card.of(22),
      Card.of(23),
      Card.of(26),
      Card.of(36),
      Card.of(39),
      Card.of(49)
  );

  private static final int COUNT = (int) Math.pow(2, getPointCards().size());

  private static final int POSSIBLE_SOURS = 23;

  private final Set<Card> stack;

  private final int sour;

  public static List<Set<Card>> getPossibleStates() {
    if (possibleStates == null) {
      possibleStates = new ArrayList<>();
      for (int i = 0; i <= pointCards.size(); i++) {
        //noinspection UnstableApiUsage
        possibleStates.addAll(Sets.combinations(pointCards, i));
      }
    }
    return possibleStates;
  }

  public static int getStateCount() {
    return COUNT;
  }

  public int getStateIndex() {
    return getPossibleStates().indexOf(Sets.intersection(getPointCards(), stack));
  }
}
