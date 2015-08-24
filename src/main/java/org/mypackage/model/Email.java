package org.mypackage.model;

/**
 *
 * @author dpa
 */

public class Email {

    public static enum Category {

        PERSONAL((byte) 1),
        WORK((byte) 2);

        private final byte byteValue;

        private Category(byte value) {
            this.byteValue = value;
        }

        public byte getByteValue() {
            return this.byteValue;
        }

        public static Category forValue(byte value) {
            for (Category c : Category.values()) {
                if (value == c.getByteValue()) {
                    return c;
                }
            }

            throw new IllegalArgumentException();
        }
    }

    private int id;

    private String address;

    private Category category;

    private int fContactId;

    public Email() {
    }

    public Email(int id, String address, Category category, int contactId) {
        this.id = id;
        this.address = address;
        this.category = category;
        this.fContactId = contactId;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param Id the id to set
     */
    public void setId(int Id) {
        this.id = Id;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the fContactId
     */
    public int getfContactId() {
        return fContactId;
    }

    /**
     * @param fContactId the fContactId to set
     */
    public void setfContactId(int fContactId) {
        this.fContactId = fContactId;
    }
}
