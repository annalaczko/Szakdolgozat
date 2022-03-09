module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports com.annalaczko.onlab.view;
    opens com.annalaczko.onlab.view to javafx.fxml;
    exports com.annalaczko.onlab.controller;
    opens com.annalaczko.onlab.controller to javafx.fxml;
    exports com.annalaczko.onlab.model;
    opens com.annalaczko.onlab.model to javafx.fxml;
}