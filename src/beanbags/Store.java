package beanbags;

import java.io.*;

/**
 * Store is a fully compiling, with fully-functioning implementor of the
 * BeanBagStore interface.
 */
public class Store implements BeanBagStore {
    //declaring a list for each bean bags state.
    private ObjectArrayList stockList; //in-store bean bags
    private ObjectArrayList reserveList; //reserved bean bags
    private ObjectArrayList soldList; //sold bean bags

    /**
     * Constructs an instance of the object with no message.
     */
    public Store() {
        this.stockList = new ObjectArrayList();
        this.reserveList = new ObjectArrayList();
        this.soldList = new ObjectArrayList();
    }

    /**
     * Method checks weather the id represent positive hexadecimal number.
     *
     * @param id                ID of bean bag
     * @return                  boolean representing if the id is valid
     */
    private boolean isIDValid(String id) {
        char[] hexValues = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int i = 0; i < id.length(); i++) {
            for (int x = 0; x < hexValues.length; x++) {
                //checking if the id is negative id
                if (i == 0 && x >= 8) {
                    return false;
                }
                //checking if the characters match
                if (id.charAt(i) == hexValues[x]) {
                    break;
                }
                if (x == hexValues.length - 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method handles the add bean bags exception when adding bean bags to the
     * store with the arguments as bean bag detail.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num               number of bean bags added
     * @param id                ID of bean bag
     * @param month             month of manufacture
     * @throws IllegalNumberOfBeanBagsAddedException   if the number to be added
     *                           is less than 1
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     * @throws InvalidMonthException    if the month is not in the range 1 to 12
     */
    private void addBeanBagsException(int num, String id, byte month) throws IllegalNumberOfBeanBagsAddedException, IllegalIDException, InvalidMonthException {
        if (num < 1) {
            throw new IllegalNumberOfBeanBagsAddedException("Illegal Number Of" +
                    " Bean Bags Added: The number of bean bags added must be " +
                    "greater than 0.");
        }
        beanBagsIllegalIDException(id);
        if (month < 1 || month > 12) {
            throw new InvalidMonthException("Invalid Month: The month has to " +
                    "between 1 and 12.");
        }
    }

    /**
     * Method handles any bean bags illegal id exception.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id                ID of bean bag
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
    private void beanBagsIllegalIDException(String id) throws IllegalIDException {
        if (id.length() != 8 || !isIDValid(id)) {
            throw new IllegalIDException("Illegal ID: The id must be 8 " +
                    "characters positive number.");
        }
    }

    /**
     * Method handles any bean bags' id that is not found in the stock.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param counter                element's counter of the list
     * @param list                   list from a the Store
     * @throws BeanBagIDNotRecognisedException if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     */
    private void beanBagIDNotRecognisedException(int counter, ObjectArrayList list) throws BeanBagIDNotRecognisedException {
        if (counter == list.size() - 1) {
            throw new BeanBagIDNotRecognisedException("Bean Bag ID Not " +
                    "Recognised: The id doesn't exist in the list.");
        }
    }

    /**
     * Method handles any bean bags' reservation number that is not found in the stock.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param counter                element's counter of the reserveList
     * @throws ReservationNumberNotRecognisedException if the reservation number
     *                          does not match a current reservation in the system
     */
    private void beanBagReservationNumberNotRecognisedException(int counter) throws ReservationNumberNotRecognisedException {
        if (counter == this.reserveList.size() - 1) {
            throw new ReservationNumberNotRecognisedException("Reservation " +
                    "Number Not Recognised: The reservation number doesn't exist in the list.");
        }
    }

    /**
     * Method generate random 9 digit reservation number for customers,
     * when they reserve bean bags.
     *
     * @return           unique reservation number used to find
     *                      beanbag(s) to be sold
     */
    private int generateReservationNumber() {
        //generating a 9 digit random number
        int reservationNumber = 100000000 + (int) (Math.random() * 899999999);
        if (this.reserveList.size() != 0) {
            while (true) {
                for (int i = 0; i < this.reserveList.size(); i++) {
                    //checking if the generated number doesn't already exist in the reserveList
                    if (((BeanBag) this.reserveList.get(i)).getReservationNumber() == reservationNumber) {
                        //generating a 9 digit random number
                        reservationNumber = 100000000 + (int) (Math.random() * 899999999);
                        break;
                    }
                    if (i == this.reserveList.size() - 1) {
                        return reservationNumber;
                    }
                }
            }
        }
        return reservationNumber;
    }

    /**
     * Access method for the number of BeanBags stocked by this BeanBagStore
     * (total of reserved and unreserved stock).
     *
     * @param list1         a smaller list that is used to compare
     * @param list2         a bigger list that is used to compare
     * @return                  number of bean bags in this store
     */
    private int getTotalBeanBagsInStock(ObjectArrayList list1, ObjectArrayList list2) {
        int totalBeanBagsInStock = 0;
        for (int i = 0; i < list2.size(); i++) {
            if (i <= list1.size() - 1 && list1.size() != 0) {
                //summing all the bean bags' quantity.
                totalBeanBagsInStock += ((BeanBag) list1.get(i)).getQuantity();
            }
            //summing all the bean bags' quantity.
            totalBeanBagsInStock += ((BeanBag) list2.get(i)).getQuantity();
        }
        return totalBeanBagsInStock;
    }

    /**
     *
     * Method returns number of bean bags with matching ID in stock (total
     * researved and unreserved).
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id            ID of bean bags
     * @param list1         a smaller list that is used to compare
     * @param list2         a bigger list that is used to compare
     * @return              number of bean bags matching ID in stock
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     */
    private int getTotalBeanBagsInStock(String id, ObjectArrayList list1, ObjectArrayList list2) throws BeanBagIDNotRecognisedException {
        int totalBeanBagsInStock = 0;
        boolean isIDFound = false;
        for (int i = 0; i < list2.size(); i++) {
            if (i <= list1.size() - 1 && list1.size() != 0) {
                //checking if the id is equal.
                if(((BeanBag) list1.get(i)).getId() == id) {
                    //summing all the bean bags' quantity.
                    totalBeanBagsInStock += ((BeanBag) list1.get(i)).getQuantity();
                    isIDFound = true;
                }
            }
            //checking if the id is equal.
            if(((BeanBag) list2.get(i)).getId() == id) {
                //summing all the bean bags' quantity.
                totalBeanBagsInStock += ((BeanBag) list2.get(i)).getQuantity();
                isIDFound = true;
            }
            if (i == list2.size() - 1 && !isIDFound) {
                throw new BeanBagIDNotRecognisedException("Bean Bag ID Not Recognised: The id doesn't exist in the stockList.");
            }
        }
        return totalBeanBagsInStock;
    }

    /**
     * Method saves this BeanBagStore's contents into a serialised file,
     * with the filename given in the argument.
     *
     * @param write
     * @param list               writing to objects in this list to a file
     * @throws IOException  if there is a problem experienced when trying to load
     *                      the store contents from the file
     */
    private void saveStoreList(ObjectOutputStream write, ObjectArrayList list) throws IOException {
        try {
            //writing the size of the list to file
            write.writeInt(list.size());
            if (list.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    //writing objects to the file
                    write.writeObject(list.get(i));
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method reads BeanBagStore's contents from a serialised file,
     * from the filename given in the argument.
     *
     * @param read               to read the serialised file
     * @param list               list where the read object is stored to
     * @throws IOException  if there is a problem experienced when trying to load
     *                      the store contents from the file
     * @throws ClassNotFoundException   if required class files cannot be found when
     *                      loading
     */
    private void readStoreList(ObjectInputStream read, ObjectArrayList list) throws IOException, ClassNotFoundException {
        try {
            //reading the size of the object in the file
            int size = read.readInt();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    try {
                        list.add((BeanBag) read.readObject());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Method empties any given list.
     *
     * @param list               list that needed to be emptied
     */
    private void emptyList(ObjectArrayList list) {
        while (list.size() != 0) {
            list.remove(list.size() - 1);
        }
    }

    /**
     * Method replaces the ID of current stock matching the first argument with the
     * ID held in the second argument. To be used if there was e.g. a data entry
     * error on the ID initially entered. After the method has completed all stock
     * which had the old ID should now have the replacement ID (including
     * reservations), and all trace of the old ID should be purged from the system
     * (e.g. tracking of previous sales that had the old ID should reflect the
     * replacement ID).
     *
     * @param oldId             old ID of bean bags
     * @param replacementId     replacement ID of bean bags
     * @param list1         a smaller list that is used to compare
     * @param list2         a bigger list that is used to compare
     * @throws BeanBagIDNotRecognisedException  if the oldId does not match any
     *                          bag in (or previously in) stock
     */
    private void replaceBeanBagID(String oldId, String replacementId, ObjectArrayList list1, ObjectArrayList list2) throws BeanBagIDNotRecognisedException {
        boolean isOldIDFound = false;
        for (int i = 0; i < list2.size(); i++) {
            if (i <= list1.size() - 1 && list1.size() != 0) {
                //checking if the id is equal.
                if(((BeanBag) list1.get(i)).getId() == oldId) {
                    //replacing the id.
                    ((BeanBag) list1.get(i)).setId(replacementId);
                    isOldIDFound = true;
                }
            }
            //checking if the id is equal.
            if (((BeanBag) list2.get(i)).getId() == oldId) {
                //replacing the id.
                ((BeanBag) list2.get(i)).setId(replacementId);
                isOldIDFound = true;
            }
            if (i == list2.size() - 1 && !isOldIDFound) {
                throw new BeanBagIDNotRecognisedException("Bean Bag ID Not Recognised: The old id doesn't exist.");
            }
        }
    }

    /**
     * Method adds bean bags to the store with the arguments as bean bag details.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num               number of bean bags added
     * @param manufacturer      bean bag manufacturer
     * @param name              bean bag name
     * @param id                ID of bean bag
     * @param year              year of manufacture
     * @param month             month of manufacture
     * @throws IllegalNumberOfBeanBagsAddedException   if the number to be added
     *                           is less than 1
     * @throws BeanBagMismatchException if the id already exists (as a current in
     *                           stock bean bag, or one that has been previously
     *                           stocked in the store, but the other stored
     *                           elements (manufacturer and name) do
     *                           not match the pre-existing version
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     * @throws InvalidMonthException    if the month is not in the range 1 to 12
     */
    @Override
    public void addBeanBags(int num, String manufacturer, String name, String id, short year, byte month) throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException, IllegalIDException, InvalidMonthException {
        addBeanBagsException(num, id, month);
        //checking if the stockList is empty.
        if (stockList.size() != 0) {
            for (int i = 0; i < this.stockList.size(); i++) {
                //checking if there is an existing matching bean bags in the stockList.
                if (((BeanBag) this.stockList.get(i)).getId() == id) {
                    //checking if all the object's attributes matches.
                    if (((BeanBag) this.stockList.get(i)).getManufacturer() == manufacturer && ((BeanBag) this.stockList.get(i)).getName() == name) {
                        //initialising the matching bean bags with the new variables.
                        ((BeanBag) this.stockList.get(i)).setQuantity(((BeanBag) this.stockList.get(i)).getQuantity() + num);
                        ((BeanBag) this.stockList.get(i)).setYear(year);
                        ((BeanBag) this.stockList.get(i)).setMonth(month);
                        break; //to leave the for loop as the ID is found and ID is unique.
                    }
                    else {
                        throw new BeanBagMismatchException("Bean Bag Mismatch: There is a mismatch between id, manufacturer and name.");
                    }
                }
                //when there isn't an existing matching bean bags in the stockList.
                if (i == this.stockList.size() - 1) {
                    //initialising a BeanBag object and adding it to the stockList.
                    this.stockList.add(new BeanBag(num, manufacturer, name, id, year, month));
                    break; //to prevent the extra unnecessary loop.
                }
            }
        }
        else {
            //initialising a BeanBag object and adding it to the stockList.
            this.stockList.add(new BeanBag(num, manufacturer, name, id, year, month));
        }
    }

    /**
     * Method adds bean bags to the store with the arguments as bean bag details.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num               number of bean bags added
     * @param manufacturer      bean bag manufacturer
     * @param name              bean bag name
     * @param id                ID of bean bag
     * @param year              year of manufacture
     * @param month             month of manufacture
     * @param information       free text detailing bean bag information
     * @throws IllegalNumberOfBeanBagsAddedException   if the number to be added
     *                           is less than 1
     * @throws BeanBagMismatchException if the id already exists (as a current in
     *                           stock bean bag, or one that has been previously
     *                           stocked in the store, but the other stored
     *                           elements (manufacturer, name and free text) do
     *                           not match the pre-existing version
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     * @throws InvalidMonthException    if the month is not in the range 1 to 12
     */
    @Override
    public void addBeanBags(int num, String manufacturer, String name, String id, short year, byte month, String information) throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException, IllegalIDException, InvalidMonthException {
        addBeanBagsException(num, id, month);
        if (stockList.size() != 0) {
            for (int i = 0; i < this.stockList.size(); i++) {
                //checking if there is an existing matching bean bags in the stockList.
                if (((BeanBag) this.stockList.get(i)).getId() == id) {
                    //checking if all the object's attributes matches.
                    if (((BeanBag) this.stockList.get(i)).getManufacturer() == manufacturer && ((BeanBag) this.stockList.get(i)).getName() == name && ((BeanBag) this.stockList.get(i)).getInformation() == information) {
                        //initialising the matching bean bags with the new variables.
                        ((BeanBag) this.stockList.get(i)).setQuantity(((BeanBag) this.stockList.get(i)).getQuantity() + num);
                        ((BeanBag) this.stockList.get(i)).setYear(year);
                        ((BeanBag) this.stockList.get(i)).setMonth(month);
                        break; //to leave the for loop as the ID is found and ID is unique.
                    }
                    else {
                        throw new BeanBagMismatchException("Bean Bag Mismatch: There is a mismatch between id, manufacturer, name and free text");
                    }
                }
                //when there isn't an existing matching bean bags in the stockList.
                if (i == this.stockList.size() - 1) {
                    //initialising a BeanBag object and adding it to the stockList.
                    this.stockList.add(new BeanBag(num, manufacturer, name, id, year, month));
                    break; //to prevent the extra unnecessary loop.
                }
            }
        }
        else {
            //initialising a BeanBag object and adding it to the stockList.
            this.stockList.add(new BeanBag(num, manufacturer, name, id, year, month, information));
        }
    }

    /**
     * Method to set the price of bean bags with matching ID in stock.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id                ID of bean bags
     * @param priceInPence      bean bag price in pence
     * @throws InvalidPriceException if the priceInPence < 1
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException if the ID is not a positive eight character
     *                           hexadecimal number
     */
    @Override
    public void setBeanBagPrice(String id, int priceInPence) throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException { beanBagsIllegalIDException(id);
        if (priceInPence < 1) {
            throw new InvalidPriceException("Invalid Price: The price in pence has to be greater than 0.");
        }
        for (int i = 0; i < this.stockList.size(); i++) {
            //checking if the id is equal.
            if (((BeanBag) this.stockList.get(i)).getId() == id) {
                //assigning price to the BeanBag object according to the ID.
                ((BeanBag) this.stockList.get(i)).setPrice(priceInPence);
                break; //to leave the for loop as the ID is found and ID is unique.
            }
            beanBagIDNotRecognisedException(i, this.stockList);
        }
    }

    /**
     * Method sells bean bags with the corresponding ID from the store and removes
     * the sold bean bags from the stock.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num           number of bean bags to be sold
     * @param id            ID of bean bags to be sold
     * @throws BeanBagNotInStockException   if the bean bag has previously been in
     *                      stock, but is now out of stock
     * @throws InsufficientStockException   if the bean bag is in stock, but not
     *                      enough are available (i.e. in stock and not reserved)
     *                      to meet sale demand
     * @throws IllegalNumberOfBeanBagsSoldException if an attempt is being made to
     *                      sell fewer than 1 bean bag
     * @throws PriceNotSetException if the bag is in stock, and there is sufficient
     *                      stock to meet demand, but the price has yet to be set
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
    @Override
    public void sellBeanBags(int num, String id) throws BeanBagNotInStockException, InsufficientStockException, IllegalNumberOfBeanBagsSoldException, PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException {
        if (num < 1) {
            throw new IllegalNumberOfBeanBagsSoldException("Illegal Number Of Bean Bags Sold: The number of bean bags sold must be greater than 0.");
        }
        beanBagsIllegalIDException(id);
        for (int i = 0; i < this.stockList.size(); i++) {
            //checking if the id is equal.
            if (((BeanBag) this.stockList.get(i)).getId() == id) {
                if (((BeanBag) this.stockList.get(i)).getPrice() == 0) {
                    throw new PriceNotSetException("Price Not Set: The price for the bean bags has not been set.");
                }
                if (((BeanBag) this.stockList.get(i)).getQuantity() == 0) {
                    throw new BeanBagNotInStockException("Bean Bag Not In Stock: The quantity of bean bags in stock is 0.");
                }
                //checking if there is a sufficient amount of bean bags to sell.
                if (((BeanBag) this.stockList.get(i)).getQuantity() >= num) {
                    //editing the quantity of bean bags in the stockList.
                    ((BeanBag) this.stockList.get(i)).setQuantity(((BeanBag) this.stockList.get(i)).getQuantity() - num);
                    if (this.soldList.size() != 0) {
                        for (int x = 0; x < this.soldList.size(); x++) {
                            //checking if the id is equal.
                            if (((BeanBag) this.soldList.get(x)).getId() == id) {
                                ((BeanBag) this.soldList.get(x)).setQuantity(((BeanBag) this.soldList.get(x)).getQuantity() + num);
                                break;
                            }
                            if (x == this.soldList.size() - 1) {
                                //adding the sold bean bags to the soldList.
                                this.soldList.add(new BeanBag(num, id, ((BeanBag) this.stockList.get(i)).getPrice()));
                                break; //to leave the for loop as the ID is found and ID is unique.
                            }
                        }
                        break;
                    }
                    else {
                        //adding the sold bean bags to the soldList.
                        this.soldList.add(new BeanBag(num, id, ((BeanBag) this.stockList.get(i)).getPrice()));
                        break; //to leave the for loop as the ID is found and ID is unique.
                    }
                }
                else {
                    throw new InsufficientStockException("Insufficient Stock: Not enough stock to sell.");
                }
            }
            beanBagIDNotRecognisedException(i, this.stockList);
        }
    }

    /**
     * Method reserves bean bags with the corresponding ID in the store and returns
     * the reservation number needed to later access the reservation
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num           number of bean bags to be reserved
     * @param id            ID of bean bags to be reserved
     * @return              unique reservation number, i.e. one not currently live
     *                      in the system
     * @throws BeanBagNotInStockException   if the bean bag has previously been in
     *                      stock, but is now out of stock
     * @throws InsufficientStockException   if the bean bag is in stock, but not
     *                      enough are available to meet the reservation demand
     * @throws IllegalNumberOfBeanBagsReservedException if the number of bean bags
     *                      requested to reserve is fewer than 1
     * @throws PriceNotSetException if the bag is in stock, and there is sufficient
     *                      stock to meet demand, but the price has yet to be set
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
    @Override
    public int reserveBeanBags(int num, String id) throws BeanBagNotInStockException, InsufficientStockException, IllegalNumberOfBeanBagsReservedException, PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException {
        if (num < 1) {
            throw new IllegalNumberOfBeanBagsReservedException("Illegal Number Of Bean Bags Reserved: The number of bean bags reserved must be greater than 0.");
        }
        beanBagsIllegalIDException(id);
        for (int i = 0; i < this.stockList.size(); i++) {
            //checking if the id is equal.
            if (((BeanBag) this.stockList.get(i)).getId() == id) {
                if (((BeanBag) this.stockList.get(i)).getPrice() == 0) {
                    throw new PriceNotSetException("Price Not Set: The price for the bean bags has not been set.");
                }
                if (((BeanBag) this.stockList.get(i)).getQuantity() == 0) {
                    throw new BeanBagNotInStockException("Bean Bag Not In Stock: The quantity of bean bags in stock is 0.");
                }
                //checking if there is a sufficient amount of bean bags to reserve.
                if (((BeanBag) this.stockList.get(i)).getQuantity() >= num) {
                    //editing the quantity of bean bags in the stockList.
                    ((BeanBag) this.stockList.get(i)).setQuantity(((BeanBag) this.stockList.get(i)).getQuantity() - num);
                    int reservationNumber = generateReservationNumber();
                    //adding the reserved bean bags to the reserveList.
                    this.reserveList.add(new BeanBag(num, id, ((BeanBag) this.stockList.get(i)).getPrice(), reservationNumber));
                    return reservationNumber;
                } else {
                    throw new InsufficientStockException("Insufficient Stock: Not enough stock to sell.");
                }
            }
        }
        throw new BeanBagIDNotRecognisedException("Bean Bag ID Not Recognised: The id doesn't exist in the stockList.");
    }

    /**
     * Method removes an existing reservation from the system due to a reservation
     * cancellation (rather than sale). The stock should therefore remain unchanged.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param reservationNumber           unique reservation number used to find
     *                                    beanbag(s) to be sold
     * @throws ReservationNumberNotRecognisedException  if the reservation number
     *                          does not match a current reservation in the system
     */
    @Override
    public void unreserveBeanBags(int reservationNumber) throws ReservationNumberNotRecognisedException {
        for (int i = 0; i < this.reserveList.size(); i++) {
            //find the BeanBag object's reservationNumber in the reserveList.
            if (((BeanBag) this.reserveList.get(i)).getReservationNumber() == reservationNumber) {
                for (int x = 0; x < this.stockList.size(); x++) {
                    //checking if the id is equal.
                    if (((BeanBag) this.stockList.get(x)).getId() == ((BeanBag) this.reserveList.get(i)).getId()) {
                        //editing the quantity of bean bags in the stockList.
                        ((BeanBag) this.stockList.get(x)).setQuantity(((BeanBag) this.stockList.get(x)).getQuantity() + ((BeanBag) this.reserveList.get(i)).getQuantity());
                    }
                }
                //removing the BeanBag object in the reserveList.
                this.reserveList.remove(i);
                break; //to leave the for loop as the ID is found and ID is unique.
            }
            beanBagReservationNumberNotRecognisedException(i);
        }
    }

    /**
     * Method sells beanbags with the corresponding reservation number from
     * the store and removes these sold beanbags from the stock.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param reservationNumber           unique reservation number used to find
     *                                    beanbag(s) to be sold
     * @throws ReservationNumberNotRecognisedException  if the reservation number
     *                          does not match a current reservation in the system
     */
    @Override
    public void sellBeanBags(int reservationNumber) throws ReservationNumberNotRecognisedException {
        for (int i = 0; i < this.reserveList.size(); i++) {
            //find the BeanBag object's reservationNumber in the reserveList.
            if (((BeanBag) this.reserveList.get(i)).getReservationNumber() == reservationNumber) {
                for (int y = 0; y < this.stockList.size(); y++) {
                    //checking if the id is equal.
                    if (((BeanBag) this.stockList.get(y)).getId() == ((BeanBag) this.reserveList.get(i)).getId()) {
                        if (((BeanBag) this.stockList.get(y)).getPrice() < ((BeanBag) this.reserveList.get(i)).getPrice()) {
                            ((BeanBag) this.reserveList.get(i)).setPrice(((BeanBag) this.stockList.get(y)).getPrice());
                            break;
                        }
                    }
                }
                if(this.soldList.size() != 0) {
                    for (int x = 0; x < this.soldList.size(); x++) {
                        //checking if the id is equal.
                        if (((BeanBag) this.soldList.get(x)).getId() == ((BeanBag) this.reserveList.get(i)).getId() && ((BeanBag) this.soldList.get(x)).getPrice() == ((BeanBag) this.reserveList.get(i)).getPrice()) {
                            ((BeanBag) this.soldList.get(x)).setQuantity(((BeanBag) this.soldList.get(x)).getQuantity() + ((BeanBag) this.reserveList.get(i)).getQuantity());
                            //removing the BeanBag object from the reserveList.
                            this.reserveList.remove(i);
                            break;
                        }
                        if (x == this.soldList.size() - 1) {
                            //adding the BeanBag object to the soldList.
                            this.soldList.add(new BeanBag(((BeanBag) this.reserveList.get(i)).getQuantity(), ((BeanBag) this.reserveList.get(i)).getId(), ((BeanBag) this.reserveList.get(i)).getPrice()));
                            //removing the BeanBag object from the reserveList.
                            this.reserveList.remove(i);
                            break; //to leave the for loop as the ID is found and ID is unique.
                        }
                    }
                    break;
                }
                else {
                    //adding the BeanBag object to the soldList.
                    this.soldList.add(new BeanBag(((BeanBag) this.reserveList.get(i)).getQuantity(), ((BeanBag) this.reserveList.get(i)).getId(), ((BeanBag) this.reserveList.get(i)).getPrice()));
                    //removing the BeanBag object from the reserveList.
                    this.reserveList.remove(i);
                    break; //to leave the for loop as the ID is found and ID is unique.
                }
            }
            beanBagReservationNumberNotRecognisedException(i);
        }
    }

    /**
     * Access method for the number of BeanBags stocked by this BeanBagStore
     * (total of reserved and unreserved stock).
     *
     * @return                  number of bean bags in this store
     */
    @Override
    public int beanBagsInStock() {
        if (this.stockList.size() >= this.reserveList.size()) {
            return getTotalBeanBagsInStock(this.reserveList, this.stockList);
        }
        else {
            return getTotalBeanBagsInStock(this.stockList, this.reserveList);
        }
    }

    /**
     * Access method for the number of reserved bean bags stocked by this
     * BeanBagStore.
     *
     * @return             number of reserved bean bags in this store
     */
    @Override
    public int reservedBeanBagsInStock() {
        int reservedBeanBagsInStock = 0;
        for (int i = 0; i < this.reserveList.size(); i++) {
            //summing all the reserved bean bags' quantity.
            reservedBeanBagsInStock += ((BeanBag) this.reserveList.get(i)).getQuantity();
        }
        return reservedBeanBagsInStock;
    }

    /**
     * Method returns number of bean bags with matching ID in stock (total
     * researved and unreserved).
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id            ID of bean bags
     * @return              number of bean bags matching ID in stock
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
    @Override
    public int beanBagsInStock(String id) throws BeanBagIDNotRecognisedException, IllegalIDException {
        beanBagsIllegalIDException(id);
        if (this.stockList.size() >= this.reserveList.size()) {
            return getTotalBeanBagsInStock(id, this.reserveList, this.stockList);
        }
        else {
            return getTotalBeanBagsInStock(id, this.stockList, this.reserveList);
        }
    }

    /**
     * Method saves this BeanBagStore's contents into a serialised file,
     * with the filename given in the argument.
     *
     * @param filename      location of the file to be saved
     * @throws IOException  if there is a problem experienced when trying to save
     *                      the store contents to the file
     */
    @Override
    public void saveStoreContents(String filename) throws IOException {
        ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(filename));
        //saving data from the lists to a file.
        saveStoreList(write, this.stockList);
        saveStoreList(write, this.reserveList);
        saveStoreList(write, this.soldList);
        write.close();
    }

    /**
     * Method should load and replace this BeanBagStore's contents with the
     * serialised contents stored in the file given in the argument.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param filename      location of the file to be loaded
     * @throws IOException  if there is a problem experienced when trying to load
     *                      the store contents from the file
     * @throws ClassNotFoundException   if required class files cannot be found when
     *                      loading
     */
    @Override
    public void loadStoreContents(String filename) throws IOException, ClassNotFoundException {
        empty(); //so the content is replaced
        ObjectInputStream read = new ObjectInputStream(new FileInputStream(filename));
        //loading data to the lists.
        readStoreList(read, this.stockList);
        readStoreList(read, this.reserveList);
        readStoreList(read, this.soldList);
        read.close();
    }

    /**
     * Access method for the number of different bean bags currently stocked by this
     * BeanBagStore.
     *
     * @return                  number of different specific bean bags currently in
     *                          this store (i.e. how many different IDs represented
     *                          by bean bags currently in stock, including reserved)
     */
    @Override
    public int getNumberOfDifferentBeanBagsInStock() {
        return this.stockList.size();
    }

    /**
     * Method to return number of bean bags sold by this BeanBagStore.
     *
     * @return                  number of bean bags sold by the store
     */
    @Override
    public int getNumberOfSoldBeanBags() {
        int numberOfSoldBeanBags = 0;
        for (int i = 0; i < this.soldList.size(); i++) {
            //summing all the sold bean bags' quantity.
            numberOfSoldBeanBags += ((BeanBag) this.soldList.get(i)).getQuantity();
        }
        return numberOfSoldBeanBags;
    }

    /**
     * Method to return number of bean bags sold by this BeanBagStore with
     * matching ID.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id                 ID of bean bags
     * @return                   number bean bags sold by the store with matching ID
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
    @Override
    public int getNumberOfSoldBeanBags(String id) throws BeanBagIDNotRecognisedException, IllegalIDException {
        beanBagsIllegalIDException(id);
        int numberOfSoldBeanBags = 0;
        for (int i = 0; i < this.soldList.size(); i++) {
            //i didn't return here, because thanks to customer reservation advantage i need to iterate throughout the whole list.
            if (((BeanBag) this.soldList.get(i)).getId() == id) {
                //summing all the sold bean bags' quantity.
                numberOfSoldBeanBags += ((BeanBag) this.soldList.get(i)).getQuantity();
            }
            if (i == this.soldList.size() - 1 && numberOfSoldBeanBags == 0) {
                throw new BeanBagIDNotRecognisedException("Bean Bag ID Not Recognised: The given id isn't recognised in the sold list.");
            }
        }
        return numberOfSoldBeanBags;
    }

    /**
     * Method to return total price of bean bags sold by this BeanBagStore
     * (in pence), i.e. income that has been generated by these sales).
     *
     * @return                  total cost of bean bags sold (in pence)
     */
    @Override
    public int getTotalPriceOfSoldBeanBags() {
        int totalPrice = 0;
        for (int i = 0; i < this.soldList.size(); i++) {
            //summing all the sold bean bags' quantity.
            totalPrice += ((BeanBag) this.soldList.get(i)).getQuantity() * ((BeanBag) this.soldList.get(i)).getPrice();
        }
        return totalPrice;
    }

    /**
     * Method to return total price of bean bags sold by this BeanBagStore
     * (in pence) with  matching ID (i.e. income that has been generated
     * by these sales).
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id                ID of bean bags
     * @return                  total cost of bean bags sold (in pence) with
     *                          matching ID
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
    @Override
    public int getTotalPriceOfSoldBeanBags(String id) throws BeanBagIDNotRecognisedException, IllegalIDException {
        beanBagsIllegalIDException(id);
        int totalPrice = 0;
        for (int i = 0; i < this.soldList.size(); i++) {
            //checking if the id is equal.
            if (((BeanBag) this.soldList.get(i)).getId() == id) {
                //i didn't return here, because thanks to customer reservation advantage i need to iterate throughout the whole list.
                //summing all the sold bean bags' quantity.
                totalPrice += ((BeanBag) this.soldList.get(i)).getQuantity() * ((BeanBag) this.soldList.get(i)).getPrice();
            }
            if (i == this.soldList.size() - 1) {
                return totalPrice;
            }
        }
        throw new BeanBagIDNotRecognisedException("Bean Bag ID Not Recognised: The given id isn't recognised in the sold list.");
    }

    /**
     * Method to return the total price of reserved bean bags in this BeanBagStore
     * (i.e. income that would be generated if all the reserved stock is sold
     * to those holding the reservations).
     *
     * @return                  total price of reserved bean bags
     */
    @Override
    public int getTotalPriceOfReservedBeanBags() {
        int totalPrice = 0;
        for (int i = 0; i < this.reserveList.size(); i++) {
            //summing all the total price.
            totalPrice += ((BeanBag) this.reserveList.get(i)).getQuantity() * ((BeanBag) this.reserveList.get(i)).getPrice();
        }
        return totalPrice;
    }

    /**
     * Method to return the free text details of a bean bag in stock. If there
     * are no String details for a bean bag, there will be an empty String
     * instance returned.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id                ID of bean bag
     * @return                  any free text details relating to the bean bag
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
    @Override
    public String getBeanBagDetails(String id) throws BeanBagIDNotRecognisedException, IllegalIDException {
        beanBagsIllegalIDException(id);
        for (int i = 0; i < this.stockList.size(); i++) {
            //checking if the id is equal.
            if (((BeanBag) this.stockList.get(i)).getId() == id) {
                return ((BeanBag) this.stockList.get(i)).getInformation();
            }
        }
        throw new BeanBagIDNotRecognisedException("Bean Bag ID Not Recognised: The given id isn't recognised in the stock list.");
    }

    /**
     * Method empties this BeanBagStore of its contents and resets
     * all internal counters.
     */
    @Override
    public void empty() {
        //emptying all the lists.
        emptyList(this.stockList);
        emptyList(this.reserveList);
        emptyList(this.soldList);
    }

    /**
     * Method resets the tracking of number and costs of all bean bags sold.
     * The stock levels of this BeanBagStore and reservations should
     * be unaffected.
     */
    @Override
    public void resetSaleAndCostTracking() {
        //emptying the soldList.
        emptyList(this.soldList);
    }

    /**
     * Method replaces the ID of current stock matching the first argument with the
     * ID held in the second argument. To be used if there was e.g. a data entry
     * error on the ID initially entered. After the method has completed all stock
     * which had the old ID should now have the replacement ID (including
     * reservations), and all trace of the old ID should be purged from the system
     * (e.g. tracking of previous sales that had the old ID should reflect the
     * replacement ID).
     * <p>
     * If the replacement ID already exists in the system, this method will return
     * an {@link IllegalIDException}.
     *
     * @param oldId             old ID of bean bags
     * @param replacementId     replacement ID of bean bags
     * @throws BeanBagIDNotRecognisedException  if the oldId does not match any
     *                          bag in (or previously in) stock
     * @throws IllegalIDException   if either argument is not a positive eight
     *                          character hexadecimal number, or if the
     *                          replacementID is already in use in the store as
     *                          an ID
     */
    @Override
    public void replace(String oldId, String replacementId) throws BeanBagIDNotRecognisedException, IllegalIDException {
        beanBagsIllegalIDException(oldId);
        beanBagsIllegalIDException(replacementId);
        if (this.stockList.size() >= this.reserveList.size()) {
            replaceBeanBagID(oldId, replacementId, this.reserveList, this.stockList);
        } else {
            replaceBeanBagID(oldId, replacementId, this.stockList, this.reserveList);
        }
    }
}
