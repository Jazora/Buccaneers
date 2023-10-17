package uk.ac.aber.cs221.gp12.game;

/**
 * This is the class responsible for creating Treasure objects.
 *
 * @author Behrooz Rezvani (ber39), Jason Weller (jaw125)
 * @version 1.0 - Initial development of the class
 */
public class Treasure {
    private TreasureDetail treasureDetail;

    /**
     * This is the constructor for treasure objects
     * @param treasureDetail - the features of the treasure
     */
    public Treasure(TreasureDetail treasureDetail) {
        this.treasureDetail = treasureDetail;
    }

    /**
     * This gets the features of a treasure.
     * @return treasureDetail - the features of a treasure
     */
    public TreasureDetail getTreasureDetail() {
        return treasureDetail;
    }
}
