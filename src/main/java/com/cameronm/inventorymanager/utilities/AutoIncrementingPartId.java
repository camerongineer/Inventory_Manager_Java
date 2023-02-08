package com.cameronm.inventorymanager.utilities;

import java.util.HashSet;

/**
 * The AutoIncrementingPartId class keeps track the next ID to assign a part.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-01
 */
public class AutoIncrementingPartId {

    /**
     * a set that that holds previously used part ID numbers
     */
    private static final HashSet<Integer> previousPartIdSet = new HashSet<>();

    /**
     * the next part ID
     */
    private static int nextID = 1;

    /**
     * The constructor for the AutoIncrementingPartId Class
     */
    public AutoIncrementingPartId() {}

    /**
     * The getNextID method first check if the next part ID in the queue has been assigned to a part previously, if it
     * has it will keep incrementing until it finds a unique id number and then return the number to be assigned to a part.
     *
     * @return the next valid part ID number
     */
    public static int getNextID() {
        int partId = nextID++;
        while (previousPartIdSet.contains(partId)) {
            partId = nextID++;
        }
        previousPartIdSet.add(partId);
        return partId;
    }
}
