package bsuir.vlad.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class ChoosingExhibitionDialog {
    ChoosingExhibitionDialog(Stage nextStage) {
        Stage stage = new Stage();

        Label nameLabel = new Label("Exhibition Name");

        double nameLabelWidth = 100;
        nameLabel.setPrefWidth(nameLabelWidth);

        TextField nameTextField = new TextField();

        HBox inputField = new HBox(nameLabel, nameTextField);

        double space = 5;
        inputField.setSpacing(space);

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
            String exhibitionName = nameTextField.getText();

            stage.close();

            new CurrentExhibitionWindow(nextStage, exhibitionName);
        });

        double buttonsInset = 10;
        StackPane.setAlignment(okButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(okButton, new Insets(buttonsInset));

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelEvent -> {
            stage.close();

            new MainWindow(nextStage);
        });

        StackPane.setAlignment(cancelButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(cancelButton, new Insets(buttonsInset));

        StackPane root = new StackPane(inputField, okButton, cancelButton);

        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.show();
    }
}
