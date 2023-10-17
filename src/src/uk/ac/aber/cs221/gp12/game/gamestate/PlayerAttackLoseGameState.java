package uk.ac.aber.cs221.gp12.game.gamestate;

import uk.ac.aber.cs221.gp12.game.Player;

/**
 * Player attack lost state class
 * @author Jason Weller (jaw125)
 * @version 1.0
 */
public class PlayerAttackLoseGameState extends GameState {

    Player player;

    /**
     * This is the state of the player losing an attack.
     * @param player - this player triggers this change of state because of losing an attack.
     */
    public PlayerAttackLoseGameState(Player player) {
        this.player = player;
    }

    /**
     * This method runs the change of state during the game
     */
    @Override
    public void Run() {
        player.setupLoseMove();
    }
}
