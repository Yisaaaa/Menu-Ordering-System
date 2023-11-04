
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
    private final static Scanner scanner = new Scanner(System.in);
    private final static String[] categories = {"Karne Meals", "Gulay Meals", "Desserts", "Rice"};
    private final static ArrayList<MenuItem> KarneMeals = new ArrayList<>();
    private final static ArrayList<MenuItem> GulayMeals = new ArrayList<>();
    private final static ArrayList<MenuItem> Desserts = new ArrayList<>();
    private static ArrayList<OrderItem> OrderItems = new ArrayList<>();
    private static MenuItem Rice = new MenuItem("Rice", 12);
    private static String bunchOfNewLines = "";

    static {
        KarneMeals.add(new MenuItem("Adobo", 40));
        KarneMeals.add(new MenuItem("Sinigang", 40));
        KarneMeals.add(new MenuItem("Sarciado", 40));
        KarneMeals.add(new MenuItem("Beef Steak", 40));
        KarneMeals.add(new MenuItem("Nilaga", 40));

        GulayMeals.add(new MenuItem("Pinakbet", 30));
        GulayMeals.add(new MenuItem("Toge", 30));
        GulayMeals.add(new MenuItem("Munggo", 30));
        GulayMeals.add(new MenuItem("Chopsuey", 30));
        GulayMeals.add(new MenuItem("Ampalaya", 30));

        Desserts.add(new MenuItem("Halo-halo", 60));
        Desserts.add(new MenuItem("Ice cream", 25));
        Desserts.add(new MenuItem("Waffles", 30));
        Desserts.add(new MenuItem("Leche flan", 90));
        Desserts.add(new MenuItem("Chocolate cake", 120));
        for (int i = 0; i <= 100; i++) {
            bunchOfNewLines += "\n";
        }
    }


    public static void main(String[] args) {
        getCategoryChoice();
    }

    public static void getCategoryChoice() {
        printMainMenu();
        String choice = getChoice().toLowerCase();
        switch (choice) {
            case "exit":
                System.out.println("Thank you!");
                System.exit(0);
            case "0":
                confirmOrder();
            case "1":
                getOrder(KarneMeals);
                break;
            case "2":
                getOrder(GulayMeals);
                break;
            case "3":
                getOrder(Desserts);
                break;
            case "4":
                getRiceOrder();
                break;
            default:
                System.out.println(bunchOfNewLines + "Please enter the number of your choice.");
                getCategoryChoice();
        }
    }

    public static void getOrder(ArrayList<MenuItem> menu) {
        printMenuOf(menu);
        int choice = Integer.parseInt(getChoice());

        if (choice == 0) {
            getCategoryChoice();
        } else if (isValidChoice(choice, menu)) {
            addOrderItem(menu, choice);
            System.out.println(bunchOfNewLines);
            getCategoryChoice();
        } else {
//            System.out.println(bunchOfNewLines + "Please enter the number of your choice.");
            System.out.println(bunchOfNewLines + "Please enter the number of your choice.");
            getOrder(menu);
        }
    }

    public static void getRiceOrder() {
        System.out.print("Enter how many order of rice: ");
        int quantity = -1;
        while (quantity < 0) {
            quantity = scanner.nextInt();
            scanner.nextLine(); // To consume the newline left by nextInt
        }

        if (quantity == 0) {
            getCategoryChoice();;
        } else {
            OrderItem existingOrder = checkOrderExists("Rice");
            if (existingOrder != null) {
                existingOrder.incrementQuantityBy(quantity);
            } else {
                OrderItem order = new OrderItem(Rice.getName(), Rice.getPrice(), quantity);
                OrderItems.add(order);
                getCategoryChoice();
            }
        }
    }

    public static boolean isValidChoice(int choice, ArrayList<MenuItem> menu) {
        return choice > 0 && choice <= menu.size();
    }

    public static String getChoice() {
        System.out.print("Enter the number of your choice (or 'exit'): ");
        return scanner.nextLine();
    }

    public static int getQuantity() {
        int quantity = 0;
        while (quantity <= 0) {
            System.out.print("Enter quantity (cannot be <= 0): ");
            quantity = scanner.nextInt();
            scanner.nextLine();  // To consume the newline left by nextInt
        }
        return quantity;
    }

    public static void addOrderItem(ArrayList<MenuItem> menu, int choice) {
        MenuItem item = menu.get(choice - 1);
        String name = item.getName();
        int price = item.getPrice();

        OrderItem existingOrder = checkOrderExists(name);
        if (existingOrder != null) {
            existingOrder.incrementQuantityBy(getQuantity());
            return;
        }

        int quantity = getQuantity();
        OrderItem orderItem = new OrderItem(name, price, quantity);
        OrderItems.add(orderItem);
    }

    public static OrderItem checkOrderExists(String name) {
        for (OrderItem orderItem : OrderItems) {
            if (orderItem.name.equals(name)) {
                return orderItem;
            }
        }
        return null;
    }

    public static void printMainMenu() {
        System.out.println("Enter 'exit' to exit.");
        System.out.println("[0] Confirm order");
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("[%d] %s\n", i+1, categories[i]);
        }
    }

    public static void printMenuOf(ArrayList<MenuItem> menu) {
        System.out.println("[0] Go back to main menu.");
        int counter = 1;
        for (MenuItem item : menu) {
            System.out.printf("[%d] %s\n", counter, item.getName());
            counter++;
        }
    }

    public static void processPayment(int totalCost) {
        System.out.print("Please enter your money: ");
        int payment = Integer.parseInt(scanner.nextLine());
        if (payment < totalCost) {
            System.out.println("Sorry, your money is not enough");
            processPayment(totalCost);
        }
        else {
            int change = payment - totalCost;
            System.out.printf("Thank you. Here's your change: %d\n", change);
        }
    }

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

        processPayment(totalCost);

        System.exit(0);
    }
}

