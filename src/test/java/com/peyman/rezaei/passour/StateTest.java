package com.peyman.rezaei.passour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;

class StateTest {

  @Test
  void hashCodeTestPositive() {
    var state1 = State.builder().sour(0).stack(Set.of(Card.of(11), Card.of(2), Card.of(50))).build();
    var state2 = State.builder().sour(0).stack(Set.of(Card.of(2), Card.of(11), Card.of(51), Card.of(40))).build();
    assertEquals(state1.getStateIndex(), state2.getStateIndex());
  }

  @Test
  void hashCodeTestNegative() {
    var state1 = State.builder().sour(0).stack(Set.of(Card.of(11), Card.of(1), Card.of(13), Card.of(50))).build();
    var state2 = State.builder().sour(0).stack(Set.of(Card.of(13), Card.of(11), Card.of(50))).build();
    assertNotEquals(state1.getStateIndex(), state2.getStateIndex());
  }

  @Test
  void testStateCount(){
    assertEquals(State.getStateCount(), State.getPossibleStates().size());
  }

}