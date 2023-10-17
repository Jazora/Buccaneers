package uk.ac.aber.cs221.gp12.game.ui.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import uk.ac.aber.cs221.gp12.game.ui.UIManager;

/**
 * a base controller class to standardise controllers, allowing them to work with UIManger
 *
 * @author Jan F. Halaczek (jah143)
 * @version 1.0
 * @since 0.5
 */
public class ControllerParent {
    /**
     * the pane to be edited by the controller
     */
    @FXML AnchorPane content_page;

    @FXML Rectangle cover_screen;

    /**
     * the UIManager to communicate with
     */
    UIManager uim;

    /**
     * the width of the application
     */
    double width = 1600;
    /**
     * the height of the application
     */
    double height = 900;

    /**
     * the option used to change the screen to
     */
    int selectedOption = 0;

    /**
     * function to signal UIManger to change the page
     * @param page the page to change to
     */
    public void changeScreenTo(int page) {
        selectedOption = page;
        uim.setScene(selectedOption);
        //rollAnimation(true);
    }

    /**
     * simple dynamic animation used for transition of the pages
     * @param intro whether the animation is for exiting of the page or entry
     */

    public void rollAnimation(boolean intro){
        cover_screen.setId("cover_screen");
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(400));
        tt.setNode(cover_screen);
        if(intro){
            tt.setByY(height);
            cover_screen.setY(0);
            tt.setOnFinished(null);
        }else{
            tt.setOnFinished(handler);
            tt.setByY(-height);
        }
        tt.setCycleCount(1);
        tt.setAutoReverse(false);

        tt.play();
        //content_page.getChildren().remove(cover_screen);
    }

    /**
     * handler used to handle changing screens
     */
    EventHandler<ActionEvent> handler = new EventHandler<>(){
        @Override
        public void handle(ActionEvent actionEvent){
            uim.setScene(selectedOption);
        }
    };

    /**
     * sets the UIManager instance to call
     * @param uim the UIM to call
     */
    public void setUIM(UIManager uim){
        this.uim = uim;
    }
}
