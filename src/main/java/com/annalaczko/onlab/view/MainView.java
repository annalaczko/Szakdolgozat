package com.annalaczko.onlab.view;

import com.annalaczko.onlab.viewmodel.NewRoomViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

import java.util.ResourceBundle;

public class MainView implements Initializable {
    public static boolean isRunning = true;
    @FXML
    private MenuBar menuBar;
    @FXML
    private BorderPane borderPane;
    private WindowUpdate thread;

    @FXML
    private void handleExitAction(final ActionEvent event) {
        thread.stop();
        Platform.exit();
    }

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);
    }

    @FXML
    private void handleNewRoomAction(final ActionEvent event) throws Exception {
        NewRoomViewModel.start();
    }

    @FXML
    public void handleLoadAction() {
        SceneView.initialize();
        borderPane.setCenter(SceneView.pane);
    }

    public void update() {
        SceneView.update();
    }

    @FXML
    public void handleHelpAction() {
    }

    @FXML
    public void handleStartAction() throws Exception {

        borderPane.setCenter(SceneView.pane);


        thread = new WindowUpdate();

        thread.start();

        SceneView.moveRobot();
    }

    private class WindowUpdate extends Thread {
        public void run() {

            while (isRunning) {
                update();
                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
