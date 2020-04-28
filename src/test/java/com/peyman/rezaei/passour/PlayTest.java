package com.peyman.rezaei.passour;

import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;

class PlayTest {

  @Test
  void hashCodeTestNegative() {
    var play1 = Play.builder()
        .card(Card.of(45))
        .wonCards(Set.of(Card.of(46), Card.of(4), Card.of(40)))
        .build();
    var play2 = Play.builder()
        .card(Card.of(45))
        .wonCards(Set.of(Card.of(47), Card.of(3), Card.of(40)))
        .build();
    assertNotEquals(play1, play2);
  }

  @Test
  void hashCodeTestPositive() {
    var play1 = Play.builder()
        .card(Card.of(45))
        .wonCards(Set.of(Card.of(46), Card.of(4), Card.of(45)))
        .build();
    var play2 = Play.builder()
        .card(Card.of(45))
        .wonCards(Set.of(Card.of(4), Card.of(46), Card.of(45)))
        .build();
    assertEquals(play1, play2);
  }

}