package InventoryManagement;

import adt.LinkedList;

/**
 *
 * @author Yong Cheng Wei
 */
public class Inventory {

    private LinkedList<Ingredient> ingredientStock;
    private int stockCount;
    private final int MAX_STOCK = 100;
    private final int LOW_STOCK_THRESHOLD = 10;

    public Inventory() {
        ingredientStock = new LinkedList<>();
        stockCount = 0;

        addIngredient(new Ingredient("Coconut Rice", 20));
        addIngredient(new Ingredient("Chicken", 20));
        addIngredient(new Ingredient("Noodles", 20));
        addIngredient(new Ingredient("Dough", 20));
        addIngredient(new Ingredient("Beef", 20));
        addIngredient(new Ingredient("Rice Noodles", 20));
        addIngredient(new Ingredient("Tea", 20));
        addIngredient(new Ingredient("Rose Syrup", 20));
        addIngredient(new Ingredient("Durian", 20));
        addIngredient(new Ingredient("Shaved Ice", 50));
    }

    public void decreaseIngredientQuantity(String ingredientName, int quantityToSubtract) {
        Ingredient ingredient = findIngredient(ingredientName);
        if (ingredient != null) {
            if (ingredient.getQuantity() >= quantityToSubtract) {
                ingredient.setQuantity(ingredient.getQuantity() - quantityToSubtract);
                System.out.println("Removed " + quantityToSubtract + " units of " + ingredientName + ". Remaining stock: " + ingredient.getQuantity());
                checkLowStock(ingredientName);
            } else {
                System.out.println("Not enough " + ingredientName + " in stock. Only " + ingredient.getQuantity() + " available.");
            }
        } else {
            System.out.println("Ingredient '" + ingredientName + "' not found in inventory.");
        }
    }

    public void increaseIngredientQuantity(String ingredientName, int quantityToAdd) {
        Ingredient ingredient = findIngredient(ingredientName);
        if (ingredient != null) {
            int newQuantity = ingredient.getQuantity() + quantityToAdd;
            if (newQuantity <= MAX_STOCK) {
                ingredient.setQuantity(newQuantity);
                System.out.println("Added " + quantityToAdd + " units of " + ingredientName + ". New stock: " + ingredient.getQuantity());

                checkLowStock(ingredientName);
            } else {
                System.out.println("Cannot add " + quantityToAdd + " units. Total stock would exceed the maximum limit of " + MAX_STOCK + ".");
            }
        } else {
            System.out.println("Ingredient '" + ingredientName + "' not found in inventory.");
        }
    }

    public int getIngredientStock(String ingredientName) {
        Ingredient ingredient = findIngredient(ingredientName);
        return ingredient != null ? ingredient.getQuantity() : 0;
    }

    public void displayIngredientStock() {
        System.out.println("\nIngredient Stock:");

        LinkedList<Ingredient>.Node currentNode = ingredientStock.getFirstNode();
        int index = 1;

        while (currentNode != null) {
            Ingredient ingredient = currentNode.getData();
            System.out.println(index + ". " + ingredient.getName() + ": " + ingredient.getQuantity());
            currentNode = currentNode.getNext();
            index++;
        }
    }

    public boolean hasIngredient(String ingredientName) {
        return findIngredient(ingredientName) != null;
    }

    private Ingredient findIngredient(String ingredientName) {
        LinkedList<Ingredient>.Node currentNode = ingredientStock.getFirstNode();

        while (currentNode != null) {
            Ingredient ingredient = currentNode.getData();
            if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
                return ingredient;
            }
            currentNode = currentNode.getNext();
        }

