package com.peyman.rezaei.passour;

import com.github.chen0040.jrl.ttt.Move;
import com.github.chen0040.rl.learning.qlearn.QLearner;
import com.github.chen0040.rl.utils.IndexValue;
import java.util.Set;
import java.util.stream.Collectors;

public class QPassourBot extends PassourBot {

  private final QLearner agent;

  public QPassourBot(int id, QLearner learner) {
    super(id);
    this.agent = learner;
  }

  @Override
  public void act(PassourGame game) {
    int state = getState();

    var possiblePlays = getPossiblePlays(game.getTable());
    Set<Integer> possibleActions = possiblePlays.stream().map(Play::getIndex).collect(Collectors.toSet());

    int action = -1;
    if (!possibleActions.isEmpty()) {
      IndexValue iv = agent.selectAction(state, possibleActions);
      action = iv.getIndex();
      double value = iv.getValue();

      if (value <= 0) {
        action = possiblePlays.get(random.nextInt(possiblePlays.size())).getIndex();
      }

      play(possiblePlays.get(action), game);
    }

    if (action != -1) {
      int newState = getState();
      moves.add(new Move(state, action, newState, 0));
    }
  }

  @Override
  public void updateStrategy(PassourGame game) {

    double reward = this.reward[game.didIWin(getId())];

    for (int i = moves.size() - 1; i >= 0; --i) {
      Move move = moves.get(i);
      if (i >= moves.size() - 2) {
        move.reward = reward;
      }
      agent.update(move.oldState, move.action, move.newState, move.reward);
    }

  }
}
