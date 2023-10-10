public class OrderItem {

    String name;
    int price, quantity, netCost;

    public OrderItem(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.netCost = quantity * price;
    }

    public void printOrderItem() {
        System.out.printf(
                """
                %-25s %-4d    %dx
                """, this.name, this.price, this.quantity
        );
    }

    public void incrementQuantity() {
        this.quantity++;
        this.netCost = this.price * this.quantity;
    }
}
