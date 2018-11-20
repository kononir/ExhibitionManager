package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.usingintable.ExhibitionInformation;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ActualExhibitionsListWindow {
    private Exchanger<ExhibitionInformation> exchanger = new Exchanger<>();
    private ScheduledExecutorService executorService;

    ActualExhibitionsListWindow(Stage stage) {
        DatabaseController databaseController = new DatabaseController();

        databaseController.controlFindingExhibitionsInformationOfAllDates(exchanger);

        Label exhibitionDateLabel = new Label(LocalDate.now().toString());

        double exhibitionDateTop = 10;
        StackPane.setAlignment(exhibitionDateLabel, Pos.TOP_CENTER);
        StackPane.setMargin(exhibitionDateLabel, new Insets(exhibitionDateTop));

        TableView<ExhibitionInformation> tableView = new TableView<>();

        startUpdating(tableView);

        TableColumn<ExhibitionInformation, String> nameCol = new TableColumn<>("Exhibition Name");

        TableColumn<ExhibitionInformation, String> addressCol = new TableColumn<>("Exhibition hall Address");
        TableColumn<ExhibitionInformation, String> streetSubCol = new TableColumn<>("Street");
        TableColumn<ExhibitionInformation, String> buildingNumberSubCol = new TableColumn<>("Building Number");
        addressCol.getColumns().addAll(streetSubCol, buildingNumberSubCol);

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        streetSubCol.setCellValueFactory(new PropertyValueFactory<>("addressStreet"));
        buildingNumberSubCol.setCellValueFactory(new PropertyValueFactory<>("addressBuildingNumber"));

        tableView.getColumns().addAll(
                nameCol,
                addressCol
        );

        for (Object o : tableView.getColumns()) {
            TableColumn tc = (TableColumn) o;
            double minWidth = 150;
            tc.setMinWidth(minWidth);
        }

        for (TableColumn tc : addressCol.getColumns()) {
            double minWidth = 150;
            tc.setMinWidth(minWidth);
        }

        tableView.setItems(FXCollections.observableArrayList());

        double informationAreaRightLeft = 0;
        double informationAreaBottomTop = 50;
        StackPane.setAlignment(tableView, Pos.CENTER);
        StackPane.setMargin(tableView, new Insets(
                informationAreaBottomTop,
                informationAreaRightLeft,
                informationAreaBottomTop,
                informationAreaRightLeft
        ));

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

        StackPane root = new StackPane(exhibitionDateLabel, tableView, cancelButton);

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
        executorService.scheduleAtFixedRate(() -> updatePane(tableView), 0, 10, TimeUnit.MILLISECONDS);
    }

    private void shutdownUpdating() {
        executorService.shutdown();
    }

    private void updatePane(TableView tableView) {
        try {
            ExhibitionInformation information = exchanger.exchange(null);

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
