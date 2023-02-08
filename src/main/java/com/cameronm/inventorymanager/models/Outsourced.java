package com.cameronm.inventorymanager.models;

/**
 * The Outsourced class extends the Part class and represents a part that was outsourced from another company.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-01
 */
public class Outsourced extends Part {

    /**
     * the name of the company from which the part was acquired
     */
    private String companyName;

     /**
      * The constructor for the Outsourced class
      *
      * @param id the id to set
      * @param name the name to set
      * @param price the price to set
      * @param stock the stock to set
      * @param min the min to set
      * @param max the max to set
      * @param companyName the companyName to set
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * The getCompanyName method returns the name of the company.
     *
     * @return returns the name of the company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * The setCompanyName method sets the name of the company.
     *
     * @param companyName the name of the company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
