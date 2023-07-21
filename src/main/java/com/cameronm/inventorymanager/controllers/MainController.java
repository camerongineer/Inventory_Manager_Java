package com.cameronm.inventorymanager.controllers;

import com.cameronm.inventorymanager.Main;
import com.cameronm.inventorymanager.models.Inventory;
import com.cameronm.inventorymanager.models.Part;
import com.cameronm.inventorymanager.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * The MainController class controls the logic of the Main window of the application.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-02
 */
public class MainController implements Initializable {

    /**
     * the alert for confirming selections
     */
    Alert confirmAlert;

    /**
     * the button for confirming a selection
     */
    static ButtonType buttonTypeOK;

    /**
     * the button for canceling a prompt
     */
    static ButtonType buttonTypeCancel;

    /**
     * the anchor pane for the main window
     */
    @FXML
    private AnchorPane mainWindow;

    /**
     * the column displaying the part ID in the part table
     */
    @FXML
    private TableColumn<Part, Integer> idPartTable;

    /**
     * the column displaying the product ID in the product table
     */
    @FXML
    private TableColumn<Product, Integer> idProductTable;

    /**
     * the column displaying the part name in the part table
     */
    @FXML
    private TableColumn<Part, String> namePartTable;

    /**
     * the column displaying the product name in the product table
     */
    @FXML
    private TableColumn<Product, String> nameProductTable;

    /**
     * the column displaying the part stock in the part table
     */
    @FXML
    private TableColumn<Part, Integer> inventoryPartTable;

    /**
     * the column displaying the product stock in the product table
     */
    @FXML
    private TableColumn<Product, Integer> inventoryProductTable;

    /**
     * the column displaying the part price in the part table
     */
    @FXML
    private TableColumn<Part, Double> pricePartTable;

    /**
     * the column displaying the product price in the product table
     */
    @FXML
    private TableColumn<Product, Double> priceProductTable;

    /**
     * the table displaying each part
     */
    @FXML
    private TableView<Part> partTable;

    /**
     * the table displaying each product
     */
    @FXML
    private TableView<Product> productTable;

    /**
     * the text field for searching for parts in the part table
     */
    @FXML
    private TextField partSearchTextField;

    /**
     * the label displaying if a part is not found in the part table
     */
    @FXML
    private Label partNotFoundLabel;

    /**
     * the text field for searching for products in the product table
     */
    @FXML
    private TextField productSearchTextField;

    /**
     * the label displaying if a product is not found in the product table
     */
    @FXML
    private Label productNotFoundLabel;

    /**
     * The partSearchTextFieldTyped method passes arguments to the partSearchTextField method.
     */
    @FXML
    void partSearchTextFieldTyped() {
        partSearchTextField(partSearchTextField, partTable, idPartTable,
                namePartTable, inventoryPartTable, pricePartTable, partNotFoundLabel);
    }

    /**
     * The constructor for the MainController Class
     */
    public MainController() {}

    /**
     * The partSearchTextField method checks the part search text field every time something is typed into
     * the field and refreshes the parts table with a list of parts that match the beginning of the name or if it
     * matches an id of a part, the part is selected.
     *
     * @param textField the text field where parts are being searched for
     * @param partTable the table being searched
     * @param idColumn the id of the part
     * @param nameColumn the name of the part
     * @param inventoryColumn the stock quantity of the part
     * @param priceColumn the price of the part
     * @param partNotFoundLabel the label that displays the error message
     */
    static void partSearchTextField(TextField textField,
                                    TableView<Part> partTable,
                                    TableColumn<Part, Integer> idColumn,
                                    TableColumn<Part, String> nameColumn,
                                    TableColumn<Part, Integer> inventoryColumn,
                                    TableColumn<Part, Double> priceColumn,
                                    Label partNotFoundLabel) {
        partNotFoundLabel.setVisible(false);
        if (textField.getText().isBlank()) {
            refreshItemTable(Inventory.getAllParts(), partTable, idColumn,
                    nameColumn, inventoryColumn, priceColumn);
        } else {
            ObservableList<Part> partList;
            partList = FXCollections.observableList(Inventory.getAllParts().stream().filter(
                    part -> (part.getName()
                            .toLowerCase()
                            .startsWith(textField.getText().toLowerCase().stripTrailing())) ||
                            (String.valueOf(part.getId())
                                    .equals(textField.getText()))
            ).collect(Collectors.toList()));
            partTable.getSelectionModel().clearSelection();
            if (partList.size() == 1 &&
                    textField.getText().equals(String.valueOf(partList.get(0).getId()))) {
                int id = partList.get(0).getId();
                partList = Inventory.getAllParts();
                for (int i = 0; i < partList.size(); i++) {
                    Part partToSelect = partList.get(i);
                    if (partToSelect.getId() == id) {
                        partTable.getSelectionModel().select(i);
                    }
                }
            } else if (partList.size() == 0) {
                partNotFoundLabel.setVisible(true);
                partList = Inventory.getAllParts();
                partTable.getSelectionModel().clearSelection();
            }
            refreshItemTable(partList, partTable, idColumn,
                    nameColumn, inventoryColumn, priceColumn);
            if (partList.size() == 1) {
                partTable.getSelectionModel().select(0);
            }
        }
    }

