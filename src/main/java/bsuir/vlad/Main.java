package bsuir.vlad;

import bsuir.vlad.windows.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new MainWindow(primaryStage);
    }
}
