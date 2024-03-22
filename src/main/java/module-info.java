module programmingtechnology.dao {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;

    opens programmingtechnology.dao.controllers to javafx.controls, javafx.fxml, javafx.graphics, com.google.gson;
    opens programmingtechnology.dao.models to javafx.base, com.google.gson;
    opens programmingtechnology.dao to javafx.fxml;

    exports programmingtechnology.dao;
    exports programmingtechnology.dao.controllers;
    opens programmingtechnology.dao.models.json to com.google.gson, javafx.base;
}