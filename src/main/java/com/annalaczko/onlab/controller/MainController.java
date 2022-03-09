package com.annalaczko.onlab.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private MenuBar menuBar;

    @FXML
    private AnchorPane anchorPane;


    @FXML
    private void handleExitAction(final ActionEvent event)
    {
        Platform.exit();
    }


    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);
        anchorPane.setPrefWidth(1000);
        anchorPane.setPrefHeight(600);
    }

    @FXML
    private void handleNewRoomAction (final ActionEvent event) throws Exception {
        NewRoomWindow.start();
    }

    @FXML
    public void handleLoadAction(){
        anchorPane.setPrefHeight(RoomController.getAnchorPane().getPrefHeight());
        anchorPane.setPrefWidth(RoomController.getAnchorPane().getPrefWidth());
    }


}
