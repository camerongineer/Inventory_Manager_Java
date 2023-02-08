package com.cameronm.inventorymanager.utilities;

import java.util.HashSet;

/**
 * The AutoIncrementingProductId class keeps track the next ID to assign a product.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-01
 */
public class AutoIncrementingProductId {

    /**
     * a set that that holds previously used product ID numbers
     */
    private static final HashSet<Integer> previousProductIdSet = new HashSet<>();

    /**
     * the next product ID
     */
    private static int nextID = 1;

    /**
     * The constructor for the AutoIncrementingProductId Class
     */
    public AutoIncrementingProductId() {}

    /**
     * The getNextID method first check if the next product ID in the queue has been assigned to a product previously, if it
     * has it will keep incrementing until it finds a unique id number and then return the number to be assigned to a product.
     *
     * @return the next valid part ID number
     */
    public static int getNextID() {
        int productId = nextID++;
        while (previousProductIdSet.contains(productId)) {
            productId = nextID++;
        }
        previousProductIdSet.add(productId);
        return productId;
    }
}
