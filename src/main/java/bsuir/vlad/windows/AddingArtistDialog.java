package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.model.Artist;
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

class AddingArtistDialog {
    AddingArtistDialog(ObservableList<Artist> artists) {
        Stage stage = new Stage();

        Label firstNameLabel = new Label("First Name");
        Label lastNameLabel = new Label("Last Name");
        Label patronymicLabel = new Label("Patronymic");
        Label birthdayPlaceLabel = new Label("Birthday Place");
        Label birthdayDateLabel = new Label("Birthday Date");
        Label vitaeLabel = new Label("Vitae");
        Label educationLabel = new Label("Education");

        VBox labelsVBox = new VBox(
                firstNameLabel,
                lastNameLabel,
                patronymicLabel,
                birthdayPlaceLabel,
                birthdayDateLabel,
                vitaeLabel,
                educationLabel
        );

        double labelsSpacing = 13;
        double labelsWidth = 150;
        labelsVBox.setSpacing(labelsSpacing);
        labelsVBox.setPrefWidth(labelsWidth);

        TextField firstNameTextField = new TextField();
        TextField lastNameTextField = new TextField();
        TextField patronymicTextField = new TextField();
        TextField birthdayPlaceTextField = new TextField();

        DatePicker datePicker = new DatePicker();

        TextField vitaeTextField = new TextField();
        TextField educationTextField = new TextField();

        VBox textFieldsVBox = new VBox(
                firstNameTextField,
                lastNameTextField,
                patronymicTextField,
                birthdayPlaceTextField,
                datePicker,
                vitaeTextField,
                educationTextField
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
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String patronymic = patronymicTextField.getText();
            String birthdayPlace = birthdayPlaceTextField.getText();
            LocalDate birthdayDate = datePicker.getValue();
            String vitae = vitaeTextField.getText();
            String education = educationTextField.getText();

            Artist artist = new Artist(
                    firstName,
                    lastName,
                    patronymic,
                    birthdayPlace,
                    birthdayDate,
                    vitae,
                    education
            );

            artists.add(artist);

            DatabaseController controller = new DatabaseController();
            controller.controlInsertingArtist(artist);

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
