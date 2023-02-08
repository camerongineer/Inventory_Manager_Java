package com.cameronm.inventorymanager.controllers;

import com.cameronm.inventorymanager.models.*;
import com.cameronm.inventorymanager.utilities.AutoIncrementingProductId;
import com.cameronm.inventorymanager.utilities.InventoryValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * The ProductController class controls the logic for the Add/Modify Product window.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-03
 */
public class ProductController {

    /**
     * the add button to add available parts to product
     */
    @FXML
    private Button addAvailablePartButton;

    /**
     * The label displayed specifying if the "add" or "modify" product button was pressed
     */
    @FXML
    private Label addOrModifyLabel;

    /**
     * the table displaying each associated part
     */
    @FXML
    private TableView<Part> associatedPartTable;

    /**
     * the table displaying each available part
     */
    @FXML
    private TableView<Part> availablePartTable;

    /**
     * the cancel button
     */
    @FXML
    private Button cancelButton;

    /**
     * the column displaying the associated part ID in the associated part table
     */
    @FXML
    private TableColumn<Part, Integer> idAssociatedPartTable;

    /**
     * the column displaying the available part ID in the available part table
     */
    @FXML
    private TableColumn<Part, Integer> idAvailablePartTable;

    /**
     * the column displaying the associated part inventory quantity in the associated part table
     */
    @FXML
    private TableColumn<Part, Integer> inventoryAssociatedPartTable;

    /**
     * the column displaying the available part inventory quantity in the available part table
     */
    @FXML
    private TableColumn<Part, Integer> inventoryAvailablePartTable;

    /**
     * the column displaying the associated part name in the associated part table
     */
    @FXML
    private TableColumn<Part, String> nameAssociatedPartTable;

    /**
     * the column displaying the available part name in the available part table
     */
    @FXML
    private TableColumn<Part, String> nameAvailablePartTable;

    /**
     * the column displaying the associated part price in the associated part table
     */
    @FXML
    private TableColumn<Part, Double> priceAssociatedPartTable;

    /**
     * the column displaying the available part price in the available part table
     */
    @FXML
    private TableColumn<Part, Double> priceAvailablePartTable;

    /**
     * the text field for product ID input
     */
    @FXML
    private TextField productIdTextBox;

    /**
     * the text field for product maximum quantity input
     */
    @FXML
    private TextField productMaxQuantityTextBox;

    /**
     * the text field for product minimum quantity input
     */
    @FXML
    private TextField productMinQuantityTextBox;

    /**
     * the text field for product name input
     */
    @FXML
    private TextField productNameTextBox;

    /**
     * the text field for product price input
     */
    @FXML
    private TextField productPriceTextBox;

    /**
     * the text field for product inventory quantity input
     */
    @FXML
    private TextField productStockTextBox;

    /**
     * the button for removing an associated part
     */
    @FXML
    private Button removeAssociatedPartButton;

    /**
     * the save button
     */
    @FXML
    private Button saveButton;

    /**
     * the text field for searching for parts in the available part table
     */
    @FXML
    private TextField searchAvailablePartTextField;

    /**
     * the label displaying if a part is not found in the part table
     */
    @FXML
    private Label availablePartNotFoundLabel;

    /**
     * the constructor for the ProductController Class
     */
    public ProductController() {}

    /**
     * The partSearchTextFieldTyped method passes arguments to the MainController.partSearchTextField method.
     */
    @FXML
    void searchAvailablePartTextFieldTyped() {
        MainController.partSearchTextField(searchAvailablePartTextField, availablePartTable, idAvailablePartTable,
                    nameAvailablePartTable, inventoryAvailablePartTable,
                priceAvailablePartTable, availablePartNotFoundLabel);
    }

