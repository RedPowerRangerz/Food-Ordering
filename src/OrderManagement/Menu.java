package OrderManagement;

import BillingManagement.Food;
import adt.LinkedList;

/**
 *
 * @author Chee Jun Yin
 */
public class Menu {

    private LinkedList<Food> menuItems;

    public Menu() {
        menuItems = new LinkedList<>();
        menuItems.add(new Food("Nasi Lemak", 25.00, "Coconut Rice"));
        menuItems.add(new Food("Chicken Rice", 12.00, "Chicken"));
        menuItems.add(new Food("Mee Goreng Mamak", 7.00, "Noodles"));
        menuItems.add(new Food("Roti Canai", 2.50, "Dough"));
        menuItems.add(new Food("Satay", 1.50, "Chicken/Beef"));
        menuItems.add(new Food("Char Kuey Teow", 9.00, "Rice Noodles"));
        menuItems.add(new Food("Teh Tarik", 3.00, "Tea"));
        menuItems.add(new Food("Sirap Bandung", 4.00, "Rose Syrup"));
        menuItems.add(new Food("Durian Crepe", 15.00, "Durian"));
        menuItems.add(new Food("ABC (Air Batu Campur)", 6.00, "Shaved Ice"));
    }

    public void addMenuItem(Food food) {
        menuItems.add(food);
    }

    public void setMenuItems(LinkedList<Food> menuItems) {
        this.menuItems = menuItems;
    }

    public void displayMenu() {
        if (menuItems.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }

        System.out.println("\n--- Today's Menu ---");

        int maxNameLength = 0;
        for (int i = 1; i <= menuItems.getNumberOfEntries(); i++) {
            Food food = menuItems.getEntry(i);
            if (food != null) {
                maxNameLength = Math.max(maxNameLength, food.getName().length());
            }
        }

        for (int i = 1; i <= menuItems.getNumberOfEntries(); i++) {
            Food food = menuItems.getEntry(i);
            if (food != null) {

                System.out.printf("%-4d%-" + (maxNameLength + 3) + "sRM%.2f%n", i, food.getName(), food.getPrice());
            } else {
                System.out.println(i + ". [Error: Food item is null]");
            }
        }

        System.out.println("--------------------");
    }

    public Food getMenuItem(int index) {
        if (index < 1 || index > menuItems.getNumberOfEntries()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return menuItems.getEntry(index);
    }

    public int getMenuItemsCount() {
        return menuItems.getNumberOfEntries();
    }

    public void sortByName() {
        for (int i = 1; i <= menuItems.getNumberOfEntries(); i++) {
            for (int j = 1; j <= menuItems.getNumberOfEntries() - i; j++) {
                Food food1 = menuItems.getEntry(j);
                Food food2 = menuItems.getEntry(j + 1);
                if (food1.getName().compareTo(food2.getName()) > 0) {
                    menuItems.replace(j, food2);
                    menuItems.replace(j + 1, food1);
                }
            }
        }
    }

    public void sortByPrice() {
        for (int i = 1; i <= menuItems.getNumberOfEntries(); i++) {
            for (int j = 1; j <= menuItems.getNumberOfEntries() - i; j++) {
                Food food1 = menuItems.getEntry(j);
                Food food2 = menuItems.getEntry(j + 1);
                if (food1.getPrice() > food2.getPrice()) {
                    menuItems.replace(j, food2);
                    menuItems.replace(j + 1, food1);
                }
            }
        }
    }
}
