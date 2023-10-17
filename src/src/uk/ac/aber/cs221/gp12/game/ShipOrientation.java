package uk.ac.aber.cs221.gp12.game;

/**
 * Ship orientation Class
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of enum class.
 */
public enum ShipOrientation {
    NORTH("North",0, 1, 0),
    NORTH_EAST("North East",1, 1, 1),
    EAST("East",1, 0, 2),
    SOUTH_EAST("South East",1, -1, 3),
    SOUTH("South",0, -1, 4),
    SOUTH_WEST("South West",-1, -1, 5),
    WEST("West",-1,0, 6),
    NORTH_WEST("North West",-1, 1, 7);

    final String name;
    public final int xOffset, yOffset, angle;
    ShipOrientation(String name, int x, int y, int angle) { this.name = name; this.xOffset = x; this.yOffset = y; this.angle = angle; }
}
