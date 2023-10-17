package uk.ac.aber.cs221.gp12.game;

/**
 * This is the class which constructs Game objects.
 *
 * @author Jason Weller (jaw125)
 * @version 1.0 - Initial development of the class.
 */
public class GameObject {

    /**
     * This method checks whether a position in the game is passable or not.
     * @return true if passable, false if it isn't.
     */
    public boolean isPassable() {
        return false;
    }

    /**
     * This method triggers a change of state for the player
     * @param player - The player the state is changing for
     */
    public void triggered(Player player) { }
}
