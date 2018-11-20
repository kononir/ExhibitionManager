package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.model.Exhibition;
import bsuir.vlad.usingintable.ArtistWorkInformation;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class CurrentExhibitionWindow {
    private Exchanger<ArtistWorkInformation> exchanger = new Exchanger<>();
    private ScheduledExecutorService executorService;

    CurrentExhibitionWindow(Exhibition exhibition, Stage stage) {
        DatabaseController databaseController = new DatabaseController();

        databaseController.controlFindingArtistWorkInformation(exhibition.getName(), exchanger);

        Label exhibitionNameLabel = new Label(exhibition.getName());
        Label exhibitionDateLabel = new Label(exhibition.getDate().toString());

        VBox exhibitionHeaderLabel = new VBox(exhibitionNameLabel, exhibitionDateLabel);

        double headerTop = 10;
        StackPane.setAlignment(exhibitionHeaderLabel, Pos.TOP_CENTER);
        StackPane.setMargin(exhibitionHeaderLabel, new Insets(headerTop));

        TableView<ArtistWorkInformation> tableView = new TableView<>();

        startUpdating(tableView);

        double tableViewRightLeft = 0;
        double tableViewTopBottom = 50;
        StackPane.setAlignment(tableView, Pos.CENTER);
        StackPane.setMargin(tableView, new Insets(
                tableViewTopBottom,
                tableViewRightLeft,
                tableViewTopBottom,
                tableViewRightLeft
        ));

        TableColumn<ArtistWorkInformation, String> artistWorkNameCol = new TableColumn<>("Artist work Name");
        TableColumn<ArtistWorkInformation, String> artistWorkTypeCol = new TableColumn<>("Artist work Type");

        TableColumn<ArtistWorkInformation, String> artistFirstNameSubCol = new TableColumn<>("First name");
        TableColumn<ArtistWorkInformation, String> artistLastNameSubCol = new TableColumn<>("Last name");
        TableColumn<ArtistWorkInformation, String> artistPatronymicSubCol = new TableColumn<>("Patronymic");
        TableColumn<ArtistWorkInformation, String> artistNameCol = new TableColumn<>("Artist Name");
        artistNameCol.getColumns().addAll(artistFirstNameSubCol, artistLastNameSubCol, artistPatronymicSubCol);

        TableColumn<ArtistWorkInformation, Integer> artistAgeCol = new TableColumn<>("Artist Age");
        TableColumn<ArtistWorkInformation, LocalDate> artistWorkCreationDateCol = new TableColumn<>("Artist work Creation date");

        artistWorkNameCol.setCellValueFactory(new PropertyValueFactory<>("artistWorkName"));
        artistWorkTypeCol.setCellValueFactory(new PropertyValueFactory<>("artistWorkType"));
        artistFirstNameSubCol.setCellValueFactory(new PropertyValueFactory<>("artistFirstName"));
        artistLastNameSubCol.setCellValueFactory(new PropertyValueFactory<>("artistLastName"));
        artistPatronymicSubCol.setCellValueFactory(new PropertyValueFactory<>("artistPatronymic"));
        artistAgeCol.setCellValueFactory(new PropertyValueFactory<>("artistAge"));
        artistWorkCreationDateCol.setCellValueFactory(new PropertyValueFactory<>("artistWorkCreationDate"));

        tableView.setItems(FXCollections.observableArrayList());

        tableView.getColumns().addAll(
                artistWorkNameCol,
                artistWorkTypeCol,
                artistNameCol,
                artistAgeCol,
                artistWorkCreationDateCol
        );

        for (Object o : tableView.getColumns()) {
            TableColumn tc = (TableColumn) o;
            double minWidth = 150;
            tc.setMinWidth(minWidth);
        }

        for (TableColumn tc : artistNameCol.getColumns()) {
            double minWidth = 150;
            tc.setMinWidth(minWidth);
        }

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelEvent -> {
            if (!executorService.isShutdown()) {
                shutdownUpdating();
            }

            stage.close();
            new MainWindow(stage);
        });

        double cancelButtonBottom = 10;
        StackPane.setAlignment(cancelButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(cancelButton, new Insets(cancelButtonBottom));

        StackPane root = new StackPane(exhibitionHeaderLabel, tableView, cancelButton);

        Scene scene = new Scene(root);

        stage.setOnCloseRequest(closeAction -> {
            if (!executorService.isShutdown()) {
                shutdownUpdating();
            }

            stage.close();
        });

        stage.setScene(scene);
        stage.show();
    }

    private void startUpdating(TableView tableView) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> updateTable(tableView), 0, 10, TimeUnit.MILLISECONDS);
    }

    private void shutdownUpdating() {
        executorService.shutdown();
    }

    private void updateTable(TableView tableView) {
        try {
            ArtistWorkInformation information = exchanger.exchange(null);

            boolean outOfRecords = (information == null);

            if (outOfRecords) {
                shutdownUpdating();
            } else {
                tableView.getItems().add(information);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
