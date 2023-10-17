package uk.ac.aber.cs221.gp12.game.ui.controllers;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import uk.ac.aber.cs221.gp12.game.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * a controller class used to define behaviour of game page
 * @author Jan F. Halaczek (jah143)
 * @version 0.9
 * @since 0.5
 */
public class GameController extends ControllerParent implements Initializable {

    /**
     * the board created in GridPane to allow easier tile placement
     */
    @FXML GridPane board;

    /**
     * the size in px to display tiles
     */
    double tile_size = 40;
    /**
     * temporary test player info used for testing the draw_players
     */
    public double[] players_start = {0,0,0,0,0,0,0,0,0,0,0,0}; //player info: [Position, orientation, points]

    /**
     * Players' name to display on the game screen
     */
    private final String[] player_name = {"na","na","na","na"};

    /**
     * the x board origin, made to prevent duplicate code
     */
    double board_origin_X = (width/2) - (10 * tile_size);

    /**
     * the y board origin, made to prevent duplicate code
     */
    double board_origin_Y = (height/2) - (10 * tile_size);

    /**
     * set the name of a specific player
     * @param newName new name for the player
     * @param pos player to change the name
     */
    public void setPlayer_nameAt(String newName, int pos){
        player_name[pos] = newName;
        ((Label)content_page.lookup(".player_name_" + pos)).setText(this.player_name[pos]);
    }

    /**
     * overwritten function from Initialise to get code to execute code upon loading of the page
     * @param url standard variable for initialize()
     * @param resourceBundle standard variable for initialize()
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Screen Size X: " + width + ", Y: " + height);

        draw_board(content_page);
        cover_screen.toFront();
        //rollAnimation(true);
    }

    /**
     * draw players on the screen
     * @param root the pane to draw to
     * @param players the info used to draw the players
     */
    private void draw_players(AnchorPane root, double[] players){ //players might be redrawn multiple times


        Rectangle player_blackboard = new Rectangle(board_origin_X, board_origin_Y + (tile_size * (5)), Color.BLACK);
        content_page.getChildren().add(player_blackboard);
        player_blackboard.setLayoutX(board_origin_X + (21*tile_size));
        player_blackboard.setLayoutY(40);
        player_blackboard.setOpacity(0.2);

        for(int i = 0; i < players.length; i+=3){
            if(players[i] != -1){
                String path = "uk/ac/aber/cs221/gp12/game/ui/content/resources/ship_" + String.format("%d", (i/3) + 1) + ".png";
                System.out.println("Loaded ship: " + i);
                Image img = new Image(path);
                ImageView ship = new ImageView(img);

                ship.setId("player_ship_" + i/3);
                root.getChildren().add(ship);
                ship.setLayoutX(board_origin_X + (tile_size * (players[i] % 20)));
                ship.setLayoutY(board_origin_Y + (tile_size * Math.round(players[i]/20)));



                double angle = ((int) players[i+1] * (Math.PI/4)) * (180/Math.PI);
                System.out.println("angle: " + angle);
                ship.setRotate(angle);
                System.out.println("Ship " + ship.getId() + " created!");

                double xPos = board_origin_X + (tile_size * 21);
                double yPos = board_origin_Y + (tile_size/2 * (i-1));

                addLabel(root, player_name[(i/3)], xPos + 10,yPos + 20 , "player_name_" + (i/3));

            }else{
                break;
            }
        }
    }

    /**
     * draw the harbour tiles to to show which player belongs to where
     */
    public void draw_harbour(){
        Player[] players_list = Main.getGame().getPlayers();
        Harbour[] harbours = Main.getGame().getHarbours();

        for(int i = 0; i < players_list.length; i++){
            Player pl = players_list[i];
            add_image_to_board("uk/ac/aber/cs221/gp12/game/ui/content/resources/Port" + (i + 1) + ".png",1,1,pl.getHomePort().getHarbourDetail().x, 19 - pl.getHomePort().getHarbourDetail().y, (double)(pl.getHomePort().getHarbourDetail().startDirection.angle + 4) * (360.0/8.0));
        }

        add_image_to_board("uk/ac/aber/cs221/gp12/game/ui/content/resources/Port.png",1,1,harbours[4].getHarbourDetail().x, 19 - harbours[4].getHarbourDetail().y, (6) * (360.0/8.0));
        add_image_to_board("uk/ac/aber/cs221/gp12/game/ui/content/resources/Port.png",1,1,harbours[5].getHarbourDetail().x, 19 - harbours[5].getHarbourDetail().y, (2) * (360.0/8.0));

    }

