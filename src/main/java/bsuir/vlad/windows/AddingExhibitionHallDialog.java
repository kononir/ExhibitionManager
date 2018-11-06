package bsuir.vlad.windows;

import bsuir.vlad.model.Address;
import bsuir.vlad.model.ExhibitionHall;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddingExhibitionHallDialog {
    public AddingExhibitionHallDialog(ObservableList<ExhibitionHall> exhibitionHalls) {
        Stage stage = new Stage();

        Label nameLabel = new Label("Name");
        Label squareLabel = new Label("Square");
        Label streetLabel = new Label("Street");
        Label buildingNumberLabel = new Label("Building Number");
        Label phoneNumberLabel = new Label("Phone Number");
        Label ownerNameLabel = new Label("Owner Name");

        VBox labelsVBox = new VBox(
                nameLabel,
                squareLabel,
                streetLabel,
                buildingNumberLabel,
                phoneNumberLabel,
                ownerNameLabel
        );

        double labelsSpacing = 13;
        double labelsWidth = 120;
        labelsVBox.setSpacing(labelsSpacing);
        labelsVBox.setPrefWidth(labelsWidth);

        TextField nameTextField = new TextField();
        TextField squareTextField = new TextField();
        TextField streetTextField = new TextField();
        TextField buildingNumberTextField = new TextField();
        TextField phoneNumberTextField = new TextField();
        TextField ownerNameTextField = new TextField();

        VBox textFieldsVBox = new VBox(
                nameTextField,
                squareTextField,
                streetTextField,
                buildingNumberTextField,
                phoneNumberTextField,
                ownerNameTextField
        );

        double textFieldsSpace = 5;
        textFieldsVBox.setSpacing(textFieldsSpace);

        HBox inputField = new HBox(labelsVBox, textFieldsVBox);

        double inputFieldTopLeftRight = 10;
        double inputFieldBottom = 50;
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
            double square = Double.valueOf(squareTextField.getText());
            String street = streetTextField.getText();
            String buildingNumber = buildingNumberTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String ownerName = ownerNameTextField.getText();

            Address address = new Address(street, buildingNumber);

            ExhibitionHall exhibitionHall = new ExhibitionHall(
                    name,
                    square,
                    address,
                    phoneNumber,
                    ownerName
            );

            exhibitionHalls.add(exhibitionHall);

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
