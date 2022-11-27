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
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewRoomWindowView implements Initializable {

    @FXML
    private CheckBox IsTurnedOver;

    @FXML
    private TextField roomWidth;

    @FXML
    private TextField roomHeight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void handleOKAction() {

        int width = Integer.parseInt(roomWidth.getText());
        int height = Integer.parseInt(roomHeight.getText());
        if (height != 0 && width != 0) {
            if (height < RobotModel.getRadius() * 2) height = RobotModel.getRadius() * 2;
            if (width < RobotModel.getRadius() * 2) width = RobotModel.getRadius() * 2;
            NewRoomViewModel.getStage().close();
            RoomModel.setCorners(width, height);
            RoomModel.addObject();

        }


        RoomViewModel.initialize();
        RobotViewModel.initialize();
        if (IsTurnedOver.isSelected()) {
            OrientationConverter.turned = true;

            OrientationConverter.convert();
            RobotModel.setCorner(0);
        }

    }
}