    /**
     * draw the board and the general UI for the game
     * @param root the pane to draw to
     */
    private void draw_board(AnchorPane root){ //board only needs to be drawn once

        //edge tiles
        for(int yy = 0; yy < 22; yy++){
            for(int xx = 0; xx < 22; xx++){
                Rectangle piece = new Rectangle(tile_size,tile_size,Color.BLACK);
                board.add(piece,(xx),(yy));
            }
        }

        //blue tiles
        for(int yy = 0; yy < 20; yy++){
            for(int xx = (yy % 2); xx < 20; xx+=2){
                Rectangle piece = new Rectangle(tile_size,tile_size,Color.LIGHTBLUE);
                board.add(piece,xx + 1,yy + 1);
            }
        }

        //white tiles
        for(int yy = 0; yy < 20; yy++){
            for(int xx = ((yy+1) % 2); xx < 20; xx+=2){
                Rectangle piece = new Rectangle(tile_size,tile_size,Color.WHITE);
                board.add(piece,xx + 1,yy +1);
            }
        }

        board.setLayoutX(board_origin_X - tile_size);
        board.setLayoutY(board_origin_Y - tile_size);

        //resources\Flat_Island_Placeholder.png
        add_image_to_board("uk/ac/aber/cs221/gp12/game/ui/content/resources/Flat_Island.png", 3,4,1,1, 0);

        //add pirate island
        add_image_to_board("uk/ac/aber/cs221/gp12/game/ui/content/resources/Pirate_Island.png", 3,4,16,15, 0);

        //add Treasure Island
        add_image_to_board("uk/ac/aber/cs221/gp12/game/ui/content/resources//Treasure_Island.png",  4,4, 8, 8, 0);

        //frame
        add_image_to_board("uk/ac/aber/cs221/gp12/game/ui/content/resources/Border2.png",22,22,-1,-1,0);

        //anchor bay
        add_image_to_board("uk/ac/aber/cs221/gp12/game/ui/content/resources/Anchor.png", 1,1,20,20,0);

        draw_players(root, players_start);

    }

    /**
     * help function to prevent duplicate code when adding images to the board
     * @param path the filepath to the image
     * @param width the width of the image
     * @param height the height of the image
     * @param xPos the X position of the image
     * @param yPos the Y position of the image
     */
    private void add_image_to_board(String path, double width, double height, double xPos, double yPos, double angle){
        Image img = new Image(path);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(tile_size * height);
        imgView.setFitWidth(tile_size * width);
        imgView.setRotate(angle);
        content_page.getChildren().add(imgView);
        imgView.setX(board_origin_X + (tile_size * xPos));
        imgView.setY(board_origin_Y + (tile_size * yPos));
    }


    /**
     * a function to easily add a label to the pane
     * @param root the pane to draw to
     * @param msg the massage for the tile
     * @param xx the x position of the label
     * @param yy the y position of the label
     * @param id the id of the label, used for CSS
     */
    private void addLabel(AnchorPane root, String msg, double xx, double yy, String id){
        Label new_label = new Label(msg);
        root.getChildren().add(new_label);
        new_label.setLayoutX(xx);
        new_label.setLayoutY(yy);
        new_label.getStyleClass().add(id);
    }

    /**
     * changes player's location to the specified location
     * @param player the player to move
     * @param pos the position to move the player to
     */
    public void changePlayerPos(int player, double pos){

        if((content_page.lookup("#player_ship_" + player)) == null){
            System.err.println("ERROR: unable to find player: " + "#player_ship_" + player);
        }else{
            TranslateTransition tt = new TranslateTransition();
            tt.setDuration(Duration.seconds(3));
            tt.setNode(content_page.lookup("#player_ship_" + player));
            tt.setToX((tile_size * (pos % 20)));
            tt.setToY((tile_size * Math.round(pos/20)));
            tt.setOnFinished(null);
            tt.setCycleCount(1);
            tt.setAutoReverse(false);
            tt.play();
        }
    }

    /**
     * change the players position to one of the 8 different directions
     * @param player the player to change to direction of
     * @param angle the angle to change the player's direction to
     */
    public void changePlayerDirection(int player, int angle){
        if((content_page.lookup("#player_ship_" + player)) == null){
            System.err.println("ERROR: unable to find player: " + "#player_ship_" + player);
        }else{
            double rot = (angle * (Math.PI/4)) * (180/Math.PI);

            RotateTransition rt = new RotateTransition();
            rt.setDuration(Duration.seconds(2));
            rt.setNode(content_page.lookup("#player_ship_" + player));
            rt.setToAngle(rot);
            rt.setOnFinished(null);
            rt.setCycleCount(1);
            rt.setAutoReverse(false);
            rt.play();
        }
    }

