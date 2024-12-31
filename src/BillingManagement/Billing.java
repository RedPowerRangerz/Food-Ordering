package BillingManagement;

import OrderManagement.Order;
import ReservationManagement.Table;
import adt.LinkedList;

/**
 *
 * @author Lim Wei Jian
 */
public class Billing {

    private static final double TAX_RATE = 0.06;
    private static final double SERVICE_CHARGE_RATE = 0.10;
    private static final double DISCOUNT_PERCENTAGE = 0.05;

    public static double calculateTableBill(Table table) {
        double subtotal = 0.0;
        LinkedList<Order> orders = table.getOrders();

        if (orders == null || orders.isEmpty()) {
            return 0.0;
        }

        for (int i = 1; i <= orders.getNumberOfEntries(); i++) {
            Order order = orders.getEntry(i);
            subtotal += order.getOrderTotal();
        }

        double discountAmount = subtotal * DISCOUNT_PERCENTAGE;
        double discountedSubtotal = subtotal - discountAmount;
        double taxAmount = discountedSubtotal * TAX_RATE;
        double serviceChargeAmount = discountedSubtotal * SERVICE_CHARGE_RATE;
        double totalBill = discountedSubtotal + taxAmount + serviceChargeAmount;

        return totalBill;
    }

    public static void printBillBreakdown(Table table) {
        double subtotal = 0.0;
        LinkedList<Order> orders = table.getOrders();

        System.out.println("\n--- Bill Breakdown for Table " + table.getTableNumber() + " ---");

        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders for this table.");
            System.out.println("------------------------------------");
            return;
        }

        for (int i = 1; i <= orders.getNumberOfEntries(); i++) {
            Order order = orders.getEntry(i);
            System.out.println("Order " + i + ": RM" + String.format("%.2f", order.getOrderTotal()));
            subtotal += order.getOrderTotal();
        }
        System.out.println("------------------------------------");
        System.out.println("Subtotal: RM" + String.format("%.2f", subtotal));

        double discountAmount = subtotal * DISCOUNT_PERCENTAGE;
        System.out.println("Discount (" + (DISCOUNT_PERCENTAGE * 100) + "%): -RM" + String.format("%.2f", discountAmount));

        double discountedSubtotal = subtotal - discountAmount;
        System.out.println("Discounted Subtotal: RM" + String.format("%.2f", discountedSubtotal));

        double taxAmount = discountedSubtotal * TAX_RATE;
        System.out.println("Tax (" + (TAX_RATE * 100) + "%): RM" + String.format("%.2f", taxAmount));

        double serviceChargeAmount = discountedSubtotal * SERVICE_CHARGE_RATE;
        System.out.println("Service Charge (" + (SERVICE_CHARGE_RATE * 100) + "%): RM" + String.format("%.2f", serviceChargeAmount));

        double totalBill = discountedSubtotal + taxAmount + serviceChargeAmount;
        System.out.println("------------------------------------");
        System.out.println("Total Bill: RM" + String.format("%.2f", totalBill));
        System.out.println("------------------------------------");
    }

    public static double getTaxRate() {
        return TAX_RATE;
    }

    public static double getServiceChargeRate() {
        return SERVICE_CHARGE_RATE;
    }
}
