package uk.ac.aber.cs221.gp12.game;

/**
 * This class constructs and describes the features of Cliff's Creek.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of the class
 */
public class CliffCreek extends GameObject {
    private int x = 19;
    private int y = 19;

    /**
     * This method checks the x-axis position based on Cliff's Creek
     * @return the x-axis position
     */
    public int getX() {
        return x;
    }

    /**
     * This method checks the y-axis position based on Cliff's Creek
     * @return the y-axis position
     */
    public int getY() {
        return y;
    }

    /**
     * This method checks whether a position in the game is passable or not.
     * @return true if passable, false if it isn't.
     */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * This method triggers a change of state for the player being in or out of Cliff's Creek
     * @param player - The player the state is changing for
     */
    @Override
    public void triggered(Player player) {
        player.setPlayerState(PlayerState.AT_CLIFFCREEK);
    }
}
