package uk.ac.aber.cs221.gp12.game.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.List;

/**
 * A simple class which allows for  popups throughout the system.
 *
 * @author jah143
 * @version 1.1
 */
public class PopUps {
    /**
     * a string variable to allow the return from the popup
     */
    static String answer;
    /**
     * a integer variable to allow the return from a multiple choice popup
     */
    static int int_ans;

    /**
     * a multiple choice popup, requires a list of choices, and returns an index of the choice provided
     * @param Title the title used for the pop up window
     * @param Content the question/content of the pop up window
     * @param options the options available to the user
     * @return the integer index of the option from the list
     */
    public String makePopup(String Title, String Content, List<String> options) {

        //create a stage for the pop up
        Stage popup_stage = new Stage();
        popup_stage.initModality(Modality.APPLICATION_MODAL);


        Label content = new Label(Content);
        GridPane pane = new GridPane();
        content.setMaxWidth(250);

        Button test_button = new Button("Hello");
        System.out.println(test_button.getBoundsInLocal().getHeight());


        for(int i = 0; i < options.size(); i++){
            Button button = new Button(options.get(i));
            button.setId(String.valueOf(i));
            button.setOnAction(e -> {
                int_ans = Integer.parseInt(button.getId());
                popup_stage.close();
            });

            pane.add(button, 1,i + 1);
            //System.out.println("Button height: " + button.getBoundsInLocal().getHeight());
        }


        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        pane.add(content, 1, 0);

        Scene scene = new Scene(pane, 250, 50 + (30 * options.size()));
        popup_stage.setTitle(Title);
        popup_stage.setScene(scene);
        popup_stage.setResizable(false);
        popup_stage.showAndWait();
        return options.get(int_ans);
    }

    /**
     * a text field pop up, asks a question and allows the user for an open answer
     * @param Title the title for the pop up window
     * @param Content the question for the player
     * @return the string that the player has inputted
     */
    public String makePopup(String Title, String Content) {


        Stage popup_stage = new Stage();
        popup_stage.initModality(Modality.APPLICATION_MODAL);

        TextField answer_field = new TextField();

        Button submit_button = new Button("Submit");
        submit_button.setOnAction(e -> {
            answer = answer_field.getText();
            popup_stage.close();
        });

        Label content = new Label(Content);
        content.setMaxWidth(250);
        GridPane pane = new GridPane();

        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        pane.add(answer_field, 1, 1);
        pane.add(submit_button, 1, 2);
        pane.add(content, 1, 0);

        Scene scene = new Scene(pane, 250, 150);
        popup_stage.setTitle(Title);
        popup_stage.setScene(scene);
        popup_stage.setResizable(false);
        popup_stage.showAndWait();

        return answer;
    }

    /**
     * a message pop up that informs a player, but does not return anything
     * @param Content the message to display
     */
    public void makePopup(String Content){
        Stage popup_stage = setup_popup(Content);
        popup_stage.setTitle("Message");
        popup_stage.setResizable(false);
        popup_stage.showAndWait();
    }

    /**
     * creates a popup for the chance cards, which require a custom content and title, without the need to return a string
     * @param title the title of the popup
     * @param Content the Content of the popup
     */
    public void makePopupChanceCard(String title, String Content){
        Stage popup_stage = setup_popup(Content);
        popup_stage.setTitle(title);
        popup_stage.setResizable(false);
        popup_stage.showAndWait();
    }

    /**
     * setup the pop up. used to prevent duplication of code
     * @param Content the content of the pop up to display
     * @return the Stage that can be further customised before displaying
     */
    private Stage setup_popup(String Content){
        Stage popup_stage = new Stage();
        popup_stage.initModality(Modality.APPLICATION_MODAL);

        Button submit_button = new Button("OK");
        submit_button.setOnAction(e -> popup_stage.close());

        Label content = new Label(Content);
        content.setWrapText(true);
        content.setMaxWidth(250);
        GridPane pane = new GridPane();

        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        pane.add(submit_button, 1, 1);
        pane.add(content, 1, 0);
        return popup_stage;
    }
}
