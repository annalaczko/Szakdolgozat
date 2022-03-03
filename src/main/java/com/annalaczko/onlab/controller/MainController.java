package com.annalaczko.onlab.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.MenuBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private MenuBar menuBar;

    @FXML
    private VBox vBox;

    @FXML
    private Circle robot;

    @FXML
    private void handleExitAction(final ActionEvent event)
    {
        Platform.exit();
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

        double deltaX = 2;
        double deltaY = 2;

        @Override
        public void handle(ActionEvent actionEvent) {
            robot.setLayoutX(robot.getLayoutX() + deltaX);
            robot.setLayoutY(robot.getLayoutY() + deltaY);

            Bounds bounds = vBox.getBoundsInLocal();
            boolean rightBorder = robot.getLayoutX() >= (bounds.getMaxX() - robot.getRadius());
            boolean leftBorder = robot.getLayoutX() <= (bounds.getMinX() + robot.getRadius());
            boolean bottomBorder = robot.getLayoutY() >= (bounds.getMaxY() - robot.getRadius());
            boolean topBorder = robot.getLayoutY() <= (bounds.getMinY() + robot.getRadius());

            if (rightBorder || leftBorder) {
                deltaX *= -1;
            }
            if (bottomBorder || topBorder) {
                deltaY *= -1;
            }
        }
    }));


    /**
     * Handle action related to input (in this case specifically only responds to
     * keyboard event CTRL-A).
     *
     * @param event Input event.
     */
    @FXML
    private void handleKeyInput(final InputEvent event)
    {
        if (event instanceof KeyEvent)
        {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A)
            {
                provideAboutFunctionality();
            }
        }
    }

    /**
     * Perform functionality associated with "About" menu selection or CTRL-A.
     */
    private void provideAboutFunctionality()
    {
        System.out.println("You clicked on About!");
    }


    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
}
