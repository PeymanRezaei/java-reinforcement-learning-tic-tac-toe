package com.peyman.rezaei.passour;

import com.github.chen0040.rl.learning.qlearn.QLearner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PassourDojoQ {

  private static final String ITERATION = "Iteration: {} / {}";

  public static double test(QLearner model, int episodes) {

    PassourGame game;
    var player1 = new QPassourBot(1, model);
    var player2 = new NaivePassourBot(2);

    int wins = 0;
    for (int i = 0; i < episodes; ++i) {
      player1.reset();
      player2.reset();
      game = new PassourGame(player1, player2);
      log.info(ITERATION, (i + 1), episodes);

      while (game.canBePlayed()) {
        player1.act(game);
        player2.act(game);
      }

      log.info("Player1: {} points", player1.getPoints());
      log.info("Player2: {} points", player2.getPoints());
      int win = game.didIWin(player1.getId());
      String nameOfWinner = win == 1 ? player1.toString() : win == 0 ? "both" : player2.toString();
      log.info("Winner: {}", nameOfWinner);
      wins += win == 1 ? 1 : 0;
    }

    return wins * 1.0 / episodes;

  }

  public static QLearner trainAgainstSelf(int episodes) {

    int stateCount = State.getStateCount();
    int actionCount = 24;

    QLearner learner = new QLearner(stateCount, actionCount);
    //learner.setActionSelection(SoftMaxActionSelectionStrategy.class.getCanonicalName());

    QPassourBot player1 = new QPassourBot(1, learner);
    QPassourBot player2 = new QPassourBot(2, learner);

    PassourGame game;

    for (int i = 0; i < episodes; ++i) {
      log.info(ITERATION, (i + 1), episodes);
      player1.reset();
      player2.reset();
      game = new PassourGame(player1, player2);
      while (game.canBePlayed()) {
        player1.act(game);
        player2.act(game);
      }

      log.info("Player1: {} points", player1.getPoints());
      log.info("Player2: {} points", player2.getPoints());

      player1.updateStrategy(game);
      player2.updateStrategy(game);
    }

    return learner;
  }

  public static QLearner trainAgainstNaiveBot(int episodes) {

    int stateCount = State.getStateCount();
    int actionCount = 24;

    QLearner learner = new QLearner(stateCount, actionCount);
    //learner.setActionSelection(SoftMaxActionSelectionStrategy.class.getCanonicalName());

    QPassourBot player1 = new QPassourBot(1, learner);
    NaivePassourBot player2 = new NaivePassourBot(2);

    PassourGame game;

    int wins = 0;

    for (int i = 0; i < episodes; ++i) {
      log.info(ITERATION, (i + 1), episodes);
      player1.reset();
      player2.reset();
      game = new PassourGame(player1, player2);
      while (game.canBePlayed()) {
        player1.act(game);
        player2.act(game);
      }

      player1.updateStrategy(game);

      wins += game.didIWin(player1.getId()) == 1 ? 1 : 0;
      log.info("success rate: {} %", (wins * 100.000000) / (i + 1));
    }

    return learner;
  }

  public static void main(String[] args) {

    QLearner model = trainAgainstNaiveBot(30000);

    double cap = test(model, 1000);
    log.info("Q-Learn Bot beats Random Bot in {} % of the games", cap * 100);
  }

}
