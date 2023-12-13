package main.a7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load(), 1000, 700);

        FXMLLoader fxmlLoader2 = new FXMLLoader(MainApplication.class.getResource("window.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load(), 500, 400);

        stage.setTitle("~ Toy Language Interpreter ~");
        stage.setScene(scene1);
        stage.show();

        Stage stage1 = new Stage();
        stage1.setScene(scene2);
        stage1.setTitle("~ Programs ~");
        stage1.show();


    }

    public static void main(String[] args) {
        launch();
    }
}