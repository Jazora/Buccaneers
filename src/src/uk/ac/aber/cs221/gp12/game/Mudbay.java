package uk.ac.aber.cs221.gp12.game;

/**
 * This class constructs and describes the features of Mud Bay.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of the class
 */
public class Mudbay extends GameObject{
    private int x = 0;
    private int y = 0;

    /**
     * This method checks the x-axis position based on Mud Bay
     * @return the x-axis position
     */
    public int getX(){
        return x;
    }

    /**
     * This method checks the y-axis position based on Mud Bay
     * @return the y-axis position
     */
    public int getY(){
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
     * This method triggers a change of state for the player being in or out of Mud Bay
     * @param player - The player the state is changing for
     */
    @Override
    public void triggered(Player player) {
        player.setPlayerState(PlayerState.AT_MUDBAY);
    }
}
