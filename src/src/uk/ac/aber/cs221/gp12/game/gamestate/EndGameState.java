package uk.ac.aber.cs221.gp12.game.gamestate;

import uk.ac.aber.cs221.gp12.game.Main;
import uk.ac.aber.cs221.gp12.game.Player;

/**
 * EndGameState Class
 * @author Jason Weller (jaw125)
 * @version 1.0
 */
public class EndGameState extends GameState {
    Player player;

    /**
     * This method shows the game ending state.
     * @param player - the player who wins and ends the game.
     */
    public EndGameState(Player player) { this.player = player; }

    /**
     * This method runs the change of state during the game
     */
    @Override
    public void Run() {
        Main.getUiManager().makePopup(player.getName() + "has won the game!");
    }
}
