import java.util.Scanner;

public class PharmacyManagementSystem {

    static final String CORRECT_USERNAME = "abd";
    static final String CORRECT_PASSWORD = "1234";
    static final int MAX_MEDICINES = 1000;

    static String[] medicineNames = new String[MAX_MEDICINES];
    static double[] medicinePrices = new double[MAX_MEDICINES];
    static int[] medicineStocks = new int[MAX_MEDICINES];

    static int medicineCount = 3;
    static double subtotal = 0;
    static int totalItems = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        
        medicineNames[0] = "Paracetamol";
        medicinePrices[0] = 3.00;
        medicineStocks[0] = 20;

        medicineNames[1] = "Vitamin C";
        medicinePrices[1] = 6.00;
        medicineStocks[1] = 15;

        medicineNames[2] = "Cough Syrup";
        medicinePrices[2] = 8.00;
        medicineStocks[2] = 10;

        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("Doctor Login");

            System.out.print("Username: ");
            String username = scanner.next();
            System.out.print("Password: ");
            String password = scanner.next();
           
            loggedIn = verifyLogin(username, password);

            if (loggedIn) {
                System.out.println("Login successful.\n");
            } else {
                System.out.println("Incorrect username or password.\n");
            }
        }
        
        int choice;
        do {
            displayMenu();

            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (medicineCount == 0) {
                          System.out.println("No medicines available to order.");
                       break;
                    } 
                    displayInventory();
                    System.out.print("Select medicine number: ");
                    int medicineChoice = scanner.nextInt();

                    if (medicineChoice < 1 || medicineChoice > medicineCount) {
                        System.out.println("Invalid medicine selection.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();

                    if (quantity <= 0) { 
                        System.out.println("Quantity must be greater than zero.");
                        break;
                    }
                    int stock = getMedicineStock(medicineChoice);

                    if (quantity > stock) {
                        System.out.println("Not enough stock available.");
                        break;
                    }

                    double price = getMedicinePrice(medicineChoice);

                    double itemCost =
                        calculateItemCost(price, quantity);

                    subtotal = subtotal + itemCost;
                    totalItems = totalItems + quantity;

                    medicineStocks[medicineChoice - 1] = medicineStocks[medicineChoice - 1] - quantity;

                    System.out.println("Medicine ordered successfully.");

                    System.out.printf( "Item Cost: $%.2f\n", itemCost  );
                    break;
                case 2:
                    addMedicine();
                    break;
                case 3:
                    deleteMedicine();
                    break;
                case 4:
                    displayInventory();
                    break;
                case 5:
                    double finalTotal = calculateFinalTotal(subtotal);
                    printReceipt(totalItems,subtotal,finalTotal );
                    break;
                    
                default:
                    System.out.println("Invalid option.");
            }
            System.out.println();
            
        } while (choice != 5);
    }
    
    static boolean verifyLogin(String username,String password) {
        return username.equals(CORRECT_USERNAME) && password.equals(CORRECT_PASSWORD);
    }

    static void displayMenu() {
        System.out.println("Pharmacy Menu");
        System.out.println("1. Order Medicine");
        System.out.println("2. Add New Medicine");
        System.out.println("3. Delete Medicine");
        System.out.println("4. Display Inventory");
        System.out.println("5. Finish Order");
    }

    static void displayInventory() {

        System.out.println("Pharmacy Inventory");
 
        if (medicineCount == 0) {
        System.out.println("No medicines available.");
        return;
        }
        for (int i = 0; i < medicineCount; i++) {
            System.out.printf( "%d. %s - $%.2f - Stock: %d\n",i + 1,medicineNames[i],medicinePrices[i],medicineStocks[i]);
        }
    }
    
    static double getMedicinePrice(int medicineChoice) {
        switch (medicineChoice) {
            case 1:
                return medicinePrices[0];
            case 2:
                return medicinePrices[1];
            case 3:
                return medicinePrices[2];
            default:
                return medicinePrices[medicineChoice - 1];
        }
    }

    static int getMedicineStock(int medicineChoice) {

        return medicineStocks[medicineChoice - 1];
    }

  static void addMedicine() {

    if (medicineCount >= MAX_MEDICINES) {
        System.out.println("Cannot add more medicines.");
        return;
    }

    scanner.nextLine();

    System.out.print("Enter medicine name: ");
    medicineNames[medicineCount] = scanner.nextLine();
    System.out.print("Enter medicine price: ");
    medicinePrices[medicineCount] = scanner.nextDouble();
    System.out.print("Enter available stock: ");
    medicineStocks[medicineCount] = scanner.nextInt();
    
    medicineCount++;

    System.out.println("Medicine added successfully.");
}

    static void deleteMedicine() {
      
        if (medicineCount == 0) {
        System.out.println("No medicines available to delete.");
        return;
    }
        displayInventory();
        
        System.out.print("Enter medicine number to delete: ");
        int choice = scanner.nextInt();

        if (choice < 1 || choice > medicineCount) {
            System.out.println("Invalid medicine number.");
            return;
        }
        int index = choice - 1;
        for (int i = index; i < medicineCount - 1; i++) {

            medicineNames[i] = medicineNames[i + 1];
            medicinePrices[i] = medicinePrices[i + 1];
            medicineStocks[i] = medicineStocks[i + 1];
        }

        medicineCount--;

        System.out.println("Medicine deleted successfully.");
    }

    static double calculateItemCost( double price,int quantity) {
        
        return price * quantity;
    }

    static double calculateFinalTotal(double subtotal) {

        return subtotal;
    }


    static void printReceipt( int totalItems, double subtotal, double finalTotal) {

        System.out.println("==============================");
        System.out.println("        ORDER RECEIPT");
        System.out.println("==============================");
        System.out.println("Total Items: " + totalItems);
        System.out.printf("Subtotal: $%.2f\n", subtotal);
        System.out.printf("Final Total: $%.2f\n", finalTotal);
        System.out.println("==============================");
    }
}