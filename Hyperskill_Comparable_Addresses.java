import java.util.*;

/**
 * 
 * This problem is from Jetbrains Academy topic - Comparable
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-04
 *
 * Problem statement:
*   Addresses:
*
*       Our information system stores mailing addresses using the Address class. 
*       Users want to see addresses listed in the alphabetical order. 
*       It means we need to sort the addresses and for this purpose we must be able to compare Address objects with each other. 
*       Make the Address class Comparable so we can do it! The full address format is defined in the toString method. Follow it when comparing addresses.
*/

class Hyperskill_Comparable_Addresses {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Address> list = new ArrayList<>();

        while (sc.hasNextLine()) {
            String[] arguments = sc.nextLine().split(",");
            list.add(new Address(arguments[0], arguments[1], arguments[2]));
        }

        Collections.sort(list);
        
        // Checker.check(list);

        for (Address address : list) {
            System.out.println(address);
        }

        sc.close();
    }
}

class Address implements Comparable<Address> {
    private final String city;
    private final String street;
    private final String house;

    public Address(String city, String street, String house) {
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public String getCity() {
        return this.city;
    }

    public String getStreet() {
        return this.street;
    }

    public String getHouse() {
        return this.house;
    }

    @Override
    public String toString() {
        // return "%s, %s, %s".formatted(house, street, city);

        return String.format("%s, %s, %s", this.house, this.street, this.city);
    }

    @Override
    public int compareTo(Address address) {
        return this.toString().compareToIgnoreCase(address.toString());
    }
    
    /*
    @Override
    public int compareTo(Address address) {
        int compHouse = this.house.compareToIgnoreCase(address.getHouse());

        if (compHouse == 0) {
            int compStreet = this.street.compareToIgnoreCase(address.getStreet());

            if (compStreet == 0) {
                return this.city.compareToIgnoreCase(address.getCity());
            } else {
                return compStreet;
            }
        } else {
            return compHouse;
        }
    }
    */
}