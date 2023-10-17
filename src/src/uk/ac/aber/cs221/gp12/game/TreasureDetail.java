package uk.ac.aber.cs221.gp12.game;

/**
 * Treasure detail enum
 * @author Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of enum class.
 */
public enum TreasureDetail {
    DIAMOND("Diamond", 5),
    RUBY("Ruby", 5),
    GOLD_BAR("Gold Bar", 4),
    PEARL("Pearl", 3),
    BARREL_OF_RUM("Rum", 2);

    String name;
    public final int worth;
    TreasureDetail(String name, int worth) {
        this.name = name;
        this.worth = worth;
    }
}
