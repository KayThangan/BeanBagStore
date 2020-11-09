package beanbags;

import java.io.Serializable;

/**
 * BeanBag class containing all the attributes
 * and behaviours of a bean bag.
 */
public class BeanBag implements Serializable {
    private int quantity;
    private String manufacturer;
    private String name;
    private int price;
    private String id;
    private short year;
    private byte month;
    private String information;

    private int reservationNumber;

    /**
     * Constructs an instance of the object containing num,
     * manufacturer, name, id, year and month arguments.
     *
     * @param num               number of bean bags added
     * @param manufacturer      bean bag manufacturer
     * @param name              bean bag name
     * @param id                ID of bean bag
     * @param year              year of manufacture
     * @param month             month of manufacture
     */
    public BeanBag(int num, String manufacturer, String name, String id, short year, byte month) {
        this.quantity = num;
        this.manufacturer = manufacturer;
        this.name = name;
        this.id = id;
        this.year = year;
        this.month = month;
    }

    /**
     * Constructs an instance of the object containing num,
     * manufacturer, name, id, year, month and information arguments.
     *
     * @param num               number of bean bags added
     * @param manufacturer      bean bag manufacturer
     * @param name              bean bag name
     * @param id                ID of bean bag
     * @param year              year of manufacture
     * @param month             month of manufacture
     * @param information       free text detailing bean bag information
     */
    public BeanBag(int num, String manufacturer, String name, String id, short year, byte month, String information) {
        this.quantity = num;
        this.manufacturer = manufacturer;
        this.name = name;
        this.id = id;
        this.year = year;
        this.month = month;
        this.information = information;
    }

    /**
     * Constructs an instance of the object containing num,
     * id, price and reservationNumber arguments.
     *
     * @param num               number of bean bags added
     * @param id                ID of bean bag
     * @param price             bean bag price in pence
     * @param reservationNumber unique reservation number used to find
     *                              beanbag(s) to be sold
     */
    BeanBag(int num, String id, int price, int reservationNumber) {
        this.quantity = num;
        this.id = id;
        this.price = price;
        this.reservationNumber = reservationNumber;
    }

    /**
     * Constructs an instance of the object containing num,
     * id and price arguments.
     *
     * @param num               number of bean bags added
     * @param id                ID of bean bag
     * @param price             bean bag price in pence
     */
    BeanBag(int num, String id, int price) {
        this.quantity = num;
        this.id = id;
        this.price = price;
    }

    /**
     * Method sets the number of bean bags.
     *
     * @param num               number of bean bags added
     */
    public void setQuantity(int num) {
        this.quantity = num;
    }

    /**
     * Method gets the number of bean bags.
     *
     * @return               number of bean bags added
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Method gets the manufacturer of the bean bags.
     *
     * @return              bean bag manufacturer
     */
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * Method gets the name of the bean bags.
     *
     * @return              bean bag name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method sets the price of the bean bags.
     *
     * @param price         bean bag price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Method gets the price of the bean bags.
     *
     * @return              bean bag price in pence
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Method sets the id of the bean bags.
     *
     * @param id            ID of bean bags
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method gets the id of the bean bags.
     *
     * @return              ID of bean bags
     */
    public String getId() {
        return this.id;
    }

    /**
     * Method sets the year of the bean bags.
     *
     * @param year          year of manufacture
     */
    public void setYear(short year) {
        this.year = year;
    }

    /**
     * Method sets the month of the bean bags.
     *
     * @param month         month of manufacture
     */
    public void setMonth(byte month) {
        this.month = month;
    }

    /**
     * Method gets the information of the bean bags.
     *
     * @return              free text detailing bean bag information
     */
    public String getInformation() {
        return this.information;
    }

    /**
     * Method gets the reservation number of the bean bags.
     *
     * @return              customers' reservation number
     */
    public int getReservationNumber() {
        return this.reservationNumber;
    }
}
