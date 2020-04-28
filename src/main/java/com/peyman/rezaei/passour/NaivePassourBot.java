package com.peyman.rezaei.passour;

import java.util.Set;
import java.util.stream.Collectors;

public class NaivePassourBot extends PassourBot {

  public NaivePassourBot(int id) {
    super(id);

  }

  @Override
  public void act(PassourGame game) {

    var possiblePlays = getPossiblePlays(game.getTable());
    Set<Integer> possibleActions = possiblePlays.stream().map(Play::getIndex).collect(Collectors.toSet());

    if (!possibleActions.isEmpty()) {
      play(possiblePlays.get(random.nextInt(possiblePlays.size())), game);
    }

  }

  @Override
  public void updateStrategy(PassourGame game) {

  }

}
