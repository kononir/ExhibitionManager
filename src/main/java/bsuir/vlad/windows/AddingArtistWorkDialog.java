package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.model.ArtistWork;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

class AddingArtistWorkDialog {
    AddingArtistWorkDialog(ObservableList<ArtistWork> artistWorks) {
        Stage stage = new Stage();

        Label nameLabel = new Label("Name");
        Label typeLabel = new Label("Type");
        Label creationDateLabel = new Label("Creation Date");
        Label widthLabel = new Label("Width");
        Label heightLabel = new Label("Height");
        Label volumeLabel = new Label("Volume");
        Label artistLastNameLabel = new Label("Artist Last name");
        Label exhibitionNameLabel = new Label("Exhibition Name");

        VBox labelsVBox = new VBox(
                nameLabel,
                typeLabel,
                creationDateLabel,
                widthLabel,
                heightLabel,
                volumeLabel,
                artistLastNameLabel,
                exhibitionNameLabel
        );

        double labelsSpacing = 13;
        double labelsWidth = 150;
        labelsVBox.setSpacing(labelsSpacing);
        labelsVBox.setPrefWidth(labelsWidth);

        TextField nameTextField = new TextField();
        TextField typeTextField = new TextField();

        DatePicker datePicker = new DatePicker();

        TextField widthTextField = new TextField();
        TextField heightTextField = new TextField();
        TextField volumeTextField = new TextField();

        TextField artistLastNameTextField = new TextField();
        TextField exhibitionNameTextField = new TextField();

        VBox textFieldsVBox = new VBox(
                nameTextField,
                typeTextField,
                datePicker,
                widthTextField,
                heightTextField,
                volumeTextField,
                artistLastNameTextField,
                exhibitionNameTextField
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
            String name = nameTextField.getText();
            String type = typeTextField.getText();
            LocalDate creationDate = datePicker.getValue();
            Double width = Double.valueOf(widthTextField.getText());
            Double height = Double.valueOf(heightTextField.getText());
            Double volume = Double.valueOf(volumeTextField.getText());
            String artistLastName = artistLastNameTextField.getText();
            String exhibitionName = exhibitionNameTextField.getText();

            ArtistWork artistWork = new ArtistWork(
                    name,
                    type,
                    creationDate,
                    width,
                    height,
                    volume,
                    artistLastName,
                    exhibitionName
            );

            artistWorks.add(artistWork);

            DatabaseController controller = new DatabaseController();
            controller.controlInsertingArtistWork(artistWork);

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
