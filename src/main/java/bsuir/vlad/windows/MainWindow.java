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
        workWithExhibitionsButton.setOnAction(workEvent -> {
            stage.close();
            new ExhibitionsWindow(stage);
        });

        Button workWithOwnersButton = new Button("Work with owners");
        workWithOwnersButton.setOnAction(workEvent -> {
            stage.close();
            new OwnersWindow(stage);
        });

        Button workWithArtistsButton = new Button("Work with artists");
        workWithArtistsButton.setOnAction(workAction -> {
            stage.close();
            new ArtistsWindow(stage);
        });

        Button workWithArtistWorksButton = new Button("Work with artist works");

        Button showListOfExhibitorsButton = new Button("Show exhibitors list of certain exhibition");
        showListOfExhibitorsButton.setOnAction(showListEvent -> {
            stage.close();
            new ChoosingExhibitionDialog(stage);
        });

        Button showExhibitionHallsList = new Button("Show list of all exhibition halls");
        Button showExhibitionsList = new Button("Show list of actual exhibitions");

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> Platform.exit());

        FlowPane pane = new FlowPane();
        pane.setOrientation(Orientation.VERTICAL);

        double indent = 10;
        pane.setPadding(new Insets(indent));
        pane.setVgap(indent);
        pane.setHgap(indent);

        double paneHeight = 330;
        pane.setPrefHeight(paneHeight);

        pane.getChildren().addAll(
                workWithExhibitionHallsButton,
                workWithExhibitionsButton,
                workWithOwnersButton,
                workWithArtistsButton,
                workWithArtistWorksButton,
                showListOfExhibitorsButton,
                showExhibitionHallsList,
                showExhibitionsList,
                exitButton
        );

        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.show();
    }
}
