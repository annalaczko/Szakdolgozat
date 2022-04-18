module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    
    exports com.annalaczko.onlab.viewmodel;
    opens com.annalaczko.onlab.viewmodel to javafx.fxml;
    exports com.annalaczko.onlab.model;
    opens com.annalaczko.onlab.model to javafx.fxml;
    exports com.annalaczko.onlab.viewmodel.controllers;
    opens com.annalaczko.onlab.viewmodel.controllers to javafx.fxml;
}