    /**
     * The addPartButtonClicked method passes a title to the addOrModifyPart method for creation of a new part.
     */
    @FXML
    void addPartButtonClicked() {
        try {
            addOrModifyPart("Add new part", null);
        } catch (IOException io) {
            System.out.println("addPartButtonClicked method failed");
            io.printStackTrace();
        }
    }

    /**
     * The modifyPartButtonClicked method passes the part selected in the part table on the main screen
     * to the addOrModifyPart method for modification and passes a title for the window.
     */
    @FXML
    void modifyPartButtonClicked() {
        Part partToBeModified = partTable.getSelectionModel().getSelectedItem();
        if (partToBeModified == null) {
            return;
        }
        try {
            addOrModifyPart("Modify part: { " + partToBeModified.getName() + " }", partToBeModified);
        } catch (IOException io) {
            System.out.println("modifyPartButtonClicked method failed");
            io.printStackTrace();
        }
    }

    /**
     * The addOrModifyPart method creates the stage and scene for the add/modify part window
     *
     * @param title the title of the window
     * @param part the part to be modified or null if a new part is being created
     * @throws IOException throws exception if PartView.fxml is inaccessible
     */
    void addOrModifyPart(String title, Part part) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PartView.fxml"));
        Image logoImage = new Image(String.valueOf(Main.class.getResource("logo.png")));
        stage.getIcons().add(logoImage);
        Scene scene = new Scene(fxmlLoader.load());
        String cssFile = Objects.requireNonNull(Main.class.getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(cssFile);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(title);

        mainWindow.setDisable(true);
        PartController PartController = fxmlLoader.getController();
        PartController.addOrModifyPart(part);

        stage.showAndWait();
        mainWindow.setDisable(false);
        refreshItemTable(Inventory.getAllParts(), partTable, idPartTable,
                namePartTable, inventoryPartTable, pricePartTable);
    }

