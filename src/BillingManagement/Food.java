package BillingManagement;

import adt.Item;

/**
 *
 * @author Lim Wei Jian
 */
public class Food implements Item {

    private String name;
    private double price;
    private int quantity;
    private String mainIngredient;

    public Food(String name, double price, String mainIngredient) {
        this.name = name;
        this.price = price;
        this.mainIngredient = mainIngredient;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getTotalPrice() {
        return price * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount > 0) {
            quantity += amount;
        } else {
            System.out.println("Invalid quantity increase amount.");
        }
    }

    public boolean decreaseQuantity(int amount) {
        if (amount > 0 && quantity >= amount) {
            quantity -= amount;
            return true;
        } else {
            System.out.println("Insufficient quantity or invalid amount.");
            return false;
        }
    }

    public String getIngredient() {
        return mainIngredient;
    }

}
