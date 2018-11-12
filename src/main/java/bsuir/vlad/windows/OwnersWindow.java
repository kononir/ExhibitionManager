package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.model.Owner;
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

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class OwnersWindow {
    private Exchanger<Owner> exchanger = new Exchanger<>();
    private ScheduledExecutorService executorService;

    OwnersWindow(Stage stage) {
        DatabaseController databaseController = new DatabaseController();

        databaseController.controlSelectingOwners(exchanger);

        TableView<Owner> tableView = new TableView<>();

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

        TableColumn<Owner, String> nameCol = new TableColumn<>("Name");

        TableColumn<Owner, String> addressCol = new TableColumn<>("Address");
        TableColumn<Owner, String> streetSubCol = new TableColumn<>("Street");
        TableColumn<Owner, String> buildingNumberSubCol = new TableColumn<>("Building Number");
        addressCol.getColumns().addAll(streetSubCol, buildingNumberSubCol);

        TableColumn<Owner, String> phoneNumberCol = new TableColumn<>("Phone Number");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        streetSubCol.setCellValueFactory(new PropertyValueFactory<>("addressStreet"));
        buildingNumberSubCol.setCellValueFactory(new PropertyValueFactory<>("addressBuildingNumber"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetSubCol.setCellFactory(TextFieldTableCell.forTableColumn());
        buildingNumberSubCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nameCol.setOnEditCommit(editEvent -> {
            TablePosition<Owner, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Owner owner = tableView.getItems().get(rowIndex);

            String updatingRecordName = owner.getName();

            owner.setName(newValue);

            databaseController.controlUpdatingOwner(
                    "name", updatingRecordName, owner
            );
        });
        streetSubCol.setOnEditCommit(editEvent -> {
            TablePosition<Owner, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Owner owner = tableView.getItems().get(rowIndex);

            String updatingRecordName = owner.getName();

            owner.setAddressStreet(newValue);

            databaseController.controlUpdatingOwner(
                    "street", updatingRecordName, owner
            );
        });
        buildingNumberSubCol.setOnEditCommit(editEvent -> {
            TablePosition<Owner, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Owner owner = tableView.getItems().get(rowIndex);

            String updatingRecordName = owner.getName();

            owner.setAddressBuildingNumber(newValue);

            databaseController.controlUpdatingOwner(
                    "buildingNumber", updatingRecordName, owner
            );
        });
        phoneNumberCol.setOnEditCommit(editEvent -> {
            TablePosition<Owner, String> pos = editEvent.getTablePosition();

            String newValue = editEvent.getNewValue();

            int rowIndex = pos.getRow();
            Owner owner = tableView.getItems().get(rowIndex);

            String updatingRecordName = owner.getName();

            owner.setPhoneNumber(newValue);

            databaseController.controlUpdatingOwner(
                    "phoneNumber", updatingRecordName, owner
            );
        });

        tableView.setItems(FXCollections.observableArrayList());

        tableView.getColumns().addAll(nameCol, addressCol, phoneNumberCol);

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
        toolAddButton.setOnAction(addEvent -> new AddingOwnerDialog(tableView.getItems()));

        Button toolDeleteButton = new Button("Delete");
        toolDeleteButton.setGraphic(new ImageView(ClassLoader.getSystemResource("delete.png").toString()));
        toolDeleteButton.setOnAction(deleteEvent -> {
            SelectionModel<Owner> selectionModel = tableView.getSelectionModel();

            Owner owner = selectionModel.getSelectedItem();

            String removableRecordName = owner.getName();

            tableView.getItems().remove(owner);

            databaseController.controlDeletingOwner(removableRecordName);
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
            Owner exhibitionHall = exchanger.exchange(null);

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
