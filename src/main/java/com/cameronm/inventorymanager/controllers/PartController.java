package com.cameronm.inventorymanager.controllers;

import com.cameronm.inventorymanager.models.InHouse;
import com.cameronm.inventorymanager.models.Inventory;
import com.cameronm.inventorymanager.models.Outsourced;
import com.cameronm.inventorymanager.models.Part;
import com.cameronm.inventorymanager.utilities.AutoIncrementingPartId;
import com.cameronm.inventorymanager.utilities.InventoryValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * The PartController class controls the logic for the Add/Modify Part window.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-02
 */
public class PartController {

    /**
     * The label displayed specifying if the "add" or "modify" part button was pressed
     */
    @FXML
    private Label addOrModifyLabel;

    /**
     * the toggle for selecting between In-House or Outsourced parts
     */
    @FXML
    private ToggleGroup partTypeToggle;

    /**
     * the radio button for In-house parts
     */
    @FXML
    private RadioButton partInHouseRadioButton;

    /**
     * the radio button for Outsourced parts
     */
    @FXML
    private RadioButton partOutsourcedRadioButton;

    /**
     * the text field for part ID input
     */
    @FXML
    private TextField partIdTextBox;

    /**
     * the text field for part name input
     */
    @FXML
    private TextField partNameTextBox;

    /**
     * the text field for part inventory quantity input
     */
    @FXML
    private TextField partStockTextBox;

    /**
     * the text field for part price input
     */
    @FXML
    private TextField partPriceTextBox;

    /**
     * the text field for part maximum quantity input
     */
    @FXML
    private TextField partMaxQuantityTextBox;

    /**
     * the text field for part minimum quantity input
     */
    @FXML
    private TextField partMinQuantityTextBox;

    /**
     * the text field for part provider input
     */
    @FXML
    private TextField partProviderTextBox;

    /**
     * the label displaying "Machine ID" when In-house is selected or "Company Name" when Outsourced is selected
     */
    @FXML
    private Label partProviderLabel;

    /**
     * the save button
     */
    @FXML
    private Button saveButton;

    /**
     * the cancel button
     */
    @FXML
    private Button cancelButton;

    /**
     * The constructor for the PartController Class
     */
    public PartController() {}

    /**
     * The partTypeInHouseSelected method changes the label of the provider text field to the in-house provider type.
     */
    @FXML
    void partTypeInHouseSelected() {
        String inHouseProviderType = "Machine ID";
        partProviderLabel.setText(inHouseProviderType);
    }

    /**
     * The partTypeOutsourceSelected method changes the label of the provider text field to the outsourced provider type.
     */
    @FXML
    void partTypeOutsourcedSelected() {
        String outsourcedProviderType = "Company Name";
        partProviderLabel.setText(outsourcedProviderType);
    }

    /**
     * The addOrModifyPart method initializes the values in each text field if a part was passed in,
     * a null value is passed in if the add part button was pressed.
     *
     * @param part This is the part that is selected from the part table
     *             and is being modified or null if add part button is used
     */
    void addOrModifyPart(Part part) {
        if (part != null) {
            addOrModifyLabel.setText("Modify Part");
            partIdTextBox.setText(String.valueOf(part.getId()));
            partNameTextBox.setText(part.getName());
            partStockTextBox.setText(String.valueOf(part.getStock()));
            partPriceTextBox.setText(String.format("%.2f", part.getPrice()));
            partMaxQuantityTextBox.setText(String.valueOf(part.getMax()));
            partMinQuantityTextBox.setText(String.valueOf(part.getMin()));
            if (part instanceof Outsourced) {
                partTypeToggle.selectToggle(partOutsourcedRadioButton);
                partTypeOutsourcedSelected();
                partProviderTextBox.setText(((Outsourced) part).getCompanyName());
            } else {
                partProviderTextBox.setText(String.valueOf(((InHouse) part).getMachineId()));
            }
        }
        saveButton.setOnAction(event -> {
            savePart(part);
        });
    }

    /**
     * The savePart method uses the InventoryValidator class to check all value of the text fields to make
     * sure they are valid, then saves a brand-new part if a null value was passed in or a part is being modified
     * and changed to a different type. In that case, the values are copied and a new part is created.
     *
     * @param part this is the part to be modified, if a null value a passed in, a new part is created
     */
    void savePart(Part part) {
        Alert alert;
        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");

        int id = -1;
        if (part != null) {
            id = part.getId();
        }
        String name = null;
        int stock = -1;
        double price = -1;
        int max = -1;
        int min = -1;
        String provider = null;

        try {
            // InventoryValidator class is used to verify is all values are valid then returns a new part
            if (InventoryValidator.isValidName(partNameTextBox.getText())) {
                name = partNameTextBox.getText();
            }
            if (InventoryValidator.isValidStock(partStockTextBox.getText())) {
                stock = Integer.parseInt(partStockTextBox.getText());
            }
            if (InventoryValidator.isValidPrice(partPriceTextBox.getText())) {
                price = Double.parseDouble(partPriceTextBox.getText());
            }
            if (InventoryValidator.isValidMaxQuantity(partMaxQuantityTextBox.getText())) {
                max = Integer.parseInt(partMaxQuantityTextBox.getText());
            }
            if (InventoryValidator.isValidMinQuantity(partMinQuantityTextBox.getText())) {
                min = Integer.parseInt(partMinQuantityTextBox.getText());
            }
            InventoryValidator.isValidMinMaxStockRange(min, max, stock);
            if (InventoryValidator.isValidProvider(partProviderTextBox.getText(),
                    partOutsourcedRadioButton.isSelected())) {
                provider = partProviderTextBox.getText();
            }
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Save");
            alert.setHeaderText("Would you like to save this part?");
            alert.setContentText("Are you sure?");
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElseThrow() == buttonTypeOK) {
                /*
                    this checks if the part is being modified is a changing to a different type, if not, it sets the
                    values and a new part is not created, otherwise the part is deleted and a new part gets created.
                 */
                boolean partModified = false;
                if (part != null) {
                    if (part instanceof InHouse) {
                        if (!partInHouseRadioButton.isSelected()) {
                            Inventory.deletePart(part);
                        } else {
                            assert provider != null;
                            ((InHouse) part).setMachineId(Integer.parseInt(provider));
                            partModified = true;
                        }
                    } else if (part instanceof Outsourced) {
                        if (!partOutsourcedRadioButton.isSelected()) {
                            Inventory.deletePart(part);
                        } else {
                            ((Outsourced) part).setCompanyName(provider);
                            partModified = true;
                        }
                    }
                }
                if (partModified) {
                    part.setId(id);
                    part.setName(name);
                    part.setStock(stock);
                    part.setPrice(price);
                    part.setMax(max);
                    part.setMin(min);
                } else {
                    if (part != null) {
                        id = part.getId();
                    } else {
                        id = AutoIncrementingPartId.getNextID();
                    }
                    if (partInHouseRadioButton.isSelected()) {
                        assert provider != null;
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, Integer.parseInt(provider)));
                    } else if (partOutsourcedRadioButton.isSelected()) {
                        Inventory.addPart(new Outsourced(id, name, price, stock, min, max, provider));
                    }
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