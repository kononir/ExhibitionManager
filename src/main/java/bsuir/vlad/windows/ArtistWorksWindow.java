package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.model.ArtistWork;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ArtistWorksWindow {
    private Exchanger<ArtistWork> exchanger = new Exchanger<>();
    private ScheduledExecutorService executorService;

    ArtistWorksWindow(Stage stage) {
        DatabaseController databaseController = new DatabaseController();

        databaseController.controlSelectingArtistWorks(exchanger);

        TableView<ArtistWork> tableView = new TableView<>();

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

        TableColumn<ArtistWork, String> nameCol = new TableColumn<>("Name");
        TableColumn<ArtistWork, String> typeCol = new TableColumn<>("Type");

        TableColumn<ArtistWork, LocalDate> creationDateCol = new TableColumn<>("Creation Date");

        TableColumn<ArtistWork, Double> sizesCol = new TableColumn<>("Sizes");
        TableColumn<ArtistWork, Double> widthSubCol = new TableColumn<>("Width");
        TableColumn<ArtistWork, Double> heightSubCol = new TableColumn<>("Height");
        TableColumn<ArtistWork, Double> volumeSubCol = new TableColumn<>("Volume");
        sizesCol.getColumns().addAll(widthSubCol, heightSubCol, volumeSubCol);

        TableColumn<ArtistWork, String> artistLastNameCol = new TableColumn<>("Artist Last name");
        TableColumn<ArtistWork, String> exhibitionNameCol = new TableColumn<>("Exhibition Name");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        creationDateCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        widthSubCol.setCellValueFactory(new PropertyValueFactory<>("width"));
        heightSubCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        volumeSubCol.setCellValueFactory(new PropertyValueFactory<>("volume"));
        artistLastNameCol.setCellValueFactory(new PropertyValueFactory<>("artistLastName"));
        exhibitionNameCol.setCellValueFactory(new PropertyValueFactory<>("exhibitionName"));

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        creationDateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        widthSubCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        heightSubCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        volumeSubCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        artistLastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        exhibitionNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nameCol.setOnEditCommit(editEvent -> {
            TablePosition<ArtistWork, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            ArtistWork artistWork = tableView.getItems().get(rowIndex);

            String updatingRecordName = artistWork.getName();

            artistWork.setName(newValue);

            databaseController.controlUpdatingArtistWork(
                    "name", updatingRecordName, artistWork
            );
        });
        typeCol.setOnEditCommit(editEvent -> {
            TablePosition<ArtistWork, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            ArtistWork artistWork = tableView.getItems().get(rowIndex);

            String updatingRecordName = artistWork.getName();

            artistWork.setType(newValue);

            databaseController.controlUpdatingArtistWork(
                    "type", updatingRecordName, artistWork
            );
        });
        creationDateCol.setOnEditCommit(editEvent -> {
            TablePosition<ArtistWork, LocalDate> pos = editEvent.getTablePosition();

            LocalDate newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            ArtistWork artistWork = tableView.getItems().get(rowIndex);

            String updatingRecordName = artistWork.getName();

            artistWork.setCreationDate(newValue);

            databaseController.controlUpdatingArtistWork(
                    "creationDate", updatingRecordName, artistWork
            );
        });
        widthSubCol.setOnEditCommit(editEvent -> {
            TablePosition<ArtistWork, Double> pos = editEvent.getTablePosition();

            double newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            ArtistWork artistWork = tableView.getItems().get(rowIndex);

            String updatingRecordName = artistWork.getName();

            artistWork.setWidth(newValue);

            databaseController.controlUpdatingArtistWork(
                    "width", updatingRecordName, artistWork
            );
        });
        heightSubCol.setOnEditCommit(editEvent -> {
            TablePosition<ArtistWork, Double> pos = editEvent.getTablePosition();

            double newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            ArtistWork artistWork = tableView.getItems().get(rowIndex);

            String updatingRecordName = artistWork.getName();

            artistWork.setHeight(newValue);

            databaseController.controlUpdatingArtistWork(
                    "height", updatingRecordName, artistWork
            );
        });
        volumeSubCol.setOnEditCommit(editEvent -> {
            TablePosition<ArtistWork, Double> pos = editEvent.getTablePosition();

            double newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            ArtistWork artistWork = tableView.getItems().get(rowIndex);

            String updatingRecordName = artistWork.getName();

            artistWork.setVolume(newValue);

            databaseController.controlUpdatingArtistWork(
                    "volume", updatingRecordName, artistWork
            );
        });
        artistLastNameCol.setOnEditCommit(editEvent -> {
            TablePosition<ArtistWork, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            ArtistWork artistWork = tableView.getItems().get(rowIndex);

            String updatingRecordName = artistWork.getName();

            artistWork.setArtistLastName(newValue);

            databaseController.controlUpdatingArtistWork(
                    "artistLastName", updatingRecordName, artistWork
            );
        });
        exhibitionNameCol.setOnEditCommit(editEvent -> {
            TablePosition<ArtistWork, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            ArtistWork artistWork = tableView.getItems().get(rowIndex);

            String updatingRecordName = artistWork.getName();

            artistWork.setExhibitionName(newValue);

            databaseController.controlUpdatingArtistWork(
                    "exhibitionName", updatingRecordName, artistWork
            );
        });

        tableView.setItems(FXCollections.observableArrayList());

        tableView.getColumns().addAll(nameCol, typeCol, creationDateCol, sizesCol, artistLastNameCol, exhibitionNameCol);

        for (Object o : tableView.getColumns()) {
            TableColumn tc = (TableColumn) o;
            double minWidth = 150;
            tc.setMinWidth(minWidth);
        }

        for (TableColumn tc : sizesCol.getColumns()) {
            double minWidth = 150;
            tc.setMinWidth(minWidth);
        }

        Button toolAddButton = new Button("Add");
        toolAddButton.setGraphic(new ImageView(ClassLoader.getSystemResource("add.png").toString()));
        toolAddButton.setOnAction(addEvent -> new AddingArtistWorkDialog(tableView.getItems()));

        Button toolDeleteButton = new Button("Delete");
        toolDeleteButton.setGraphic(new ImageView(ClassLoader.getSystemResource("delete.png").toString()));
        toolDeleteButton.setOnAction(deleteEvent -> {
            SelectionModel<ArtistWork> selectionModel = tableView.getSelectionModel();

            ArtistWork artistWork = selectionModel.getSelectedItem();

            String removableRecordName = artistWork.getName();

            tableView.getItems().remove(artistWork);

            databaseController.controlDeletingArtistWork(removableRecordName);
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
            ArtistWork artistWork = exchanger.exchange(null);

            boolean outOfRecords = (artistWork == null);

            if (outOfRecords) {
                shutdownUpdating();
            } else {
                tableView.getItems().add(artistWork);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
