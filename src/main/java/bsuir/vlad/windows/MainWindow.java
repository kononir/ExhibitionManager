package bsuir.vlad.windows;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MainWindow {
    public MainWindow(Stage stage) {
        Button workWithExhibitionHallsButton = new Button("Work with exhibition halls");
        workWithExhibitionHallsButton.setOnAction(workEvent -> {
            stage.close();
            new ExhibitionHallsWindow(stage);
        });

        Button workWithExhibitionsButton = new Button("Work with exhibitions");
        Button workWithOwnersButton = new Button("Work with owners");
        Button workWithArtistsButton = new Button("Work with artists");

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> Platform.exit());

        FlowPane pane = new FlowPane();
        pane.setOrientation(Orientation.VERTICAL);

        double indent = 10;
        pane.setPadding(new Insets(indent));
        pane.setVgap(indent);
        pane.setHgap(indent);

        double paneHeight = 185;
        pane.setPrefHeight(paneHeight);

        pane.getChildren().addAll(
                workWithExhibitionHallsButton,
                workWithExhibitionsButton,
                workWithOwnersButton,
                workWithArtistsButton,
                exitButton
        );

        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.show();
    }
}
