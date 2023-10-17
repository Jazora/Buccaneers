package uk.ac.aber.cs221.gp12.game;

import java.util.*;

public class ChanceCard {
    private final ChanceCardDetail chanceCardDetail;

    /**
     * This method constructs chance cards.
     * @param chanceCardDetail - the features of chance cards.
     */
    public ChanceCard(ChanceCardDetail chanceCardDetail) {
        this.chanceCardDetail = chanceCardDetail;
    }

    /**
     * This method gets the details of chance cards.
     * @return details of chance cards.
     */
    public ChanceCardDetail getChanceCardDetail() {
        return chanceCardDetail;
    }
}