    /**
     * Handler used to determine how far the players' want to travel
     */
    EventHandler<MouseEvent> onMoveTileClicked = mouseEvent -> {
        Rectangle move_tile = (Rectangle) mouseEvent.getSource();
        System.out.println(move_tile.getId());
        String[] parts = move_tile.getId().split("_");

        for(int i = 0; i < 20; i++){
            if(content_page.lookup("#" + parts[0] + "_" + i + "_" + parts[2] + "_" + parts[3]) == null){ //check if tile number i exist
                System.err.println("Can't find: " + "#" + parts[0] + "_" + i + "_" + parts[2] + "_" + parts[3]);
            }else{
                content_page.getChildren().remove(content_page.lookup("#" + parts[0] + "_" + i + "_" + parts[2] + "_" + parts[3])); // remove the tile number i for x player
            }
        }

        Main.getGame().getPlayers()[Integer.parseInt(parts[3])].move(Integer.parseInt(parts[1]));
    };

    /**
     * allows for an x coordinate from 20x20 coordinates to be translated into the onscreen position
     * @param x the x coordinate to translate to onscreen position
     * @return the onscreen x position
     */
    double xToBoardPositionX(int x) {
        double board_origin_X = (width/2) - (10 * tile_size);
        return board_origin_X + (tile_size * x);
    }

    /**
     * allows for an y coordinate from 20x20 coordinates to be translated into the onscreen position
     * @param y the y coordinate to translate to onscreen position
     * @return the onscreen y position
     */
    double yToBoardPositionY(int y) {
        double board_origin_Y = ((height/2) + (9 * tile_size));
        return board_origin_Y - (tile_size * y);
    }

    /**
     * display to the players the possible tiles they can move to
     * @param index the player that can move
     * @param possible_tiles list of tiles the player is able to move to, relative to the player's ship
     */
    public void showPossibleMoves(int index, List<Integer> possible_tiles){

        for(int i = 0; i < possible_tiles.size(); i+=2){
            Rectangle position_tile = new Rectangle(tile_size, tile_size, Color.GREEN);
            position_tile.setOpacity(0.5);
            position_tile.setId("moveTile_" + i/2 + "_player_" + index);
            position_tile.setOnMouseClicked(onMoveTileClicked);
            content_page.getChildren().add(position_tile);

            position_tile.setX(xToBoardPositionX(possible_tiles.get(i)));
            position_tile.setY(yToBoardPositionY(possible_tiles.get(i + 1)));

            System.out.println("new tile at: [" + xToBoardPositionX(possible_tiles.get(i)) + "," + yToBoardPositionY(possible_tiles.get(i + 1)) + "]");

        }
    }

    /**
     * display the port card, displaying the port information like the contents or the name of the port
     * @param harbour the port to display the information from
     */
    public void display_port(Harbour harbour){

        remove_display_port();

        Player[] players = Main.getGame().getPlayers();

        Group port_group = display_(board_origin_X - 40, 320, 10,10);

        StringBuilder strBuild = new StringBuilder();


        Label title = new Label(harbour.getHarbourDetail().name());

        for(Player p: players){
            if(p.getHomePort().getHarbourDetail().name().equals(harbour.getHarbourDetail().name())){
                int index = p.getIndex();
                title.getStyleClass().add("player_name_" + index);
                break;
            }
            title.getStyleClass().add("port_name");
        }

        //crew cards
        List<CrewCard> cc = harbour.getCrewCards();
        getCrewCardSummary(strBuild, cc);

        List<Treasure> player_treasure = harbour.getTreasures();

        strBuild.append("Treasure:\n");

        for (Treasure treasure : player_treasure) {
            strBuild.append(treasure.getTreasureDetail().name()).append("\t").append("[").append(treasure.getTreasureDetail().worth).append("pts]").append("\n");
        }
        strBuild.append("\n");

        Label content = new Label(strBuild.toString());
        content.getStyleClass().add("port_name");
        title.setLayoutX(30);
        title.setLayoutY(20);

        content.setLayoutY(60);
        content.setLayoutX(30);

        port_group.getChildren().add(title);
        port_group.getChildren().add(content);



        port_group.setId("port_group");

        content_page.getChildren().add(port_group);

    }

