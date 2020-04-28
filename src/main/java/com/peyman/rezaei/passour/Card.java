package com.peyman.rezaei.passour;

import java.util.Arrays;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "index")
public class Card {

  private final int number;
  private final Color color;

  // 0 to 51
  private final int index;

  private Card(int number, Color color) {
    this.number = number;
    this.color = color;
    index = color.getIndex() * 13 + number - 1;
  }

  public static Card of(int card, Color color) {
    return new Card(card, color);
  }

  public static Card of(int index) {
    return new Card(index % 13 + 1, Color.of(index / 13));
  }

  @Getter
  public enum Color {
    CLUB(0, '\u2663'),
    DIAMOND(1, '\u2666'),
    HEART(2, '\u2764'),
    SPADE(3, '\u2660');

    private final int index;

    private final char character;

    Color(int index, char character) {
      this.index = index;
      this.character = character;
    }

    public static Color of(int index) {
      return Arrays.stream(values())
          .filter(color1 -> color1.getIndex() == index)
          .findFirst()
          .orElseThrow();
    }
  }

  @Override
  public String toString() {
    String numberString;
    if (number == 13) {
      numberString = "K";
    } else if (number == 12) {
      numberString = "Q";
    } else if (number == 11) {
      numberString = "J";
    } else if (number == 1) {
      numberString = "A";
    } else {
      numberString = String.valueOf(number);
    }
    return String.valueOf(color.getCharacter()) + numberString;
  }
}
