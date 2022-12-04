package com.annalaczko.onlab.view;

import com.annalaczko.onlab.model.OrientationConverter;
import com.annalaczko.onlab.model.data.RobotModel;
import com.annalaczko.onlab.model.data.RoomModel;
import com.annalaczko.onlab.viewmodel.NewRoomViewModel;
import com.annalaczko.onlab.viewmodel.RobotViewModel;
import com.annalaczko.onlab.viewmodel.RoomViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class NewRoomWindowView implements Initializable {

    @FXML
    private CheckBox IsTurnedOver;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private Slider slider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initChoiceBox();

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            RobotModel.speed = newValue.intValue();

        });
    }

    @FXML
    private void handleOKAction() {

        RoomModel.addObject();

        RoomViewModel.initialize();

        RobotViewModel.initialize();

        if (IsTurnedOver.isSelected()) {
            OrientationConverter.turned = true;

            OrientationConverter.convert();
            RobotModel.setCorner(0);
        }
        NewRoomViewModel.getStage().close();

    }
    private void initChoiceBox() {
        choiceBox.getItems().add("Hálószoba 1");
        choiceBox.getItems().add("Hálószoba 2");
        choiceBox.getItems().add("Hálószoba 3");
        choiceBox.getItems().add("Hálószoba 4");
        choiceBox.getItems().add("Mosdó 1");
        choiceBox.getItems().add("Mosdó 2");
        choiceBox.getItems().add("Nappali és konyha");
        choiceBox.getItems().add("Előtér");

        choiceBox.setOnAction((event) -> {
            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
            RoomModel.activeRoomIndex = selectedIndex;
        });

    }
}
