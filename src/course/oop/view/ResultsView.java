package course.oop.view;

import course.oop.controller.Controller;
import course.oop.model.Game;
import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

// Results view contains elements when the game is over
public class ResultsView extends TurnView {
    //resutls view constructor
    public ResultsView(Game g) {

        // since results view build based on turn view,
        // set the elements of results view inherited from turn view
        super(g, 0, new int[]{0});

        // Set winner message and display on the top of tic-tac-toe board
        int winnerId = g.determineWinner();
        String winner = "Nobody";
        switch (winnerId) {
            case 1:
            case 2:
                winner = String.format("Team %d", winnerId);
                break;
            case 3:
                winner = "Nobody";
                break;
        }

        // display winner message
        Label winnerMessage = new Label();
        winnerMessage.setText(String.format("%s is the winner! Congratulations!", winner));
        winnerMessage.setAlignment(Pos.TOP_CENTER);
        winnerMessage.setTextAlignment(TextAlignment.CENTER);
        winnerMessage.setPadding(new Insets(0,0,500,0));

        // display game status
        this.status.setText("Game is over!");

        // get the turn view root as a BoarderPane
        BorderPane root = (BorderPane) this.root;

        // Create menu for rematch or main menu or return to game setup
        VBox menu = new VBox();
        menu.setSpacing(20.0);
        menu.setAlignment(Pos.CENTER_RIGHT);
        menu.setPadding(new Insets(5));
        Button rematch = new Button("rematch");
        Button mainmenu = new Button("main menu");
        Button setup = new Button("return to game setup");
        rematch.setOnAction(e -> Controller.execute("rematch"));
        mainmenu.setOnAction(e -> Controller.execute("mainmenu"));
        setup.setOnAction(e -> Controller.execute("setup"));
        menu.getChildren().addAll(rematch, setup, mainmenu);

        // Switch root to Stack Pane
        StackPane newRoot = new StackPane();
        newRoot.getChildren().addAll(this.root, menu, winnerMessage);
        this.root = newRoot;

        // tic-tac-toe board rotation when the game is over
        RotateTransition rt = new RotateTransition();
        rt.setDuration(Duration.millis(10));
        rt.setNode(root.getCenter());
        rt.setByAngle(360);
        rt.setCycleCount(50);
        rt.play();
    }

    @Override
    // get root of the results view
    public Parent getRoot() {
        return root;
    }

}
