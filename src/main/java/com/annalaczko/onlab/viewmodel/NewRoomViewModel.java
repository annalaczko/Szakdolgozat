package com.annalaczko.onlab.viewmodel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewRoomViewModel {

    private static Stage stage;

    public static void start() throws Exception {
        stage=new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(NewRoomViewModel.class.getResource("/view/new-room-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage.setTitle("New Room");
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}
