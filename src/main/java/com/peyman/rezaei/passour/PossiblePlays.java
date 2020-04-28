package com.peyman.rezaei.passour;

import java.util.ArrayList;

public class PossiblePlays extends ArrayList<Play> {

  @Override
  public boolean add(Play play) {
    play.setIndex(this.size());
    return super.add(play);
  }
}
