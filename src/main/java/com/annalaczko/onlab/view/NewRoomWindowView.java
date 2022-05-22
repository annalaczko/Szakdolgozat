package com.annalaczko.onlab.view;

import com.annalaczko.onlab.model.RobotModel;
import com.annalaczko.onlab.model.RoomModel;
import com.annalaczko.onlab.viewmodel.NewRoomViewModel;
import com.annalaczko.onlab.viewmodel.RobotViewModel;
import com.annalaczko.onlab.viewmodel.RoomViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class NewRoomWindowView implements Initializable
{
    @FXML
    private TextField x1;
    @FXML
    private TextField y1;
    @FXML
    private TextField x2;
    @FXML
    private TextField y2;
    @FXML
    private TextField x3;
    @FXML
    private TextField y3;

    @FXML
    private TextField roomWidth;

    @FXML
    private TextField roomHeight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
    @FXML
    private void handleOKAction(){

        int width=Integer.parseInt(roomWidth.getText());
        int height=Integer.parseInt(roomHeight.getText());
        if (height!=0 && width!=0) {
            if  (height< RobotModel.getRadius()*2) height= RobotModel.getRadius()*2;
            if  (width< RobotModel.getRadius()*2) width= RobotModel.getRadius()*2;
            NewRoomViewModel.getStage().close();
            RoomModel.setCorners(width, height);

        }

        int  [] xpoints={Integer.parseInt(x1.getText()), Integer.parseInt(x2.getText()), Integer.parseInt(x3.getText())} ;
        int  [] ypoints={Integer.parseInt(y1.getText()), Integer.parseInt(y2.getText()), Integer.parseInt(y3.getText())};

        RoomModel.objects.add(new Polygon(xpoints,ypoints,3));

        RobotViewModel.initialize();
        RoomViewModel.initialize();
    }
}