    /**
     * build a crew card string, made to prevent duplication of code
     * @param strBuild the string builder to use
     * @param cc the crew cards to count
     */
    private void getCrewCardSummary(StringBuilder strBuild, List<CrewCard> cc) {
        int[] crew_stats = {0,0,0,0,0,0};

        for (CrewCard tempCrew : cc) {
            if (tempCrew.getCrewCardColour() == CrewCardColour.BLACK) {
                if (tempCrew.getCrewStrength() == 1) {
                    crew_stats[0] = crew_stats[0] + 1;
                } else if (tempCrew.getCrewStrength() == 2) {
                    crew_stats[2] = crew_stats[2] + 1;
                } else {
                    crew_stats[4] = crew_stats[4] + 1;
                }
            } else {
                if (tempCrew.getCrewStrength() == 1) {
                    crew_stats[1] = crew_stats[1] + 1;
                } else if (tempCrew.getCrewStrength() == 2) {
                    crew_stats[3] = crew_stats[3] + 1;
                } else {
                    crew_stats[5] = crew_stats[5] + 1;
                }
            }
        }

        strBuild.append("BLACK:\tRED:\n");

        for(int i = 0; i < 3; i++){
            strBuild.append(i + 1).append("x").append(crew_stats[i]).append("\t\t").append(i + 1).append("x").append(crew_stats[i+1]).append("\n");
        }
        strBuild.append("\n");
    }

    /**
     * remove the port card displayed
     */
    public void remove_display_port(){
        if(content_page.lookup("#port_group") != null){
            System.out.println("Port group found, removing it");
            content_page.getChildren().remove(content_page.lookup("#port_group"));
        }else{
            System.out.println("Port group not found");
        }
    }

    /**
     * display a player card, showing the details about the player's who's turn it is
     * @param player the player to display the info of
     */
    public void display_player(Player player){


        if(content_page.lookup("#player_group") != null){
            System.out.println("Player group found, creating new");
            content_page.getChildren().remove(content_page.lookup("#player_group"));
        }else{
            System.out.println("Player group not found, creating new");
        }

        Group player_group = display_(board_origin_X - 40, 590, board_origin_X + (21 * tile_size) + 10, 300);
        int player_index = player.getIndex();
        Label title = new Label(player.getName());
        title.setText(player.getName());
        title.getStyleClass().add("player_name_" + player_index);
        title.setLayoutX(board_origin_X + (21 * tile_size) + 30);
        title.setLayoutY(310);


        //player home port
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("Home port: ").append(player.getHomePort().getHarbourDetail().name()).append("\n\n");



        //player crew cards
        List<CrewCard> cc = player.getCrews();
        getCrewCardSummary(strBuild, cc);


        //player's treasure
        List<Treasure> player_treasure = player.getTreasures();

        strBuild.append("Treasure:\n");

        for (Treasure treasure : player_treasure) {
            strBuild.append(treasure.getTreasureDetail().name()).append("\n");
        }
        strBuild.append("\n");

        //chance cards
        if(player.hasChanceCard()){
            strBuild.append("Chance Card: ").append("\n");
            strBuild.append(player.getChanceCards().getChanceCardDetail().cardDescription);
        }

        Text content = new Text(strBuild.toString());
        content.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        content.setWrappingWidth((board_origin_X - 40) - 40);
        content.setLayoutX(board_origin_X + (21 * tile_size) + 30);
        content.setLayoutY(380);

        player_group.getChildren().add(title);
        player_group.setId("player_group");
        player_group.getChildren().add(content);

        content_page.getChildren().add(player_group);
    }

    /**
     * help function to prevent the duplicate of the code, it creates a group which contains the card
     * @param width card width
     * @param height card height
     * @param xPos card X position
     * @param yPos card Y position
     * @return the newly created group.
     */
    private Group display_(double width, double height, double xPos, double yPos){
        Rectangle shadow = new Rectangle((width) - 20, height, Color.GRAY);
        shadow.setLayoutY(yPos);
        shadow.setLayoutX(xPos);

        Rectangle page = new Rectangle((width) - 40, height-20, Color.BISQUE);
        page.setLayoutY(yPos + 10);
        page.setLayoutX(xPos + 10);

        Rectangle name_bg = new Rectangle((width) - 40, 50, Color.BLANCHEDALMOND);
        name_bg.setLayoutY(yPos + 10);
        name_bg.setLayoutX(xPos + 10);

        Group new_group = new Group();
        new_group.getChildren().add(shadow);
        new_group.getChildren().add(page);
        new_group.getChildren().add(name_bg);

        return  new_group;
    }
}