    /**
     * The addOrModifyProduct method initializes the values in each text field if a product was passed in,
     * a null value is passed in if the add product button was pressed.
     *
     * @param product This is the product that is selected from the product table
     *                and is being modified or null if add product button is used
     */
    void addOrModifyProduct (Product product) {
        ObservableList<Part> associatedPartsCopy;
        if (product == null) {
            associatedPartsCopy = FXCollections.observableArrayList();
        } else {
            associatedPartsCopy = FXCollections.observableArrayList(product.getAllAssociatedParts());
        }
        MainController.refreshItemTable(Inventory.getAllParts(), availablePartTable,
                idAvailablePartTable, nameAvailablePartTable, inventoryAvailablePartTable, priceAvailablePartTable);
        if (product != null) {
            addOrModifyLabel.setText("Modify Product");
            productIdTextBox.setText(String.valueOf(product.getId()));
            productNameTextBox.setText(product.getName());
            productStockTextBox.setText(String.valueOf(product.getStock()));
            productPriceTextBox.setText(String.format("%.2f", product.getPrice()));
            productMaxQuantityTextBox.setText(String.valueOf(product.getMax()));
            productMinQuantityTextBox.setText(String.valueOf(product.getMin()));
            MainController.refreshItemTable(product.getAllAssociatedParts(), associatedPartTable,
                    idAssociatedPartTable, nameAssociatedPartTable, inventoryAssociatedPartTable, priceAssociatedPartTable);
        }
        removeAssociatedPartButton.setOnAction(event -> {
            if (associatedPartTable.getSelectionModel().getSelectedItem() != null) {
                Part partToDelete = associatedPartTable.getSelectionModel().getSelectedItem();
                ButtonType buttonTypeOK = new ButtonType("OK");
                ButtonType buttonTypeCancel = new ButtonType("Cancel");
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Delete part");
                confirmAlert.setHeaderText(
                        "Would you like to delete associated part: { " + partToDelete.getName() + " }\"?");
                confirmAlert.setContentText("Are you sure?");
                confirmAlert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

                Optional<ButtonType> result = confirmAlert.showAndWait();

                if (result.orElseThrow() == buttonTypeOK) {
                    associatedPartsCopy.remove(partToDelete);
                    MainController.refreshItemTable(associatedPartsCopy, associatedPartTable,
                            idAssociatedPartTable, nameAssociatedPartTable,
                            inventoryAssociatedPartTable, priceAssociatedPartTable);
                }
            }
        });
        saveButton.setOnAction(event -> {
            saveProduct(product, associatedPartsCopy);
        });
        addAvailablePartButton.setOnAction(event -> {
            Part partToBeAdded = availablePartTable.getSelectionModel().getSelectedItem();
            if (partToBeAdded != null) {
                associatedPartsCopy.add(partToBeAdded);
                MainController.refreshItemTable(associatedPartsCopy, associatedPartTable,
                        idAssociatedPartTable, nameAssociatedPartTable,
                        inventoryAssociatedPartTable, priceAssociatedPartTable);
            }
        });
    }

    /**
     * The saveProduct method uses the InventoryValidator class to check all value of the text fields to make
     * sure they are valid, then saves a brand-new product if a null value was passed in or a product is being modified
     *
     * @param product this is the product to be modified, if a null value a passed in, a new product is created
     * @param associatedPartsCopy the associatedParts that are copied for the purpose of saving only if the changes
     *                            are confirmed by the user
     */
    void saveProduct(Product product, ObservableList<Part> associatedPartsCopy) {
        Alert alert;
        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");

        int id = -1;
        if (product != null) {
            id = product.getId();
        }
        String name = null;
        int stock = -1;
        double price = -1;
        int max = -1;
        int min = -1;

        try {
            // InventoryValidator class is used to verify is all values are valid then returns a new product
            if (InventoryValidator.isValidName(productNameTextBox.getText())) {
                name = productNameTextBox.getText();
            }
            if (InventoryValidator.isValidStock(productStockTextBox.getText())) {
                stock = Integer.parseInt(productStockTextBox.getText());
            }
            if (InventoryValidator.isValidPrice(productPriceTextBox.getText())) {
                price = Double.parseDouble(productPriceTextBox.getText());
            }
            if (InventoryValidator.isValidMaxQuantity(productMaxQuantityTextBox.getText())) {
                max = Integer.parseInt(productMaxQuantityTextBox.getText());
            }
            if (InventoryValidator.isValidMinQuantity(productMinQuantityTextBox.getText())) {
                min = Integer.parseInt(productMinQuantityTextBox.getText());
            }
            InventoryValidator.isValidMinMaxStockRange(min, max, stock);
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Save");
            alert.setHeaderText("Would you like to save this product?");
            alert.setContentText("Are you sure?");
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElseThrow() == buttonTypeOK) {
                if (product == null) {
                    Inventory.addProduct(new Product(associatedPartsCopy, AutoIncrementingProductId.getNextID(),
                            name, price, stock, min, max));
                } else {
                    product.setId(id);
                    product.setName(name);
                    product.setStock(stock);
                    product.setPrice(price);
                    product.setMax(max);
                    product.setMin(min);
                    product.getAllAssociatedParts().clear();
                    associatedPartsCopy.forEach(product::addAssociatedPart);
                }
                alert.close();
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Entry");
            alert.setHeaderText(illegalArgumentException.getMessage());
            alert.setContentText("Please recheck the values");
            alert.getButtonTypes().setAll(buttonTypeOK);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElseThrow() == buttonTypeOK){
                alert.close();
            }
        }
    }

    /**
     * The initialize method initializes the cancel button.
     */
    @FXML
    private void initialize() {
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });
    }
}
