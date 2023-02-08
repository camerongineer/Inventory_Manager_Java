package com.cameronm.inventorymanager.models;

import javafx.collections.ObservableList;

/**
 * The Product class represents a product.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-01
 */
public class Product {

    /**
     * the parts that are associated with the product
     */
    private ObservableList<Part> associatedParts;

    /**
     * the ID number of the product
     */
    private int id;

    /**
     * the name of the product
     */
    private String name;

    /**
     * the price of the product
     */
    private double price;

    /**
     * the quantity in stock of the product
     */
    private int stock;

    /**
     * the minimum amount of product to keep in inventory
     */
    private int min;

    /**
     * the maximum amount of product to keep in inventory
     */
    private int max;

    /**
     * The constructor for the Product class
     *
     * @param associatedParts the parts that are associated with the product
     * @param id the ID number of the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the quantity in stock of the product
     * @param min the minimum amount of product to keep in inventory
     * @param max the maximum amount of product to keep in inventory
     */
    public Product(ObservableList<Part> associatedParts, int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = associatedParts;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * The getAllAssociatedParts method returns a reference
     * to the ObservableList containing the associated parts of the product.
     *
     * @return reference to ObservableList containing associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * The deleteAssociatedPart method return a boolean value based on if an associated part was successfully
     * deleted from the ObservableList containing associated parts of the product.
     *
     * @param selectedAssociatedPart the part that will be deleted
     * @return boolean value based on if the part was successfully deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * The addAssociatedPart method adds a part to the ObservableList of associated parts.
     *
     * @param selectedAssociatedPart the part that will be added to the ObservableList of associated parts
     */
    public void addAssociatedPart(Part selectedAssociatedPart) {
        this.associatedParts.add(selectedAssociatedPart);
    }

    /**
     * The getId method returns the ID number of the product.
     *
     * @return product ID number
     */
    public int getId() {
        return id;
    }

    /**
     * The setId method sets the ID number of the product.
     *
     * @param id product ID number
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getId method returns the name of the product.
     *
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * The setName method sets the name of the product.
     *
     * @param name the name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getPrice method returns the price of the product.
     *
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * The setPrice method sets the price of the product.
     *
     * @param price the price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * The getStock method returns the quantity of the product stock in inventory.
     *
     * @return the quantity of product stock in inventory
     */
    public int getStock() {
        return stock;
    }

    /**
     * The setStock method sets the quantity of the product stock in inventory.
     *
     * @param stock the quantity of product stock in inventory
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * The getMin method returns the minimum quantity of product stock to be available in inventory.
     *
     * @return the minimum quantity of stock in inventory
     */
    public int getMin() {
        return min;
    }

    /**
     * The setMin method sets the minimum quantity of product stock to be available in inventory.
     *
     * @param min the minimum quantity of stock in inventory
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * The getMax method returns the minimum quantity of product stock to be available in inventory.
     *
     * @return the maximum quantity of stock in inventory
     */
    public int getMax() {
        return max;
    }

    /**
     * The setMax method sets the minimum quantity of product stock to be available in inventory.
     *
     * @param max the maximum quantity of stock in inventory
     */
    public void setMax(int max) {
        this.max = max;
    }
}