        return null;
    }

    private void addIngredient(Ingredient ingredient) {
        if (ingredientStock.getNumberOfEntries() < MAX_STOCK) {
            ingredientStock.add(ingredient);
        } else {
            System.out.println("Inventory is full. Cannot add more ingredients.");
        }
    }

    public void addNewIngredient(String ingredientName, int quantity) {
        if (ingredientStock.getNumberOfEntries() >= MAX_STOCK) {
            System.out.println("Inventory is full. Cannot add more ingredients.");
            return;
        }

        if (findIngredient(ingredientName) != null) {
            System.out.println("Ingredient '" + ingredientName + "' already exists in the inventory.");
        } else if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
        } else {
            ingredientStock.add(new Ingredient(ingredientName, quantity));
            System.out.println("New ingredient '" + ingredientName + "' added with quantity: " + quantity);
        }
    }

    private void checkLowStock(String ingredientName) {
        Ingredient ingredient = findIngredient(ingredientName);
        if (ingredient != null && ingredient.getQuantity() < LOW_STOCK_THRESHOLD) {
            System.out.println("WARNING: Low stock for " + ingredientName + ". Only " + ingredient.getQuantity() + " units remaining.");
        }
    }

    public void displayIndexedIngredientStock() {
        System.out.println("\n====================================");
        System.out.println("           Ingredient Stock          ");
        System.out.println("====================================");
        System.out.printf("%-5s %-20s %-10s\n", "No.", "Ingredient Name", "Quantity");
        System.out.println("------------------------------------");

        LinkedList<Ingredient>.Node currentNode = ingredientStock.getFirstNode();
        int index = 1;

        while (currentNode != null) {
            Ingredient ingredient = currentNode.getData();
            System.out.printf("%-5d %-20s %-10d\n", index++, ingredient.getName(), ingredient.getQuantity());
            currentNode = currentNode.getNext();
        }

        System.out.println("====================================\n");
    }

    public void displayIndexedSortedIngredientsByName() {
        sortByName();

        System.out.println("\n====================================");
        System.out.println("   Ingredient Stock (Sorted by Name)   ");
        System.out.println("====================================");
        System.out.printf("%-5s %-20s %-10s\n", "No.", "Ingredient Name", "Quantity");
        System.out.println("------------------------------------");

        LinkedList<Ingredient>.Node currentNode = ingredientStock.getFirstNode();
        int index = 1;
        while (currentNode != null) {
            Ingredient ingredient = currentNode.getData();
            System.out.printf("%-5d %-20s %-10d\n", index++, ingredient.getName(), ingredient.getQuantity());
            currentNode = currentNode.getNext();
        }

        System.out.println("====================================\n");
    }

    private Ingredient[] copyIngredients() {
        Ingredient[] copiedArray = new Ingredient[stockCount];
        int index = 0;

        LinkedList<Ingredient>.Node currentNode = ingredientStock.getFirstNode();

        while (currentNode != null) {
            copiedArray[index++] = currentNode.getData();
            currentNode = currentNode.getNext();
        }

        return copiedArray;
    }

    public void sortByName() {
        if (ingredientStock.isEmpty() || ingredientStock.getFirstNode() == null || ingredientStock.getFirstNode().getNext() == null) {
            return;
        }

        boolean swapped;
        do {
            swapped = false;
            LinkedList<Ingredient>.Node currentNode = ingredientStock.getFirstNode();

            while (currentNode != null && currentNode.getNext() != null) {
                LinkedList<Ingredient>.Node nextNode = currentNode.getNext();

                if (currentNode.getData().getName().compareToIgnoreCase(nextNode.getData().getName()) > 0) {

                    Ingredient temp = currentNode.getData();
                    currentNode.setData(nextNode.getData());
                    nextNode.setData(temp);

                    swapped = true;
                }

                currentNode = currentNode.getNext();
            }
        } while (swapped);
    }

    public String getIngredientNameByIndex(int index) {
        if (index >= 0 && index < stockCount) {
            LinkedList<Ingredient>.Node currentNode = ingredientStock.getFirstNode();
            int currentIndex = 0;

            while (currentNode != null) {
                if (currentIndex == index) {
                    return currentNode.getData().getName();
                }
                currentNode = currentNode.getNext();
                currentIndex++;
            }
        }
        return null;
    }

}
