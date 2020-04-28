package com.peyman.rezaei.passour;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class PassourGameTest {

  @Test
  void setTable(){
    var game = new PassourGame(new NaivePassourBot(1), new NaivePassourBot(2));
    assertEquals(4, game.getTable().size());
  }

}