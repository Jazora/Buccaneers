package uk.ac.aber.cs221.gp12.game.gamestate;

import uk.ac.aber.cs221.gp12.game.Game;
import uk.ac.aber.cs221.gp12.game.Main;

/**
 * End Round Game State Class
 * @author Jason Weller (jaw125)
 * @version 1.0
 */
public class EndRoundGameState extends GameState {
    /**
     * This method runs the change of state during the game
     */
    @Override
    public void Run() {
        Main.save();
        Main.getGame().setupPlayerTurns();
    }
}
