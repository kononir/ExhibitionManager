package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.model.Exhibition;
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

class ExhibitionsWindow {
    private Exchanger<Exhibition> exchanger = new Exchanger<>();
    private ScheduledExecutorService executorService;

    ExhibitionsWindow(Stage stage) {
        DatabaseController databaseController = new DatabaseController();

        databaseController.controlSelectingExhibitions(exchanger);

        TableView<Exhibition> tableView = new TableView<>();

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

        TableColumn<Exhibition, String> typeCol = new TableColumn<>("Type");
        TableColumn<Exhibition, String> nameCol = new TableColumn<>("Name");
        TableColumn<Exhibition, LocalDate> dateCol = new TableColumn<>("Date");
        TableColumn<Exhibition, String> exhibitionHallNameCol = new TableColumn<>("Exhibition Hall Name");

        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        exhibitionHallNameCol.setCellValueFactory(new PropertyValueFactory<>("exhibitionHallName"));

        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        exhibitionHallNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        typeCol.setOnEditCommit(editEvent -> {
            TablePosition<Exhibition, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Exhibition exhibition = tableView.getItems().get(rowIndex);

            String updatingRecordName = exhibition.getName();

            exhibition.setType(newValue);

            databaseController.controlUpdatingExhibition(
                    "type", updatingRecordName, exhibition
            );
        });
        nameCol.setOnEditCommit(editEvent -> {
            TablePosition<Exhibition, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Exhibition exhibition = tableView.getItems().get(rowIndex);

            String updatingRecordName = exhibition.getName();

            exhibition.setName(newValue);

            databaseController.controlUpdatingExhibition(
                    "name", updatingRecordName, exhibition
            );
        });
        dateCol.setOnEditCommit(editEvent -> {
            TablePosition<Exhibition, LocalDate> pos = editEvent.getTablePosition();

            LocalDate newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Exhibition exhibition = tableView.getItems().get(rowIndex);

            String updatingRecordName = exhibition.getName();

            exhibition.setDate(newValue);

            databaseController.controlUpdatingExhibition(
                    "date", updatingRecordName, exhibition
            );
        });
        exhibitionHallNameCol.setOnEditCommit(editEvent -> {
            TablePosition<Exhibition, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Exhibition exhibition = tableView.getItems().get(rowIndex);

            String updatingRecordName = exhibition.getName();

            exhibition.setExhibitionHallName(newValue);

            databaseController.controlUpdatingExhibition(
                    "exhibitionHallName", updatingRecordName, exhibition
            );
        });

        tableView.setItems(FXCollections.observableArrayList());

        tableView.getColumns().addAll(nameCol, typeCol, dateCol, exhibitionHallNameCol);

        for (Object o : tableView.getColumns()) {
            TableColumn tc = (TableColumn) o;
            double minWidth = 150;
            tc.setMinWidth(minWidth);
        }

        Button toolAddButton = new Button("Add");
        toolAddButton.setGraphic(new ImageView(ClassLoader.getSystemResource("add.png").toString()));
        toolAddButton.setOnAction(addEvent -> new AddingExhibitionDialog(tableView.getItems()));

        Button toolDeleteButton = new Button("Delete");
        toolDeleteButton.setGraphic(new ImageView(ClassLoader.getSystemResource("delete.png").toString()));
        toolDeleteButton.setOnAction(deleteEvent -> {
            SelectionModel<Exhibition> selectionModel = tableView.getSelectionModel();

            Exhibition exhibition = selectionModel.getSelectedItem();

            String deletingRecordName = exhibition.getName();

            tableView.getItems().remove(exhibition);

            databaseController.controlDeletingExhibition(deletingRecordName);
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
            Exhibition exhibition = exchanger.exchange(null);

            boolean outOfRecords = (exhibition == null);

            if (outOfRecords) {
                shutdownUpdating();
            } else {
                tableView.getItems().add(exhibition);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
