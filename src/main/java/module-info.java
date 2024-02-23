module com.example.ping_pong {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ping_pong to javafx.fxml;
    exports com.example.ping_pong;
    exports com.example.ping_pong.controller;
    opens com.example.ping_pong.controller to javafx.fxml;
}