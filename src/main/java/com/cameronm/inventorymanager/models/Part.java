package com.cameronm.inventorymanager.models;

/**
 * The Part class represents a part.
 *
 * @author Cameron M
 */
public abstract class Part {

    /**
     * the id of the part
     */
    private int id;

    /**
     * the name of the part
     */
    private String name;

    /**
     * the price of the part
     */
    private double price;

    /**
     * the stock quantity of the part
     */
    private int stock;

    /**
     * the minimum quantity of the part
     */
    private int min;

    /**
     * the maximum quantity of the part
     */
    private int max;

    /**
     * The constructor for the Part class
     *
     * @param id the id to set
     * @param name the name to set
     * @param price the price to set
     * @param stock the stock to set
     * @param min the min to set
     * @param max the max to set
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * the getID method returns the id of the part
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * the setID method sets the id of the part
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * the getName method returns the name of the part
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * the setName method sets the name of the part
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * the getPrice method returns the price of the part
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * the setPrice method sets the price of the part
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * the getStock method returns the inventory quantity of the part
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * the setStock method sets the inventory quantity of the part
     *
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * the getMin method returns the minimum quantity of the part
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * the setMin method sets the minimum quantity of the part
     *
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * the getMax method returns the maximum quantity of the part
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * the setMax method sets the maximum quantity of the part
     *
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
}