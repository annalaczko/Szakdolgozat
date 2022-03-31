package com.annalaczko.onlab.controller;

import com.annalaczko.onlab.model.Robot;
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
        int width=Integer.parseInt(roomWidth.getText());
        int height=Integer.parseInt(roomHeight.getText());
        if (height!=0 && width!=0) {
            if  (height< Robot.getRadius()*2) height=Robot.getRadius()*2;
            if  (width< Robot.getRadius()*2) width=Robot.getRadius()*2;
            Room.setCorners(width, height);
            RoomController.initialize();
            NewRoomWindow.getStage().close();
        }
    }
}
