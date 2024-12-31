package ReservationManagement;

import adt.LinkedList;
import OrderManagement.Order;

/**
 *
 * @author Yu Han
 */
public class Table {

    private int tableNumber;
    private TableStatus status;
    private LinkedList<Order> orders;
    private LinkedList<Order> orderHistory;

    public enum TableStatus {
        AVAILABLE, RESERVED, OCCUPIED
    }

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.status = Table.TableStatus.AVAILABLE;
        this.orders = new LinkedList<>();
        this.orderHistory = new LinkedList<>();
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }

    public void addOrder(Order order) {
        orders.add(order);
        orderHistory.add(order);
        this.status = TableStatus.OCCUPIED;
    }

    public LinkedList<Order> getOrders() {
        return orders;
    }

    public void displayOrders() {
        System.out.println("Orders for Table " + tableNumber + ":");
        for (int i = 1; i <= orders.getNumberOfEntries(); i++) {
            Order order = orders.getEntry(i);
            System.out.println("Order " + i + ":");
            order.displayOrderItems();
        }
    }

    public void displayOrderHistory() {
        System.out.println("Order History for Table " + tableNumber + ":");
        if (orderHistory.isEmpty()) {
            System.out.println("No orders found for this table.");
        } else {
            for (int i = 1; i <= orderHistory.getNumberOfEntries(); i++) {
                Order order = orderHistory.getEntry(i);
                System.out.println("Order " + i + ":");
                order.displayOrderItems();
                System.out.println();
            }
        }
    }

    public void clearOrders() {
        orders.clear();
        this.status = TableStatus.AVAILABLE;

    }

    public static void displayAllTableStatuses(Table[] tables) {
        System.out.println("\n--- Table Statuses ---");
        System.out.printf("%-10s %-15s\n", "Table No.", "Status");

        for (Table table : tables) {
            System.out.printf("%-10d %-15s\n", table.getTableNumber(), table.getStatus());
        }
        System.out.println("----------------------");
    }

}
