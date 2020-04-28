package com.peyman.rezaei.passour;

import java.util.ArrayList;

public class Deck extends ArrayList<Card> {

  public Deck() {
    super(52);
    for (var i = 0; i <= 51; i++) {
      add(Card.of(i));
    }
  }

}
