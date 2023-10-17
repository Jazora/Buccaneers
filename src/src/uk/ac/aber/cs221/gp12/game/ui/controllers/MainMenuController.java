package uk.ac.aber.cs221.gp12.game.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp12.game.Main;

/**
 * Simple controller class used to define the behaviour of the main_menu page.
 *
 * @author Jan F. Halaczek (jah143)
 * @version 1.0
 * @since 0.5
 */
public class MainMenuController extends ControllerParent {

    /**
     * close button used defined in the FXML file
     */
    @FXML private Label closeButton;

    /**
     * function that allows for a custom behaviour for the playButton to be defined outside the FXML file
     */
    @FXML protected void onPlayButtonClick(){
        changeScreenTo(1);
        Main.getGame().startGame();
    }

    /**
     * a function to enable to run the code whenever a user presses the load button
     */
    @FXML private void onLoad(){
        changeScreenTo(1);
        Main.load();
        Main.getGame().loadGame();
    }

    /**
     * set up the behaviour for the closeButton button
     */
    @FXML private void onExit(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
