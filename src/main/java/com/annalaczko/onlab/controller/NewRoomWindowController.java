package com.annalaczko.onlab.controller;

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

    int rH,rW;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleOKAction(){
        if (roomHeight.getText()!=null&&roomWidth.getText()!=null) {
            rW=Integer.parseInt(roomWidth.getText());
            rH=Integer.parseInt(roomHeight.getText());
            System.out.println(rW+" "+rH);
            NewRoomWindow.getStage().close();
        }
    }
}
