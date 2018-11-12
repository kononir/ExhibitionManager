package bsuir.vlad.windows;

import bsuir.vlad.database.DatabaseController;
import bsuir.vlad.model.Address;
import bsuir.vlad.model.Owner;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class AddingOwnerDialog {
    AddingOwnerDialog(ObservableList<Owner> owners) {
        Stage stage = new Stage();

        Label nameLabel = new Label("Name");
        Label streetLabel = new Label("Street");
        Label buildingNumberLabel = new Label("Building Number");
        Label phoneNumberLabel = new Label("Phone Number");

        VBox labelsVBox = new VBox(
                nameLabel,
                streetLabel,
                buildingNumberLabel,
                phoneNumberLabel
        );

        double labelsSpacing = 13;
        double labelsWidth = 120;
        labelsVBox.setSpacing(labelsSpacing);
        labelsVBox.setPrefWidth(labelsWidth);

        TextField nameTextField = new TextField();
        TextField streetTextField = new TextField();
        TextField buildingNumberTextField = new TextField();
        TextField phoneNumberTextField = new TextField();

        VBox textFieldsVBox = new VBox(
                nameTextField,
                streetTextField,
                buildingNumberTextField,
                phoneNumberTextField
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
            String street = streetTextField.getText();
            String buildingNumber = buildingNumberTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();

            Address address = new Address(street, buildingNumber);

            Owner owner = new Owner(
                    name,
                    address,
                    phoneNumber
            );

            owners.add(owner);

            DatabaseController controller = new DatabaseController();
            controller.controlInsertingOwner(owner);

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
