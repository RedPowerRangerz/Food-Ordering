package OrderManagement;

import BillingManagement.Food;
import adt.LinkedList;

/**
 *
 * @author Chee Jun Yin
 */
public class Order {

    private LinkedList<Food> orderItems;

    public Order() {
        orderItems = new LinkedList<>();
    }

    public void addOrderItem(Food food) {
        orderItems.add(food);
    }

    public LinkedList<Food> getOrderItems() {
        return orderItems;
    }

    public double getOrderTotal() {
        double total = 0.0;
        for (int i = 1; i <= orderItems.getNumberOfEntries(); i++) {
            Food food = orderItems.getEntry(i);
            total += food.getPrice();
        }
        return total;
    }

    public void displayOrderItems() {
        System.out.println("Order Items:");
        for (int i = 1; i <= orderItems.getNumberOfEntries(); i++) {
            Food food = orderItems.getEntry(i);
            System.out.println(" - " + food.getName());
        }
    }

}
