package uk.ac.aber.cs221.gp12.game;

/**
 * Harbour enum details
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of enum class.
 */
public enum HarbourDetail {
    LONDON("London", 0, 13, ShipOrientation.EAST),
    GENOA("Genoa",6, 0, ShipOrientation.NORTH),
    MARSEILLES("Marseilles",19, 6, ShipOrientation.WEST),
    CADIZ("Cadiz", 13, 19, ShipOrientation.SOUTH),
    VENICE("Venice", 0, 6),
    AMSTERDAM("Amsterdam", 19, 13);

    String name;
    public int x, y;
    public ShipOrientation startDirection;

    HarbourDetail(String name, int x, int y) { this.name = name; this.x = x; this.y = y; }
    HarbourDetail(String name, int x, int y, ShipOrientation startDirection) { this.name = name; this.x = x; this.y = y; this.startDirection = startDirection; }
}
