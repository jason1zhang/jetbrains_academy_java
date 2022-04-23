import java.util.Scanner;
import java.util.Date;

/**
 * This program is to demonstrate the usage of various methods in the java build-in classes.
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-13
 */
public class MethodDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String strMethod;
        boolean isDone = false;

        do {
            System.out.print("\nEnter the method to demonstrate: > ");
            strMethod = scanner.next();

            switch (strMethod) {
                case "nextLine":
                    nextLineDemo(scanner);
                    break;
                case "getClass":
                    getClassDemo();
                    break;
                case "NPE":
                    NPEDemo();
                    break;            
                case "hyper_fixed_sized_array":
                    hyperskill_fixed_sized_array();
                    break; 
                case "hyper_dynamic_array":
                    hyperskill_dynamic_array();
                    break;                  
                case "hyper_exception":
                    String input = "0";
                    hyperskill_exception(input);
                    break;                                
                case "none":
                    isDone = true;
                    System.out.println("\n");
                    break;
                default:
                    System.out.println("Unexpected method, please try to enter again!\n");
                    break;
            }
        } while (!isDone);

        scanner.close();
    }

    /**
     * demo the difference between the method next() and nextLine().
     * 
     * @param scanner java.util.Scanner
     */
    private static void nextLineDemo(Scanner scanner) {
        System.out.printf("\n*** This is to demo the usage of method \"nextLine()\" in the class java.util.Scanner. ***\n");

        System.out.print("next(): > ");
        String s1 = scanner.next();
        System.out.printf("s1 -> (%s)\n\n", s1);

        System.out.print("nextLine(): > ");
        scanner.nextLine(); // Important! Here need to call nextLine() one extra line to get rid of the carriage return
        String s2 = scanner.nextLine();
        System.out.printf("s2 -> (%s)\n\n\n", s2);
    }

    /**
     * demo the usage of getClass method
     */
    public static void getClassDemo() {
        System.out.println("*** This is to demo the usage of method \"getClass()\" in the class Object. ***\n");

        System.out.println("*** 1. using the subclass reference ***\n");

        Person person = new Person("person 1");
        Client client1 = new Client("client 1");
        Employee employee1 = new Employee("employee 1");

        System.out.printf("Class of ojbect {%s} is {%s}\n", person.toString(), person.getClass().getName());
        System.out.printf("Class of ojbect {%s} is {%s}\n", client1.toString(), client1.getClass().getName());
        System.out.printf("Class of ojbect {%s} is {%s}\n", employee1.toString(), employee1.getClass().getName());

        System.out.println("\n\n*** 2. using the superclass reference ***");

        Person client2 = new Client("client 2");
        Person employee2 = new Employee("employee 2");

        System.out.printf("Class of ojbect {%s} is {%s}\n", client2.toString(), client2.getClass().getName());
        System.out.printf("Class of ojbect {%s} is {%s}\n\n", employee2.toString(), employee2.getClass().getName());
    }

    /**
     * demo the null pointer exception
     */
    public static void NPEDemo() {
        String str1;
        String str2 = "123";

        if (str2.equals(str1 = "123")) {
            str2 = null;
        }

        str1 = str2;
        str2 = str1;

        System.out.println(str2);
    }

    /**
     * hyperskill practice for fixed sized array
     */
    public static void hyperskill_fixed_sized_array() {
        int[] a = {3, 0, 3, 9, 2, 1};

        int r = 7;
        int i;
        for (i = 0; i < a.length; i = i + 1) {
            r += a[i] * a[i];
        }
        System.out.println(r);

        r = 13;
        r += a[a[0]];
        r -= a[a[a.length - 1]];
        System.out.println(r);

        r = 0;
        for (i = 0; i < a.length; i = i + 1) {
            if (a[i] < a.length) {
                r += a[i];
            }
        }
        System.out.println(r);
    }

    /**
     * hyperskill practice for dynamic array
     * 
     * Assume that the size and capacity of a dynamic array are equal to 2 and 4 respectively, 
     * and the scaling factor is 2. After you perform nn insertions, the capacity of the array will become 1024. 
     * What is the minimum possible value of n?
     */
    public static void hyperskill_dynamic_array() {
        int current_size = 2;
        int current_capacity = 4;
        int terminal_capacity = 1024;
        int scaling_factor = 2;

        int free_capacity = current_capacity - current_size;
        int n1 = (terminal_capacity - free_capacity ) / scaling_factor;
        System.out.println(n1); 

        // +1 because the additional capacity is allocated the moment size exceeds current capacity by 1.
        int n2 = (terminal_capacity / scaling_factor) - current_size + 1;
        System.out.println(n2);

        /*
         * Another solution:
         * 
         *It's very simple. Start with the fact that if the array size is 512 
         * then that would be to fill it is necessary to add elements 510.
         * Or
         * To put it in words, I'd say it like this: Your array just doubled its size to 1024, 
         * because you added 1 element more than its capacity was before it doubled. 
         * Now think how many elements you have in your array, and keep in mind 
         *that there were already two elements in it when you started. How many did you add to get to the current size?
        */
    }

    private static void hyperskill_exception(String input) {
        boolean result = Boolean.parseBoolean(input);
        result = !result;
        String output = String.valueOf(result);
        System.out.println(output);        
    }
}

class Person {
    protected String name;
    protected int yearOfBirth;
    protected String address;

    public Person (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    } 

    @Override
    public String toString() {
        return getName();
    }
}

class Client extends Person {
    protected String contractNumber;
    protected boolean gold;

    public Client(String name) {
        super(name);
    }

    public String getContractNumber() {
        return contractNumber;
    }
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
    public boolean isGold() {
        return gold;
    }
    public void setGold(boolean gold) {
        this.gold = gold;
    }    
}

class Employee extends Person {
    protected Date startDate;
    protected long salary;
    
    public Employee(String name) {
        super(name);
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public long getSalary() {
        return salary;
    }
    public void setSalary(long salary) {
        this.salary = salary;
    }    
}