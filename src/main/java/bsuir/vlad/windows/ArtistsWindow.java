package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.model.Artist;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ArtistsWindow {
    private Exchanger<Artist> exchanger = new Exchanger<>();
    private ScheduledExecutorService executorService;

    ArtistsWindow(Stage stage) {
        DatabaseController databaseController = new DatabaseController();

        databaseController.controlSelectingArtists(exchanger);

        TableView<Artist> tableView = new TableView<>();

        startUpdating(tableView);

        tableView.setEditable(true);

        double tableViewRightLeft = 0;
        double tableViewTopBottom = 50;
        StackPane.setAlignment(tableView, Pos.CENTER);
        StackPane.setMargin(tableView, new Insets(
                tableViewTopBottom,
                tableViewRightLeft,
                tableViewTopBottom,
                tableViewRightLeft
        ));

        TableColumn<Artist, String> artistFirstNameSubCol = new TableColumn<>("First name");
        TableColumn<Artist, String> artistLastNameSubCol = new TableColumn<>("Last name");
        TableColumn<Artist, String> artistPatronymicSubCol = new TableColumn<>("Patronymic");
        TableColumn<Artist, String> artistNameCol = new TableColumn<>("Artist Name");
        artistNameCol.getColumns().addAll(artistFirstNameSubCol, artistLastNameSubCol, artistPatronymicSubCol);

        TableColumn<Artist, String> birthdayPlaceCol = new TableColumn<>("Birthday Place");
        TableColumn<Artist, LocalDate> birthdayDateCol = new TableColumn<>("Birthday Date");
        TableColumn<Artist, String> vitaeCol = new TableColumn<>("Vitae");
        TableColumn<Artist, String> educationCol = new TableColumn<>("Education");

        artistFirstNameSubCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        artistLastNameSubCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        artistPatronymicSubCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        birthdayPlaceCol.setCellValueFactory(new PropertyValueFactory<>("birthdayPlace"));
        birthdayDateCol.setCellValueFactory(new PropertyValueFactory<>("birthdayDate"));
        vitaeCol.setCellValueFactory(new PropertyValueFactory<>("vitae"));
        educationCol.setCellValueFactory(new PropertyValueFactory<>("education"));

        artistFirstNameSubCol.setCellFactory(TextFieldTableCell.forTableColumn());
        artistLastNameSubCol.setCellFactory(TextFieldTableCell.forTableColumn());
        artistPatronymicSubCol.setCellFactory(TextFieldTableCell.forTableColumn());
        birthdayPlaceCol.setCellFactory(TextFieldTableCell.forTableColumn());
        birthdayDateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        vitaeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        educationCol.setCellFactory(TextFieldTableCell.forTableColumn());

        artistFirstNameSubCol.setOnEditCommit(editEvent -> {
            TablePosition<Artist, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Artist artist = tableView.getItems().get(rowIndex);

            String updatingRecordName = artist.getLastName();

            artist.setFirstName(newValue);

            databaseController.controlUpdatingArtist(
                    "firstName", updatingRecordName, artist
            );
        });
        artistLastNameSubCol.setOnEditCommit(editEvent -> {
            TablePosition<Artist, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Artist artist = tableView.getItems().get(rowIndex);

            String updatingRecordName = artist.getLastName();

            artist.setLastName(newValue);

            databaseController.controlUpdatingArtist(
                    "lastName", updatingRecordName, artist
            );
        });
        artistPatronymicSubCol.setOnEditCommit(editEvent -> {
            TablePosition<Artist, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Artist artist = tableView.getItems().get(rowIndex);

            String updatingRecordName = artist.getLastName();

            artist.setPatronymic(newValue);

            databaseController.controlUpdatingArtist(
                    "patronymic", updatingRecordName, artist
            );
        });
        birthdayPlaceCol.setOnEditCommit(editEvent -> {
            TablePosition<Artist, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Artist artist = tableView.getItems().get(rowIndex);

            String updatingRecordName = artist.getLastName();

            artist.setBirthdayPlace(newValue);

            databaseController.controlUpdatingArtist(
                    "birthdayPlace", updatingRecordName, artist
            );
        });
        birthdayDateCol.setOnEditCommit(editEvent -> {
            TablePosition<Artist, LocalDate> pos = editEvent.getTablePosition();

            LocalDate newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Artist artist = tableView.getItems().get(rowIndex);

            String updatingRecordName = artist.getLastName();

            artist.setBirthdayDate(newValue);

            databaseController.controlUpdatingArtist(
                    "birthdayDate", updatingRecordName, artist
            );
        });
        vitaeCol.setOnEditCommit(editEvent -> {
            TablePosition<Artist, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Artist artist = tableView.getItems().get(rowIndex);

            String updatingRecordName = artist.getLastName();

            artist.setVitae(newValue);

            databaseController.controlUpdatingArtist(
                    "vitae", updatingRecordName, artist
            );
        });
        educationCol.setOnEditCommit(editEvent -> {
            TablePosition<Artist, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Artist artist = tableView.getItems().get(rowIndex);

            String updatingRecordName = artist.getLastName();

            artist.setEducation(newValue);

            databaseController.controlUpdatingArtist(
                    "education", updatingRecordName, artist
            );
        });

        tableView.setItems(FXCollections.observableArrayList());

        tableView.getColumns().addAll(artistNameCol, birthdayPlaceCol, birthdayDateCol, vitaeCol, educationCol);

        for (Object o : tableView.getColumns()) {
            TableColumn tc = (TableColumn) o;
            double minWidth = 150;
            tc.setMinWidth(minWidth);
        }

        Button toolAddButton = new Button("Add");
        toolAddButton.setGraphic(new ImageView(ClassLoader.getSystemResource("add.png").toString()));
        toolAddButton.setOnAction(addEvent -> new AddingArtistDialog(tableView.getItems()));

        Button toolDeleteButton = new Button("Delete");
        toolDeleteButton.setGraphic(new ImageView(ClassLoader.getSystemResource("delete.png").toString()));
        toolDeleteButton.setOnAction(deleteEvent -> {
            SelectionModel<Artist> selectionModel = tableView.getSelectionModel();

            Artist artist = selectionModel.getSelectedItem();

            String deletingRecordName = artist.getLastName();

            tableView.getItems().remove(artist);

            databaseController.controlDeletingArtist(deletingRecordName);
        });

        ToolBar toolBar = new ToolBar(
                toolAddButton,
                toolDeleteButton,
                new Separator()
        );

        double toolBarInsets = 5;
        StackPane.setMargin(toolBar, new Insets(toolBarInsets));
        StackPane.setAlignment(toolBar, Pos.TOP_CENTER);

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

        StackPane root = new StackPane(toolBar, tableView, cancelButton);

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
            Artist artist = exchanger.exchange(null);

            boolean outOfRecords = (artist == null);

            if (outOfRecords) {
                shutdownUpdating();
            } else {
                tableView.getItems().add(artist);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