    /**
     * The deletePartButtonClicked method selects the part that is currently highlighted in the parts table on
     * main page and pushes a confirmation alert to the user to confirm deletion of the part.
     */
    @FXML
    void deletePartButtonClicked() {
        Part partToBeRemoved = partTable.getSelectionModel().getSelectedItem();
        if (partToBeRemoved == null) {
            return;
        }
        confirmAlert.setTitle("Delete part");
        confirmAlert.setHeaderText("Would you like to delete part: { " + partToBeRemoved.getName() + " }?");
        confirmAlert.setContentText("Are you sure?");
        confirmAlert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.orElseThrow() == buttonTypeOK){
            partTable.getItems().remove(partToBeRemoved);
        }
        refreshItemTable(Inventory.getAllParts(), partTable, idPartTable,
                namePartTable, inventoryPartTable, pricePartTable);
    }

    /**
     * The productSearchTextFieldTyped method checks the product search text field every time something is typed into
     * the field and refreshes the product table with a list of products that match the beginning of the id or name
     * that matches what is currently in the text field. The search is case-insensitive.
     */
    @FXML
    void productSearchTextFieldTyped() {
        productNotFoundLabel.setVisible(false);
        if (productSearchTextField.getText().isBlank()) {
            refreshItemTable(Inventory.getAllProducts(), productTable, idProductTable,
                    nameProductTable, inventoryProductTable, priceProductTable);
        } else {
            ObservableList<Product> productList;
            productList = FXCollections.observableList(Inventory.getAllProducts().stream().filter(
                    part -> (part.getName()
                            .toLowerCase()
                            .startsWith(productSearchTextField.getText().toLowerCase().stripTrailing())) ||
                            (String.valueOf(part.getId())
                                    .equals(productSearchTextField.getText()))
            ).collect(Collectors.toList()));
            productTable.getSelectionModel().clearSelection();
            if (productList.size() == 1 &&
                    productSearchTextField.getText().equals(String.valueOf(productList.get(0).getId()))) {
                int id = productList.get(0).getId();
                productList = Inventory.getAllProducts();
                for (int i = 0; i < productList.size(); i++) {
                    Product productToSelect = productList.get(i);
                    if (productToSelect.getId() == id) {
                        productTable.getSelectionModel().select(i);
                    }
                }
            } else if (productList.size() == 0) {
                productNotFoundLabel.setVisible(true);
                productList = Inventory.getAllProducts();
                productTable.getSelectionModel().clearSelection();
            }
            refreshItemTable(productList, productTable, idProductTable,
                    nameProductTable, inventoryProductTable, priceProductTable);
            if (productList.size() == 1) {
                productTable.getSelectionModel().select(0);
            }
        }
    }

    /**
     * The addProductButtonClicked method passes a title to the addOrModifyProduct method for creation of a new product.
     */
    @FXML
    void addProductButtonClicked() {
        try {
            addOrModifyProduct("Add new product", null);

        } catch (IOException io) {
            System.out.println("addProductButtonClicked method failed");
            io.printStackTrace();
        }
    }

    /**
     * The modifyProductButtonClicked method passes the product selected in the product table on the main screen
     * to the addOrModifyProduct method for modification and passes a title for the window.
     */
    @FXML
    void modifyProductButtonClicked() {
        Product productToBeModified = productTable.getSelectionModel().getSelectedItem();
        if (productToBeModified == null) {
            return;
        }
        try {
            addOrModifyProduct("Modify product: { " + productToBeModified.getName() + " }", productToBeModified);
        } catch (IOException io) {
            System.out.println("modifyProductButtonClicked method failed");
            io.printStackTrace();
        }
    }

    /**
     * The addOrModifyProduct method creates the stage and scene for the add/modify product window
     *
     * @param title the title of the window
     * @param product the product to be modified or null if a new product is being created
     * @throws IOException throws exception if ProductView.fxml is inaccessible
     */
    void addOrModifyProduct(String title, Product product) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProductView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image logoImage = new Image(String.valueOf(Main.class.getResource("logo.png")));
        stage.getIcons().add(logoImage);
        String cssFile = Objects.requireNonNull(Main.class.getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(cssFile);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(title);


        mainWindow.setDisable(true);
        ProductController productController = fxmlLoader.getController();
        productController.addOrModifyProduct(product);

        stage.showAndWait();
        mainWindow.setDisable(false);
        refreshItemTable(Inventory.getAllProducts(), productTable, idProductTable,
                nameProductTable, inventoryProductTable, priceProductTable);
    }

    /**
     * The deleteProductButtonClicked method selects the part that is currently highlighted in the parts table on
     * main page and pushes a confirmation alert to the user to confirm deletion of the part.
     */
    @FXML
    void deleteProductButtonClicked() {
        Product productToBeRemoved = productTable.getSelectionModel().getSelectedItem();
        if (productToBeRemoved == null) {
            return;
        }

        if (!productToBeRemoved.getAllAssociatedParts().isEmpty()) {
            Alert confirmAlert = new Alert(Alert.AlertType.ERROR);
            confirmAlert.setTitle("Error deleting: { " + productToBeRemoved.getName() + " }");
            confirmAlert.setHeaderText("Unable to delete this product because it contains associated parts");
            confirmAlert.setContentText("Please remove all associated parts to delete this product");
            confirmAlert.getButtonTypes().setAll(buttonTypeOK);

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.orElseThrow() == buttonTypeOK) {
                return;
            }
        } else {
            confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Delete product");
            confirmAlert.setHeaderText("Would you like to delete product: { " + productToBeRemoved.getName() + " }?");
            confirmAlert.setContentText("Are you sure?");
            confirmAlert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.orElseThrow() == buttonTypeOK) {
                Inventory.deleteProduct(productToBeRemoved);
            }
        }
        refreshItemTable(Inventory.getAllProducts(), productTable, idProductTable,
                nameProductTable, inventoryProductTable, priceProductTable);
    }

    /**
     * The refreshItemTable updates a given table with values from the itemList
     *
     * @param itemList the ObservableList of parts or products populating the table
     * @param itemTable the part table or product table being initialized
     * @param idColumn id of the part or product
     * @param nameColumn name of the part or product
     * @param inventoryColumn stock of the part or product
     * @param priceColumn price of the part or product
     * @param <Item> a part or product
     * @param <ItemList> a part table or product table
     */
    static <Item, ItemList extends ObservableList<Item>> void refreshItemTable(ItemList itemList,
                                                                               TableView<Item> itemTable,
                                                                               TableColumn<Item, Integer> idColumn,
                                                                               TableColumn<Item, String> nameColumn,
                                                                               TableColumn<Item, Integer> inventoryColumn,
                                                                               TableColumn<Item, Double> priceColumn) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean blank) {
                super.updateItem(price, blank);
                if (blank) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", price));
                }
            }
        });
        itemTable.setItems(itemList);
        itemTable.refresh();
    }

    /**
     * The initialize method initializes buttons and populates tables.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

        buttonTypeOK = new ButtonType("OK");
        buttonTypeCancel = new ButtonType("Cancel");

        refreshItemTable(Inventory.getAllParts(), partTable, idPartTable,
                namePartTable, inventoryPartTable, pricePartTable);
        refreshItemTable(Inventory.getAllProducts(), productTable, idProductTable,
                nameProductTable, inventoryProductTable, priceProductTable);
    }

    /**
     * The exitButtonClicked method pushes a confirmation alert to the user to confirm exiting the program.
     */
    @FXML
    void exitButtonClicked() {
        exitProgram();
    }

    /**
     * The exitProgram method displays a confirmation popup when the user attempts to exit the program,
     * if the user clicks "OK", the program closes, otherwise the program remains open.
     */
    public static void exitProgram() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Exit");
        confirmAlert.setHeaderText("Would you like to close the program?");
        confirmAlert.setContentText("Are you sure?");
        confirmAlert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.orElseThrow() == buttonTypeOK){
            System.exit(0);
        }
    }
}
