package com.cameronm.inventorymanager;

import com.cameronm.inventorymanager.controllers.MainController;
import com.cameronm.inventorymanager.models.*;
import com.cameronm.inventorymanager.utilities.AutoIncrementingPartId;
import com.cameronm.inventorymanager.utilities.AutoIncrementingProductId;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The Main class is the main class for this application.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-01
 */
public class Main extends Application {

    /**
     * The constructor for the Main class
     */
    public Main() {}

    /**
     * The main method is the entry point for this program.
     *
     * @param args command line arguments passed to the program
     */
    public static void main(String[] args) {

        //adding initial parts and products for testing purposes

        ObservableList<Part> partsList = FXCollections.observableArrayList();
        partsList.add(new InHouse(
                AutoIncrementingPartId.getNextID(),
                "Screws",
                0.50,
                50,
                10,
                100,
                50
        ));
        partsList.add(new InHouse(
                AutoIncrementingPartId.getNextID(),
                "Nuts",
                0.20,
                75,
                5,
                150,
                52
        ));


        partsList.add(new Outsourced(
                AutoIncrementingPartId.getNextID(),
                "Wheels",
                1.70,
                40,
                10,
                75,
                "FakeCO"
        ));
        partsList.add(new Outsourced(
                AutoIncrementingPartId.getNextID(),
                "Plastic Cover",
                1.50,
                15,
                10,
                20,
                "FakeCO"
        ));

        partsList.forEach(Inventory::addPart);
        ObservableList<Part> partList1 = FXCollections.observableArrayList();
        partList1.add(partsList.get(0));
        partList1.add(partsList.get(2));
        ObservableList<Part> partList2 = FXCollections.observableArrayList();
        //partList2.add(partsList.get(1));
        //partList2.add(partsList.get(3));
        Inventory.addProduct(new Product(
                partList1,
                AutoIncrementingProductId.getNextID(),
                "Shovel",
                15,
                30,
                20,
                50
        ));
        Inventory.addProduct(new Product(
                partList2,
                AutoIncrementingProductId.getNextID(),
                "WheelBarrow",
                75,
                20,
                10,
                25
        ));

        launch(args);
    }

    /**
     * The start method sets up the JavaFX stage and displays it to the user.
     *
     * @param stage the primary stage for the JavaFX application
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
        Image logoImage = new Image(String.valueOf(Main.class.getResource("logo.png")));
        stage.getIcons().add(logoImage);
        Scene scene = new Scene(fxmlLoader.load());
        String cssFile = Objects.requireNonNull(Main.class.getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(cssFile);
        stage.setScene(scene);
        stage.setTitle("Inventory Manager");
        stage.show();
        stage.setOnCloseRequest(event -> {
                    event.consume();
                    MainController.exitProgram();
                });
    }
}
