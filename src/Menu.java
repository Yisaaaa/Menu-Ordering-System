import java.util.Scanner;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class Menu {

    private final static Scanner scanner = new Scanner(System.in);
    private final static String[] categories = {"Ulam", "Gulay", "Meryenda"};
    private final static LinkedHashMap<String, Integer> MainDishes = new LinkedHashMap<>();
    private final static LinkedHashMap<String, Integer> SideDishes = new LinkedHashMap<>();
    private final static LinkedHashMap<String, Integer> Desserts = new LinkedHashMap<>();
    private static ArrayList<OrderItem> OrderItems = new ArrayList<>();
    private static String bunchOfNewLines = "";

    static {
        MainDishes.put("Adobo", 60);
        MainDishes.put("Sinigang na Bangus", 60);
        MainDishes.put("Bopiz", 60);
        MainDishes.put("Bicol Express", 60);
        MainDishes.put("Beef Steak", 60);

        SideDishes.put("Chopsuey", 23);
        SideDishes.put("Ampalaya", 23);
        SideDishes.put("Basta gulay", 23);
        SideDishes.put("Mapait na gulay", 23);

        Desserts.put("Halo halo", 67);
        Desserts.put("Leche flan", 90);
        Desserts.put("Ice cream", 40);

        for (int i = 0; i <= 100; i++) {
            bunchOfNewLines += "\n";
        }
    }


    public static void main(String[] args) {
        getCategoryChoice();

    }


    public static void getCategoryChoice() {
        printMainMenu();
        String choice = getChoice();
        switch (choice) {
            case "0":
                confirmOrder();
            case "1":
                String[] mainDishesArr = {"Adobo", "Sinigang na Bangus", "Bopiz", "Bicol Express", "Beef Steak"};
                getOrder(MainDishes, mainDishesArr);
                break;
            case "2":
                String[] sideDishesArr = {"Chopsuey", "Ampalaya", "Basta gulay", "Mapait na gulay"};
                getOrder(SideDishes, sideDishesArr);
                break;
            case "3":
                String[] dessertsArr = {"Halo halo", "Leche flan", "Ice cream"};
                getOrder(Desserts, dessertsArr);
                break;
            default:
                System.out.println(bunchOfNewLines + "Please enter the number of your choice.");
                getCategoryChoice();
        }
    }

//    public static void getMainDishOrder() {
////        System.out.println("This is Ulam menu. Enter 0");
//        printMenuDishes(MainDishes);
//        int choice = Integer.parseInt(getChoice());
//        String[] mainDishes = {"Adobo", "Sinigang na Bangus", "Bopiz", "Bicol Express", "Beef Steak"};
//        if (choice == 0) {
//            getCategoryChoice();
//        } else if (isValidChoice(choice, mainDishes)) {
//            addOrderItem(mainDishes, choice);
//            getCategoryChoice();
//        } else {
//            System.out.println("\n\n\n\n\nPlease enter the number of your choice.");
//            getMainDishOrder();
//        }
//    }

    public static void getOrder(LinkedHashMap<String, Integer> dishHashMap, String[] dishArray) {
        printMenuOf(dishHashMap);
        int choice = Integer.parseInt(getChoice());
//        String[] dishes = {"Adobo", "Sinigang na Bangus", "Bopiz", "Bicol Express", "Beef Steak"};
        if (choice == 0) {
            getCategoryChoice();
        } else if (isValidChoice(choice, dishArray)) {
            addOrderItem(dishHashMap, dishArray, choice);
            getCategoryChoice();
        } else {
            System.out.println(bunchOfNewLines + "Please enter the number of your choice.");
            getOrder(dishHashMap, dishArray);
        }
    }

    public static boolean isValidChoice(int choice, String[] dishes) {
        return choice > 0 && choice <= dishes.length;
    }


//    public static void getMeryendaOrder() {
//        System.out.println("This is Meryenda menu. Enter 0");
//        String choice = getChoice();
//        switch (choice) {
//            case "0":
//                getCategoryChoice();
//                break;
//            default:
//                getMeryendaOrder();
//        }
//    }

    public static String getChoice() {
        System.out.print("Enter the number of your choice: ");
        return scanner.nextLine();
    }

    public static int getQuantity() {
//        System.out.print("Enter quantity (cannot be <= 0): ");
//        int quantity = scanner.nextInt();
        int quantity = 0;
        while (quantity <= 0) {
            System.out.print("Enter quantity (cannot be <= 0): ");
            quantity = scanner.nextInt();
        }
        return quantity;
    }

    public static void addOrderItem(LinkedHashMap<String, Integer> dishesHashMap, String[] arr, int choice) {
        String name = arr[choice - 1];
        int price = dishesHashMap.get(name);
        OrderItem existingOrder = checkOrderExists(name);
        if (existingOrder != null) {
            existingOrder.incrementQuantity();
            return;
        }
        int quantity = getQuantity();
        OrderItem orderItem = new OrderItem(name, price, quantity);
        OrderItems.add(orderItem);
    }

    public static OrderItem checkOrderExists(String name) {
        for (OrderItem orderItem : OrderItems) {
            if (orderItem.name == name) {
                return orderItem;
            }
        }
        return null;
    }

    public static void printMainMenu() {
        System.out.println("[0] Confirm order");
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("[%d] %s\n", i+1, categories[i]);
        }
    }

    public static void printMenuOf(LinkedHashMap<String, Integer> menu) {
        System.out.println("[0] Go back to main menu.");
        Set<String> menuKeys = menu.keySet();
        int counter = 1;
        for (String key : menuKeys) {
            System.out.printf("[%d] %s\n", counter, key);
            counter++;
        }
    }

//    public static void printSideDish() {
//        System.out.println("[0] Go back to main menu.");
//    }

    public static void confirmOrder() {
        if (OrderItems.isEmpty()) {
            System.out.println(bunchOfNewLines + "You have no orders yet");
            getCategoryChoice();
        }
        int totalCost = 0;
        System.out.print(bunchOfNewLines + "Your orders are:\n");
        System.out.printf("%-24s %-5s %s\n", "Name", "Price", "Quantity");
        System.out.println("-------------------------------------------------");
        for (OrderItem orderItem : OrderItems) {
            orderItem.printOrderItem();
            totalCost += orderItem.netCost;
        }
        System.out.printf("Total cost is:  %d\n", totalCost);
        System.exit(0);
    }
}
