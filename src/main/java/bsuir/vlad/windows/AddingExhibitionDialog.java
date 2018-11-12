package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.model.Exhibition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

class AddingExhibitionDialog {
    AddingExhibitionDialog(ObservableList<Exhibition> exhibitions) {
        Stage stage = new Stage();

        Label typeLabel = new Label("Type");
        Label nameLabel = new Label("Name");
        Label dateLabel = new Label("Date");
        Label timeLabel = new Label("Time");
        Label exhibitionHallNameLabel = new Label("Exhibition Hall Name");

        VBox labelsVBox = new VBox(
                typeLabel,
                nameLabel,
                dateLabel,
                timeLabel,
                exhibitionHallNameLabel
        );

        double labelsSpacing = 13;
        double labelsWidth = 150;
        labelsVBox.setSpacing(labelsSpacing);
        labelsVBox.setPrefWidth(labelsWidth);

        TextField typeTextField = new TextField();
        TextField nameTextField = new TextField();

        DatePicker datePicker = new DatePicker();
        datePicker.dayCellFactoryProperty();

        TextField exhibitionHallNameTextField = new TextField();

        VBox textFieldsVBox = new VBox(
                typeTextField,
                nameTextField,
                datePicker,
                exhibitionHallNameTextField
        );

        double textFieldsSpace = 5;
        textFieldsVBox.setSpacing(textFieldsSpace);

        HBox inputField = new HBox(labelsVBox, textFieldsVBox);

        double inputFieldTopLeftRight = 10;
        double inputFieldBottom = 60;
        StackPane.setAlignment(inputField, Pos.TOP_CENTER);
        StackPane.setMargin(inputField, new Insets(
                inputFieldTopLeftRight,
                inputFieldTopLeftRight,
                inputFieldBottom,
                inputFieldTopLeftRight)
        );

        Button okButton = new Button("OK");
        okButton.setOnAction(okEvent -> {
            String type = typeTextField.getText();
            String name = nameTextField.getText();
            LocalDate date = datePicker.getValue();
            String exhibitionHallName = exhibitionHallNameTextField.getText();

            Exhibition exhibition = new Exhibition(
                    type,
                    name,
                    date,
                    exhibitionHallName
            );

            exhibitions.add(exhibition);

            DatabaseController controller = new DatabaseController();
            controller.controlInsertingExhibition(exhibition);

            stage.close();
        });

        double buttonsInset = 10;
        StackPane.setAlignment(okButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(okButton, new Insets(buttonsInset));

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelEvent -> stage.close());

        StackPane.setAlignment(cancelButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(cancelButton, new Insets(buttonsInset));

        StackPane root = new StackPane(inputField, okButton, cancelButton);

        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.show();
    }
}
