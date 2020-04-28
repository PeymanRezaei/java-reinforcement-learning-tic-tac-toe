package com.peyman.rezaei.passour;

import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(of = {"card", "wonCards"})
@Builder
@Getter
@ToString
public class Play {

  private final PassourBot player;
  private final Card card;
  private final Set<Card> wonCards;
  @Setter
  private int index;

}
