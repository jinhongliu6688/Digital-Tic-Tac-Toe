package course.oop.view;

import course.oop.controller.Controller;
import course.oop.model.Game;
import course.oop.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

//Turn view contains the elements during the game
public class TurnView implements TTTView {

    //Declaring elements for turn view
    Parent root;
    final Label status;
    Scene scene;
    private final int n = 100;

    //elements for time left display
    private Label clock;
    private long timeleft;


    // turn view constructor
    public TurnView(Game game, int team, int[] player) {
        // create a BoarderPane
        BorderPane root = new BorderPane();
        // get a UI element for the game board
        Parent center = game.getDisplay();
        // create a label to display effect
        Label effect = new Label(game.getStatus());
        // create buttons container
        VBox buttons = new VBox();

        // setting elements positions
        root.setLeft(buttons);
        root.setCenter(center);

        // If player1/player2 is Computer, automate tile selection
        Player p = game.getPlayer(team, player[team]);
        if (p.isComputer()) {
            Timeline cpu = new Timeline(new KeyFrame(Duration.seconds(1.0), event -> {
            }));
            cpu.setOnFinished(e -> Controller.execute("select " + game.selectRandomTile()));
            cpu.setCycleCount(1);
            cpu.play();
        }

        // display time left during the game
        GridPane bottom = new GridPane();
        root.setBottom(bottom);
        timeleft = game.getConfig().getTimeout();
        if (game.getConfig().getTimeout() > 0) {
            clock = new Label();
            bottom.add(clock, 0, 0);
            clock.setText(String.format("%d", timeleft));
            Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1.0), event -> clock.setText(String.format("%d", --timeleft))));
            timer.setCycleCount((int) game.getConfig().getTimeout());
            timer.setOnFinished(event -> {
                Controller.interruptTurn();
                Controller.interruptTurn();
            });
            timer.play();
        }

        // rotate the tic-tac-toe board clockwise or counterclockwise
        System.out.println(center.getRotationAxis());
        Button rotateCCW = new Button("Rotate CCW");
        rotateCCW.setOnMouseClicked(e -> Controller.execute("rotate ccw"));
        Button rotateCW = new Button("Rotate CW");
        rotateCW.setOnMouseClicked(e -> Controller.execute("rotate cw"));
        buttons.getChildren().addAll(rotateCCW, rotateCW);

        // display turn status
        status = new Label(String.format("It's %s's turn!", game.getPlayer(team, player[team])));
        bottom.add(status, 1, 0);

        // button for game forfeit
        Button forfeit = new Button("Forfeit");
        forfeit.setOnAction(e -> Controller.execute("forfeit"));
        bottom.add(forfeit, 10, 0);
        bottom.setAlignment(Pos.TOP_CENTER);
        status.setFont(Font.font(20));
        buttons.getChildren().add(forfeit);
        effect.setAlignment(Pos.CENTER);
        root.setTop(effect);

        this.root = root;
    }


    @Override
    // get root of the turn view
    public Parent getRoot() {
        return this.root;
    }
}
