package com.cameronm.inventorymanager.models;

/**
 * The InHouse class extends the Part class and represents a part that was manufactured in-house.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-01
 */
public class InHouse extends Part {

    /**
     * the Machine ID of the part
     */
    private int machineId;

    /**
     * The constructor for the InHouse class
     *
     * @param id the id to set
     * @param name the name to set
     * @param price the price to set
     * @param stock the stock to set
     * @param min the min to set
     * @param max the max to set
     * @param machineId the machineId to set
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * The getMachineId method returns the machine ID number of the part
     *
     * @return the machine ID number
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * The setMachineId method sets machine ID number of the part
     *
     * @param machineId the machine ID number
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
