package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.model.ExhibitionHall;
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

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ExhibitionHallsWindow {
    private Exchanger<ExhibitionHall> exchanger = new Exchanger<>();
    private ScheduledExecutorService executorService;

    ExhibitionHallsWindow(Stage stage) {
        DatabaseController databaseController = new DatabaseController();

        databaseController.controlSelectingExhibitionHalls(exchanger);

        TableView<ExhibitionHall> tableView = new TableView<>();

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

        TableColumn<ExhibitionHall, String> nameCol = new TableColumn<>("Name");
        TableColumn<ExhibitionHall, Double> squareCol = new TableColumn<>("Square");

        TableColumn<ExhibitionHall, String> addressCol = new TableColumn<>("Address");
        TableColumn<ExhibitionHall, String> streetSubCol = new TableColumn<>("Street");
        TableColumn<ExhibitionHall, String> buildingNumberSubCol = new TableColumn<>("Building Number");
        addressCol.getColumns().addAll(streetSubCol, buildingNumberSubCol);

        TableColumn<ExhibitionHall, String> phoneNumberCol = new TableColumn<>("Phone Number");
        TableColumn<ExhibitionHall, String> ownerNameCol = new TableColumn<>("Owner Name");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        squareCol.setCellValueFactory(new PropertyValueFactory<>("square"));
        streetSubCol.setCellValueFactory(new PropertyValueFactory<>("addressStreet"));
        buildingNumberSubCol.setCellValueFactory(new PropertyValueFactory<>("addressBuildingNumber"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        ownerNameCol.setCellValueFactory(new PropertyValueFactory<>("ownerName"));

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        squareCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        streetSubCol.setCellFactory(TextFieldTableCell.forTableColumn());
        buildingNumberSubCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        ownerNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nameCol.setOnEditCommit(editNameColEvent -> {
            TablePosition<ExhibitionHall, String> pos = editNameColEvent.getTablePosition();

            String newValue = editNameColEvent.getNewValue();

            int rowIndex = pos.getRow();
            ExhibitionHall exhibitionHall = tableView.getItems().get(rowIndex);

            exhibitionHall.setName(newValue);

            int updatingRecordIndex = editNameColEvent.getTablePosition().getRow() + 1;
            databaseController.controlUpdatingExhibitionHall(
                    "name", updatingRecordIndex, exhibitionHall
            );
        });
        squareCol.setOnEditCommit(editNameColEvent -> {
            TablePosition<ExhibitionHall, Double> pos = editNameColEvent.getTablePosition();

            double newValue = editNameColEvent.getNewValue();

            int rowIndex = pos.getRow();
            ExhibitionHall exhibitionHall = tableView.getItems().get(rowIndex);

            exhibitionHall.setSquare(newValue);

            int updatingRecordIndex = editNameColEvent.getTablePosition().getRow() + 1;
            databaseController.controlUpdatingExhibitionHall(
                    "square", updatingRecordIndex, exhibitionHall
            );
        });
        streetSubCol.setOnEditCommit(editNameColEvent -> {
            TablePosition<ExhibitionHall, String> pos = editNameColEvent.getTablePosition();

            String newValue = editNameColEvent.getNewValue();

            int rowIndex = pos.getRow();
            ExhibitionHall exhibitionHall = tableView.getItems().get(rowIndex);

            exhibitionHall.setAddressStreet(newValue);

            int updatingRecordIndex = editNameColEvent.getTablePosition().getRow() + 1;
            databaseController.controlUpdatingExhibitionHall(
                    "street", updatingRecordIndex, exhibitionHall
            );
        });
        buildingNumberSubCol.setOnEditCommit(editNameColEvent -> {
            TablePosition<ExhibitionHall, String> pos = editNameColEvent.getTablePosition();

            String newValue = editNameColEvent.getNewValue();

            int rowIndex = pos.getRow();
            ExhibitionHall exhibitionHall = tableView.getItems().get(rowIndex);

            exhibitionHall.setAddressBuildingNumber(newValue);

            int updatingRecordIndex = editNameColEvent.getTablePosition().getRow() + 1;
            databaseController.controlUpdatingExhibitionHall(
                    "buildingNumber", updatingRecordIndex, exhibitionHall
            );
        });
        phoneNumberCol.setOnEditCommit(editNameColEvent -> {
            TablePosition<ExhibitionHall, String> pos = editNameColEvent.getTablePosition();

            String newValue = editNameColEvent.getNewValue();

            int rowIndex = pos.getRow();
            ExhibitionHall exhibitionHall = tableView.getItems().get(rowIndex);

            exhibitionHall.setPhoneNumber(newValue);

            int updatingRecordIndex = editNameColEvent.getTablePosition().getRow() + 1;
            databaseController.controlUpdatingExhibitionHall(
                    "phoneNumber", updatingRecordIndex, exhibitionHall
            );
        });
        ownerNameCol.setOnEditCommit(editNameColEvent -> {
            TablePosition<ExhibitionHall, String> pos = editNameColEvent.getTablePosition();

            String newValue = editNameColEvent.getNewValue();

            int rowIndex = pos.getRow();
            ExhibitionHall exhibitionHall = tableView.getItems().get(rowIndex);

            exhibitionHall.setOwnerName(newValue);

            int updatingRecordIndex = editNameColEvent.getTablePosition().getRow() + 1;
            databaseController.controlUpdatingExhibitionHall(
                    "ownerName", updatingRecordIndex, exhibitionHall
            );
        });

        tableView.setItems(FXCollections.observableArrayList());

        tableView.getColumns().addAll(nameCol, squareCol, addressCol, phoneNumberCol, ownerNameCol);

        for (Object o : tableView.getColumns()) {
            TableColumn tc = (TableColumn) o;
            double minWidth = 150;
            tc.setMinWidth(minWidth);
        }

        for (TableColumn tc : addressCol.getColumns()) {
            double minWidth = 150;
            tc.setMinWidth(minWidth);
        }

        Button toolAddButton = new Button("Add");
        toolAddButton.setGraphic(new ImageView(ClassLoader.getSystemResource("add.png").toString()));
        toolAddButton.setOnAction(addEvent -> new AddingExhibitionHallDialog(tableView.getItems()));

        Button toolDeleteButton = new Button("Delete");
        toolDeleteButton.setGraphic(new ImageView(ClassLoader.getSystemResource("delete.png").toString()));
        toolDeleteButton.setOnAction(deleteEvent -> {
            ExhibitionHall exhibitionHall = tableView.getSelectionModel().getSelectedItem();

            tableView.getItems().remove(exhibitionHall);

            databaseController.controlDeletingExhibitionHall(exhibitionHall);
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
            ExhibitionHall exhibitionHall = exchanger.exchange(null);

            boolean outOfRecords = (exhibitionHall == null);

            if (outOfRecords) {
                shutdownUpdating();
            } else {
                tableView.getItems().add(exhibitionHall);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
