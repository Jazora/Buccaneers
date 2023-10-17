package uk.ac.aber.cs221.gp12.game.gamestate;

import uk.ac.aber.cs221.gp12.game.Main;

/**
 * Start Game State Class
 * @author Jason Weller (jaw125)
 * @version 1.0
 */
public class StartGameState extends GameState {
    /**
     * This method runs the change of state during the game
     */
    @Override
    public void Run() {
        Main.getGame().setupGameSettings();
    }
}
