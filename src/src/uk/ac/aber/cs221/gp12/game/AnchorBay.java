package uk.ac.aber.cs221.gp12.game;

/**
 * This class constructs and describes the features of Anchor Bay.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of the class
 */
public class AnchorBay extends GameObject {

    /**
     * This method checks whether a position in the game is passable or not.
     * @return true if passable, false if it isn't.
     */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * This method triggers a change of state for the player being in or out of Anchor Bay
     * @param player - The player the state is changing for
     */
    @Override
    public void triggered(Player player) {
        player.setPlayerState(PlayerState.AT_ANCHORBAY);
    }
}
