package uk.ac.aber.cs221.gp12.game.ui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp12.game.Harbour;
import uk.ac.aber.cs221.gp12.game.Player;
import uk.ac.aber.cs221.gp12.game.ui.controllers.ControllerParent;
import uk.ac.aber.cs221.gp12.game.ui.controllers.GameController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User Interface Manager class, which handles everything UI related, it extends pop_ups to allow
 *
 * @author Jan F. Halaczek (jah143)
 * @version 1.0
 * @since 0.5
 */
public class UIManager extends PopUps {
    /**
     * width of the window
     */
    final double width = 1600;
    /**
     * height of the window
     */
    final double height = 900;
    /**
     * the current page selected
     */
    int indexer = 0;
    /**
     * the stage to be used
     */
    final Stage stage;
    /**
     * the scene to render to the stage
     */
    Scene scene;
    /**
     * the list of layout and content to display on the scene
     */
    List<AnchorPane> paneList = new ArrayList<>();
    /**
     * the list of controllers used by different pages
     */
    List<Object> controllerList = new ArrayList<>(); //the list of controllers


    /**
     * a simple UIManager constructor
     *
     * @param stage the stage to start with
     */
    public UIManager(Stage stage) {
        this.stage = stage;
    }

    /**
     * the content and the controller to add to the lists for future referance
     *
     * @param file the name of the FXML file from which the content and controller is sourced
     * @throws IOException the standard Exception for the FXMLLoader
     */
    public void addPaneFromPackage(String file) throws IOException {
        System.out.println("content/" + file);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("content/" + file));
        AnchorPane pane = loader.load();
        Object cont = loader.getController();
        ((ControllerParent) cont).setUIM(this);
        controllerList.add(cont);
        paneList.add(pane);
    }

    /**
     * a function to initialize and start the application with preset information
     *
     * @return returns whether the initialisation was a success
     */
    public int init_UI() {
        if (paneList.isEmpty()) {
            return -100;
        } else {
            scene = new Scene(paneList.get(indexer), width, height);

            ControllerParent temp_mmc = (ControllerParent) controllerList.get(0);

            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styling.css")).toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            Image img = new Image("uk/ac/aber/cs221/gp12/game/ui/content/resources/title.png");
            ImageView treasure_island = new ImageView(img);
            paneList.get(indexer).getChildren().add(treasure_island);

            treasure_island.setFitHeight(img.getHeight() * 2);
            treasure_island.setFitWidth(img.getWidth() * 2);

            treasure_island.setX((width/2) - img.getWidth());
            treasure_island.setY(60);


            temp_mmc.setUIM(this);
            temp_mmc.rollAnimation(true);
            return 0;
        }
    }

    /**
     * sets the scene to change to
     *
     * @param sc the id of the page, defined by the position in the lists of paneList and controllerList
     */
    public void setScene(int sc) {
        System.out.println("setting scene...");
        indexer = sc;
        scene.setRoot(paneList.get(indexer));
        System.out.println("scene set");
        //((ControllerParent) controllerList.get(indexer)).rollAnimation(true);
    }

    /**
     * Set a name to a specific player
     * @param name new name for a player, in a string format
     * @param pos the player that the new name applies
     */
    public void setIndividualPlayerName(String name, int pos){
        ((GameController) controllerList.get(1)).setPlayer_nameAt(name,pos);
    }

    /**
     * converts X and Y coordinates into a single value position
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return return a single value coordinate
     */
    public int convertToDisplayXY(int x, int y) {
        return ((19 - y) * 20) + (x);
    }

    /**
     * allows for the player sprites to be moved
     * @param player the player to move
     * @param XPosition the X position to move to
     * @param YPosition the Y position to move to
     */
    public void updatePlayerPosition(int player, int XPosition, int YPosition){
        int pos = convertToDisplayXY(XPosition, YPosition);
        ((GameController) controllerList.get(1)).changePlayerPos(player, pos);
    }

    /**
     * allows for the game to change the player rotation.
     * @param player player to change the angle of
     * @param angle the angle to change the angle to
     */
    public void updatePlayerRotation(int player, int angle){
        ((GameController) controllerList.get(1)).changePlayerDirection(player,angle);
    }

    /**
     * pass to the front-end the possible tiles the player is able to move to
     * @param indexer the index of the player
     * @param accessibleTiles the list of possible tiles
     */
    public void getMovement(int indexer, List<Integer> accessibleTiles){
        ((GameController) controllerList.get(1)).showPossibleMoves(indexer, accessibleTiles);
    }

    /**
     * used to get the controller list
     * @return the controller list
     */
    public List<Object> getControllerList(){
        return controllerList;
    }

    /**
     * Passes a Harbour information to the UI, for the GameController to display relevant information to the player
     * @param har the harbour to pass
     */
    public void passDisplayHarbour(Harbour har){
        ((GameController) controllerList.get(1)).display_port(har);
    }

    /**
     * Passes a Player information to the UI, for the GameController to display relevant information to the player
     * @param play the harbour to pass
     */
    public void passDisplayPlayer(Player play){
        ((GameController) controllerList.get(1)).display_player(play);
    }

    /**
     * Draw the harbours for the players to be able to see where their port is
     */
    public void drawHarbour(){
        ((GameController) controllerList.get(1)).draw_harbour();
    }
}

