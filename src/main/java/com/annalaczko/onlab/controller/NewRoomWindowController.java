package com.annalaczko.onlab.controller;

import com.annalaczko.onlab.model.Room;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewRoomWindowController implements Initializable
{
    @FXML
    private TextField roomWidth;

    @FXML
    private TextField roomHeight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void handleOKAction(){
        if (roomHeight.getText()!=null&&roomWidth.getText()!=null) {
            Room.setCorners(Integer.parseInt(roomWidth.getText()),Integer.parseInt(roomHeight.getText()));
            RoomController.update();
            NewRoomWindow.getStage().close();
        }
    }
}
