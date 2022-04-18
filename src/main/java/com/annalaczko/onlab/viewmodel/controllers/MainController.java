package com.annalaczko.onlab.viewmodel.controllers;

import com.annalaczko.onlab.viewmodel.NewRoomViewModel;
import com.annalaczko.onlab.viewmodel.RobotViewModel;
import com.annalaczko.onlab.viewmodel.RoomViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private MenuBar menuBar;

    //@FXML
    //private Circle robot;

    @FXML
    private BorderPane borderPane;

    public static boolean isRunning=true;

    @FXML
    private void handleExitAction(final ActionEvent event)
    {
        thread.stop();
        Platform.exit();
    }

    private WindowUpdate thread;

    private class WindowUpdate extends Thread{
        public void run (){

            while (isRunning){
                update();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);


    }

    @FXML
    private void handleNewRoomAction (final ActionEvent event) throws Exception {
        NewRoomViewModel.start();
    }

    @FXML
    public void handleLoadAction(){
        SceneController.initialize();
        borderPane.setCenter(SceneController.pane);
    }

    public void update(){
        SceneController.update();
    }

    @FXML
    public void handleHelpAction(){
   }

    @FXML
    public void handleStartAction() throws Exception {

        thread= new WindowUpdate();

        thread.start();

        SceneController.moveRobot();
    }
}
