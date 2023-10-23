package course.oop.view;

import course.oop.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class MainView implements TTTView {
    // declaring root for the user interface
    private final Parent root;

    public MainView() {
        // create root
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // Title page
        // Title of the game
        Label title = new Label("Tic-tac-toe: The Game!");
        // Title color
        title.setTextFill(Color.web("#0076a3"));
        // Title font size
        title.setFont(Font.font(50));

        // Main Menu
        // start button
        Button start = new Button("Start");
        // start button color
        start.setTextFill(Color.web("#0076a3"));
        // quit button
        Button quit = new Button("Quit");
        // quit button color
        quit.setTextFill(Color.web("red"));
        // store button
        Button store = new Button("Shop");
        // store button color
        store.setTextFill(Color.web("#0076a3"));

        // Actions
        // start the game
        start.setOnAction(e -> onStart());
        // quit the game
        quit.setOnAction(e -> Controller.execute("quit"));
        // open store
        store.setOnAction(e -> Controller.execute("store"));

        // Add elements to the root
        root.getChildren().addAll(title, start, store, quit);
        this.root = root;
    }

    @Override
    // get the root
    public Parent getRoot() {
        return root;
    }

    // start the game
    private void onStart() {
        Controller.execute("setup");
    }
}
