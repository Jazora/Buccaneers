package uk.ac.aber.cs221.gp12.game.gamestate;

import uk.ac.aber.cs221.gp12.game.Player;

/**
 * Player turn game state class
 * @author Jason Weller (jaw125)
 * @version 1.0
 */
public class PlayerTurnGameState extends GameState {
    Player player;

    /**
     * This changes the state of turns during the game.
     * @param player - this player will make the turn and change of state.
     */
    public PlayerTurnGameState(Player player) {
        this.player = player;
    }

    /**
     * This method runs the change of state during the game
     */
    @Override
    public void Run() {
        player.setupPlayerTurn();
    }
}
