package com.davi.messenger.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new Label("Messager"), 300, 200));
        stage.setTitle("Messager");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
