package com.peyman.rezaei.passour;

import static java.util.Collections.emptySet;
import static org.junit.Assert.assertEquals;

import com.peyman.rezaei.passour.Card.Color;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassourBotTest {

  private PassourBot bot;

  @BeforeEach
  void setUp() {
    bot = new PassourBot(0) {
      @Override
      public void act(PassourGame game) {
        // Do nothing
      }

      @Override
      public void updateStrategy(PassourGame game) {
        // Do nothing
      }
    };
  }

  @Test
  void testPossiblePlaysNoCardsWon() {
    var table = Set.of(Card.of(7, Color.SPADE));
    bot.getHand().add(Card.of(2, Color.CLUB));
    var plays = bot.getPossiblePlays(table);
    assertEquals(1, plays.size());
    assertEquals(
        Play.builder()
            .card(Card.of(2, Color.CLUB))
            .wonCards(emptySet())
            .build(),
        plays.iterator().next()
    );
  }

  @Test
  void testPossiblePlaysOneCardWon() {
    var table = Set.of(Card.of(7, Color.SPADE));
    bot.getHand().add(Card.of(4, Color.HEART));
    var plays = bot.getPossiblePlays(table);
    assertEquals(1, plays.size());
    assertEquals(
        Play.builder()
            .card(Card.of(4, Color.HEART))
            .wonCards(Set.of(Card.of(7, Color.SPADE), Card.of(4, Color.HEART)))
            .build(),
        plays.iterator().next()
    );
  }

  @Test
  void testPossiblePlaysOneCardWonTwoChoices() {
    var table = Set.of(Card.of(7, Color.SPADE));
    bot.getHand().add(Card.of(4, Color.HEART));
    bot.getHand().add(Card.of(4, Color.CLUB));
    bot.getHand().add(Card.of(12, Color.CLUB));
    var plays = bot.getPossiblePlays(table);
    assertEquals(3, plays.size());
    var expected = Set.of(
        Play.builder()
            .card(Card.of(4, Color.HEART))
            .wonCards(Set.of(Card.of(7, Color.SPADE), Card.of(4, Color.HEART)))
            .build(),
        Play.builder()
            .card(Card.of(4, Color.CLUB))
            .wonCards(Set.of(Card.of(7, Color.SPADE), Card.of(4, Color.CLUB)))
            .build(),
        Play.builder()
            .card(Card.of(12, Color.CLUB))
            .wonCards(emptySet())
            .build()
    );

    assertEquals(expected, plays);
  }

  @Test
  void testPossiblePlaysMoreCombination() {
    var table = Set.of(
        Card.of(7, Color.SPADE),
        Card.of(2, Color.DIAMOND),
        Card.of(5, Color.SPADE)
    );
    bot.getHand().add(Card.of(4, Color.HEART));
    bot.getHand().add(Card.of(4, Color.CLUB));
    bot.getHand().add(Card.of(11, Color.DIAMOND));
    var plays = bot.getPossiblePlays(table);
    assertEquals(5, plays.size());
    var expected = Set.of(
        Play.builder()
            .card(Card.of(4, Color.HEART))
            .wonCards(Set.of(Card.of(7, Color.SPADE), Card.of(4, Color.HEART)))
            .build(),
        Play.builder()
            .card(Card.of(4, Color.HEART))
            .wonCards(
                Set.of(
                    Card.of(2, Color.DIAMOND),
                    Card.of(5, Color.SPADE),
                    Card.of(4, Color.HEART))
            )
            .build(),
        Play.builder()
            .card(Card.of(4, Color.CLUB))
            .wonCards(Set.of(Card.of(7, Color.SPADE), Card.of(4, Color.CLUB)))
            .build(),
        Play.builder()
            .card(Card.of(4, Color.CLUB))
            .wonCards(
                Set.of(
                    Card.of(2, Color.DIAMOND),
                    Card.of(5, Color.SPADE),
                    Card.of(4, Color.CLUB))
            )
            .build(),
        Play.builder()
            .card(Card.of(11, Color.DIAMOND))
            .wonCards(
                Set.of(
                    Card.of(7, Color.SPADE),
                    Card.of(2, Color.DIAMOND),
                    Card.of(5, Color.SPADE),
                    Card.of(11, Color.DIAMOND)
                )
            )
            .build()
    );

    assertEquals(expected, plays);
  }

  @Test
  void testPossiblePlaysQueenAndKing() {
    var table = Set.of(
        Card.of(7, Color.SPADE),
        Card.of(2, Color.DIAMOND),
        Card.of(5, Color.SPADE),
        Card.of(13, Color.SPADE),
        Card.of(13, Color.DIAMOND),
        Card.of(12, Color.CLUB)
    );
    bot.getHand().add(Card.of(13, Color.HEART));
    bot.getHand().add(Card.of(12, Color.DIAMOND));
    bot.getHand().add(Card.of(12, Color.SPADE));
    bot.getHand().add(Card.of(1, Color.HEART));
    var plays = bot.getPossiblePlays(table);
    assertEquals(5, plays.size());
    var expected = Set.of(
        Play.builder()
            .card(Card.of(13, Color.HEART))
            .wonCards(Set.of(Card.of(13, Color.SPADE), Card.of(13, Color.HEART)))
            .build(),
        Play.builder()
            .card(Card.of(13, Color.HEART))
            .wonCards(Set.of(Card.of(13, Color.DIAMOND), Card.of(13, Color.HEART)))
            .build(),
        Play.builder()
            .card(Card.of(12, Color.DIAMOND))
            .wonCards(Set.of(Card.of(12, Color.CLUB), Card.of(12, Color.DIAMOND)))
            .build(),
        Play.builder()
            .card(Card.of(12, Color.SPADE))
            .wonCards(Set.of(Card.of(12, Color.CLUB), Card.of(12, Color.SPADE)))
            .build(),
        Play.builder()
            .card(Card.of(1, Color.HEART))
            .wonCards(emptySet())
            .build()
    );

    assertEquals(expected, plays);
  }

  @Test
  void testAllPoints() {
    var stack = Set.of(
        Card.of(0),
        Card.of(1),
        Card.of(5),
        Card.of(7),
        Card.of(9),
        Card.of(10),
        Card.of(12),
        Card.of(13),
        Card.of(22),
        Card.of(23),
        Card.of(26),
        Card.of(36),
        Card.of(39),
        Card.of(49)
    );
    bot.getStack().addAll(stack);
    assertEquals(20, bot.getPoints());
  }

  @Test
  void testAllPointsNoClubs() {
    var stack = Set.of(
        Card.of(0),
        Card.of(1),
        Card.of(7),
        Card.of(9),
        Card.of(10),
        Card.of(12),
        Card.of(13),
        Card.of(22),
        Card.of(23),
        Card.of(26),
        Card.of(36),
        Card.of(39),
        Card.of(49)
    );
    bot.getStack().addAll(stack);
    assertEquals(13, bot.getPoints());
  }
  @Test
  void testAllPointsNoClubsSour() {
    var stack = Set.of(
        Card.of(0),
        Card.of(1),
        Card.of(7),
        Card.of(9),
        Card.of(10),
        Card.of(12),
        Card.of(13),
        Card.of(22),
        Card.of(23),
        Card.of(26),
        Card.of(36),
        Card.of(39),
        Card.of(49)
    );
    bot.getStack().addAll(stack);
//    bot.setSour(2);
    assertEquals(13, bot.getPoints());
  }
}