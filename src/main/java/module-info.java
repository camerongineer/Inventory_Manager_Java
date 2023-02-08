/**
 * module for program
 */
module com.cameronm.inventorymanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cameronm.inventorymanager to javafx.fxml;
    exports com.cameronm.inventorymanager;
    exports com.cameronm.inventorymanager.controllers;
    opens com.cameronm.inventorymanager.controllers to javafx.fxml;
    opens com.cameronm.inventorymanager.models to javafx.base;
}