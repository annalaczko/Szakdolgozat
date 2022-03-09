package com.annalaczko.onlab.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewRoomWindow {

    private static Stage stage;

    public static void start() throws Exception {
        stage=new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(NewRoomWindow.class.getResource("/new-room-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 80);
        stage.setTitle("New Room");
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}
