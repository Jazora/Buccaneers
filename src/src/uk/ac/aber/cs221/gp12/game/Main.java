package uk.ac.aber.cs221.gp12.game;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp12.game.gson.Deserialize;
import uk.ac.aber.cs221.gp12.game.gson.Serialize;
import uk.ac.aber.cs221.gp12.game.ui.UIManager;

/**
 * Main application class, this is the class that runs the program.
 *
 * @author Jason Weller (jaw125)
 * @version 1.0 - Initial development of the class.
 */
public class Main extends Application {

    private static Game game;
    private static UIManager uiManager;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This is the class that starts up the program.
     * @param stage - The map the game is played in.
     * @throws Exception if anything at the start of the game goes wrong.
     */
    @Override
    public void start(Stage stage) throws Exception {
        game = new Game();
        uiManager = new UIManager(stage);
        uiManager.addPaneFromPackage("main_menu.fxml"); // add main menu page to the UI
        uiManager.addPaneFromPackage("game.fxml"); // add game page to the UI

        //display application status
        switch (uiManager.init_UI()) {
            case -100 -> System.err.println("ERROR: No pages read");
            case 0 -> System.out.println("Application launched successful");
        }
    }

    /**
     * This is the method used to save game data and states so that when the game is finished or
     * crashes, the game can load back to a working state from what was saved.
     */
    public static void save() {
        Serialize.serializeGameObject(game, "data.json");
    }

    /**
     * This is the method the program uses to load the information onto the game. This is extracted
     * from a json file and used to run the game.
     */
    public static void load() {
        game = Deserialize.deserializeGameObject("data.json");
    }

    /**
     * This gets the game features going on.
     * @return the game that is going on.
     */
    public static Game getGame() { return game; }

    /**
     * The gets the UI elements being used at one point in time.
     * @return the UI manager elements being used for.
     */
    public static UIManager getUiManager() { return uiManager; }
}
