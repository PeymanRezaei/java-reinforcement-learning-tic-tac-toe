package com.peyman.rezaei.passour;


import static org.junit.Assert.assertEquals;

import com.peyman.rezaei.passour.Card.Color;
import org.junit.jupiter.api.Test;


class CardTest {

  @Test
  void test_last_index() {
    assertEquals(Card.of(51), Card.of(13, Color.SPADE));
  }

  @Test
  void test_middle_index() {
    assertEquals(Card.of(26), Card.of(1, Color.HEART));
  }

  @Test
  void test_index() {
    assertEquals(Card.of(40), Card.of(2, Color.SPADE));
  }
}