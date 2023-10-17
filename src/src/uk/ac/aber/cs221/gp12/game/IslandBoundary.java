package uk.ac.aber.cs221.gp12.game;

/**
 * This class describes the Island boundaries for the game.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of the class.
 */
public class IslandBoundary extends Island {
    private Island island;
    public ShipOrientation direction;

    /**
     * This method sets the boundaries for islands.
     * @param island - The island the boundary is for.
     * @param direction - The direction of the boundary.
     */
    public IslandBoundary(Island island, ShipOrientation direction) { this.island = island; this.direction = direction; }

    /**
     * This method checks whether a position in the game is passable or not.
     * @return true if passable, false if it isn't.
     */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * This method gets the direction the ship is going towards.
     * @return the moving ship's direction.
     */
    public ShipOrientation getDirection() {
        return direction;
    }

    /**
     * This method triggers a change of state for the player going into an island.
     * @param player - The player the state is changing for
     */
    @Override
    public void triggered(Player player) {
        island.triggered(player);
    }
}
