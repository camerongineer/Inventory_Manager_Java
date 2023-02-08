package com.cameronm.inventorymanager.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class represents the inventory of a company.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-01
 */
public class Inventory {

    /**
     * The parts that are currently in inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * The products that are currently in inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * The constructor for the Inventory Class
     */
    public Inventory() {}

    /**
     * The addPart method adds a part to allParts ObservableList representing parts inventory.
     *
     * @param newPart the part to be added to inventory
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * The addProduct method adds a product to allProducts ObservableList representing product inventory.
     *
     * @param newProduct the product to be added to inventory
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * The lookupPart method looks a part up by its ID number
     *
     * @param partId The ID number of the part to being looked up
     * @return Returns the part if the ID number matches
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * The lookupPart method looks a part up by its name
     *
     * @param partName The name of the part to being looked up
     * @return Returns the part if its name matches
     */
    public static Part lookupPart(String partName) {
        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                return part;
            }
        }
        return null;
    }

    /**
     * The lookupProduct method looks a product up by its ID number
     *
     * @param productId The ID of the product to being looked up
     * @return Returns the product if its ID number matches
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * The lookupProduct method looks a product up by its name
     *
     * @param productName The name of the product to being looked up
     * @return Returns the product if its name matches
     */
    public static Product lookupProduct(String productName) {
        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    /**
     * The updatePart method replaces the part at the provided index with a new part.
     *
     * @param index The index of the part in the allParts ObservableList being replaced
     * @param selectedPart The part that is replacing the part at the given index
     */
    public static void updatePart(int index, Part selectedPart) {
        if (allParts.get(index) != null) {
            allParts.set(index, selectedPart);
        }
    }

    /**
     * The updateProduct method replaces the product at the provided index with a new product.
     *
     * @param index The index of the products in the allProducts ObservableList being replaced
     * @param newProduct The product that is replacing the product at the given index
     */
    public static void updateProduct(int index, Product newProduct) {
        if (allProducts.get(index) != null) {
            allProducts.set(index, newProduct);
        }
    }

    /**
     * The deletePart method deletes a part from the allParts ObservableList.
     *
     * @param selectedPart the part that is to be deleted
     * @return returns true if the part is successfully removed
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * The deleteProduct method deletes a product from the allProducts ObservableList.
     *
     * @param selectedProduct the product that is to be deleted
     * @return returns true if the product is successfully removed
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            return false;
        }
        return allProducts.remove(selectedProduct);
    }

    /**
     * The getAllParts returns the allParts ObservableList.
     *
     * @return returns the allParts ObservableList
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * The getAllProducts returns the allProducts ObservableList.
     *
     * @return returns the allProducts ObservableList
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
