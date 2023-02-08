package com.cameronm.inventorymanager.utilities;

/**
 * The InventoryValidator class is used to check all the values at that are entered
 * into various text fields in the application to make sure they are valid.
 *
 * @author Cameron M
 * @version 1.0
 * @since 2023-02-02
 */
public class InventoryValidator {

    /**
     * The constructor for the InventoryValidator Class
     */
    public InventoryValidator() {}

    /**
     * The isValidName method checks if name value is blank.
     *
     * @param name String value that was entered into the Name text field
     * @return returns true if name is a valid string otherwise throws IllegalArgumentException
     */
    public static boolean isValidName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        return true;
    }

    /**
     * The isValidStock method checks if stock value is a String representing a positive integer.
     *
     * @param stock String value that was entered into the Inv text field
     * @return returns true if stock is a valid positive integer otherwise throws IllegalArgumentException
     */
    public static boolean isValidStock(String stock) {
        if (!stock.trim().matches("^[+]?[1-9][0-9]*$")) {
            throw new IllegalArgumentException("Invalid inventory quantity");
        }
        return true;
    }

    /**
     * The isValidPrice method checks if price value is a String representing a positive double.
     *
     * @param price String value that was entered into the Price text field
     * @return returns true if price is a valid positive double otherwise throws IllegalArgumentException
     */
    public static boolean isValidPrice(String price) {
        if (!price.trim().matches("^[+]?[0-9]+(\\.[0-9]+)?$")) {
            throw new IllegalArgumentException("Invalid price");
        }
        return true;
    }

    /**
     * The isValidMaxQuantity method checks if max value is a String representing a positive integer.
     *
     * @param max String value that was entered into the Max text field
     * @return returns true if max is a valid positive integer otherwise throws IllegalArgumentException
     */
    public static boolean isValidMaxQuantity(String max) {
        if (!max.trim().matches("^[+]?[1-9][0-9]*$")) {
            throw new IllegalArgumentException("Invalid maximum stock quantity");
        }
        return true;
    }

    /**
     * The isValidMinQuantity method checks if min value is a String representing a positive integer.
     *
     * @param min String value that was entered into the Min text field
     * @return returns true if min is a valid positive integer otherwise throws IllegalArgumentException
     */
    public static boolean isValidMinQuantity(String min) {
        if (!min.trim().matches("^[+]?[1-9][0-9]*$")) {
            throw new IllegalArgumentException("Invalid minimum stock quantity");
        }
        return true;
    }

    /**
     * The isValidMinMaxStockRange method first checks of max value is less than min value, then checks if
     * stock value is equal between both min and max values or equal to one of them. An IllegalArgumentException
     * is thrown if these values are not in their proper ranges.
     *
     * @param min the minimum value entered into the Min text field
     * @param max the maximum value entered into the Max text field
     * @param stock the stock value entered into the Inv text field
     */
    public static void isValidMinMaxStockRange(int min, int max, int stock) {
        if (max < min) {
            throw new IllegalArgumentException("Minimum quantity must be equal to or less than maximum quantity");
        }
        if (!(min <= stock) || !(stock <= max)) {
            throw new IllegalArgumentException("Inventory quantity must be equal to or between minimum and maximum");
        }
    }

    /**
     * The isValidProvider method checks if the provider value is a String representing a positive integer if
     * isOutsourced is false, otherwise any non-blank value is valid if isOutsourced is true. The provider value
     * represents a Machine ID for In-house parts or Company Name for Outsourced parts.
     *
     * @param provider represents a Machine ID if the In-house radio button is selected, otherwise it
     *                 represents a Company Name is the Outsourced radio button is selected
     * @param isOutsourced represents that the Outsource radio button is selected
     * @return returns true if provider value is valid based on the type of part being referenced
     */
    public static boolean isValidProvider(String provider, boolean isOutsourced) {
        if (provider.isBlank() || (!isOutsourced && !provider.matches("^[+]?[1-9][0-9]*$"))) {
            throw new IllegalArgumentException("Invalid " +
                    (!isOutsourced ? "machine ID, must be an integer" : "company name"));
        }
        return true;
    }
}
