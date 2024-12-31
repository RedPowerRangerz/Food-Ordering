package Main;

import BillingManagement.Billing;
import BillingManagement.Food;
import InventoryManagement.Inventory;
import OrderManagement.Menu;
import OrderManagement.Order;
import ReservationManagement.Reservation;
import ReservationManagement.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Yong Cheng Wei
 */
public class Restaurant {

    private static int getValidIntInput(Scanner scanner, String errorMessage) {
        while (!scanner.hasNextInt()) {
            System.out.println(errorMessage);
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Table[] tables = new Table[10];
        Menu menu = new Menu();

        for (int i = 0; i < tables.length; i++) {
            tables[i] = new Table(i + 1);
        }

        Scanner scanner = new Scanner(System.in);
        int userChoice = 0;

        do {
            System.out.println("\nRestaurant System");
            System.out.printf("%-3s %-30s\n", "No.", "Action");
            System.out.printf("%-3s %-30s\n", "1.", "Order Item");
            System.out.printf("%-3s %-30s\n", "2.", "Make A Reservation");
            System.out.printf("%-3s %-30s\n", "3.", "Display All Reservations");
            System.out.printf("%-3s %-30s\n", "4.", "Display Order History");
            System.out.printf("%-3s %-30s\n", "5.", "Billing Management");
            System.out.printf("%-3s %-30s\n", "6.", "Ingredient Management");
            System.out.printf("%-3s %-30s\n", "7.", "Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1 and 6.");
                scanner.nextLine();
                continue;
            }

            userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:

                    System.out.println("\n1. Sort by Name ");
                    System.out.println("2. Sort by Price ");
                    System.out.println("How would you like the menu to be sorted? (1/2): ");

                    int sort = getValidIntInput(scanner, "Invalid input! Please enter valid sorting number");

                    switch (sort) {
                        case 1:
                            menu.sortByName();
                            System.out.println("\nMenu (Sorted by Name):");
                            menu.displayMenu();
                            break;

                        case 2:
                            menu.sortByPrice();
                            System.out.println("\nMenu (Sorted by Price):");
                            menu.displayMenu();
                            break;

                        default:
                            System.out.println("Invalid choice! Menu will be sorted by default or previous choice!");
                            menu.displayMenu();
                            break;
                    }

                    System.out.print("Enter the index of the food item to order: ");
                    int foodIndex = getValidIntInput(scanner, "Invalid input! Please enter a valid food index.");

                    if (foodIndex < 1 || foodIndex > menu.getMenuItemsCount()) {
                        System.out.println("Invalid food index.");
                        break;
                    }

                    Food selectedFood = menu.getMenuItem(foodIndex);
                    if (selectedFood == null) {
                        System.out.println("Invalid food selection. Please try again.");
                        break;
                    }

                    String ingredient = selectedFood.getIngredient();
                    if (ingredient == null || !inventory.hasIngredient(ingredient)) {
                        System.out.println("Sorry, " + selectedFood.getName() + " cannot be made as the ingredient '" + ingredient + "' is not in the inventory.");
                        break;
                    }

                    System.out.print("Enter the quantity to order: ");
                    int quantityToOrder = getValidIntInput(scanner, "Invalid quantity. Please enter a number.");

                    if (quantityToOrder <= 0) {
                        System.out.println("Invalid quantity. Please enter a positive number.");
                        break;
                    }

                    int availableStock = inventory.getIngredientStock(ingredient);

                    if (availableStock < quantityToOrder) {
                        System.out.println("Not enough " + selectedFood.getName() + " in stock because only " + availableStock + " units of " + ingredient + " available.");
                        break;
                    }

                    inventory.decreaseIngredientQuantity(ingredient, quantityToOrder);

                    Order newOrder = new Order();
                    for (int i = 0; i < quantityToOrder; i++) {
                        newOrder.addOrderItem(selectedFood);
                    }

                    Table.displayAllTableStatuses(tables);
                    System.out.print("Enter a table number(1 - 10): ");
                    int tableNumber = getValidIntInput(scanner, "Invalid input! Please enter a valid table number.");

                    if (tableNumber < 1 || tableNumber > tables.length) {
                        System.out.println("Invalid table number.");
                        break;
                    }

                    Table selectedTable = tables[tableNumber - 1];
                    selectedTable.addOrder(newOrder);
                    System.out.println(quantityToOrder + " order(s) of " + selectedFood.getName() + " added to Table " + tableNumber + ".");
                    break;

                case 2:
                    System.out.print("Enter the reservation date and time (YYYY-MM-dd HH:mm): ");
                    String dateTimeStr = scanner.nextLine();

                    if (!ReservationManagement.validateDateTime.validateFutureDate(dateTimeStr)) {
                        System.out.println("Please enter a valid future date and time.");
                        break;
                    }

                    if (!ReservationManagement.validateDateTime.validateDateTimeRegex(dateTimeStr)) {
                        System.out.println("Invalid format! Please use yyyy-MM-dd HH:mm.");
                        break;
                    }

                    if (!ReservationManagement.validateDateTime.validateDateTime(dateTimeStr)) {
                        System.out.println("Invalid date or time! Please enter a valid date.");
                        break;
                    }

                    LocalDateTime reservationTime = LocalDateTime.parse(
                            dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    );

                    System.out.print("Enter the number of guests: ");
                    int numberOfGuests = getValidIntInput(scanner, "Invalid input! Please enter a valid number of guests.");
                    scanner.nextLine();

                    System.out.print("Enter your name: ");
                    String guestName = scanner.nextLine();

                    Table.displayAllTableStatuses(tables);
                    System.out.print("Enter the table number to reserve (1-10): ");
                    int tableNumberToReserve = getValidIntInput(scanner, "Invalid table number. Please enter a valid number.");
                    scanner.nextLine();

                    if (tableNumberToReserve < 1 || tableNumberToReserve > tables.length) {
                        System.out.println("Invalid table number.");
                        break;
                    }

                    Table selectedTableToReserve = tables[tableNumberToReserve - 1];

                    if (selectedTableToReserve.getStatus() == Table.TableStatus.RESERVED) {
                        System.out.println("Table " + tableNumberToReserve + " is already reserved.");
                        break;
                    } else if (selectedTableToReserve.getStatus() == Table.TableStatus.OCCUPIED) {
                        System.out.println("Table " + tableNumberToReserve + " is currently occupied. Please choose another table.");
                        break;
                    }

                    Reservation reservation = Reservation.createReservation(reservationTime, numberOfGuests, guestName);

                    if (reservation != null) {
                        System.out.println("Reservation created successfully for Table " + tableNumberToReserve + "!");
                        selectedTableToReserve.setStatus(Table.TableStatus.RESERVED);
                        Table.displayAllTableStatuses(tables);
                    }

                    break;

                case 3:
                    if (!Reservation.getAllReservations().isEmpty()) {
                        System.out.println("All Reservations:");

                        int size = Reservation.getAllReservations().getNumberOfEntries();
                        ReservationManagement.print.printReservations(1, size);

                        System.out.println("Do you wanna Print all the Reservation? (yes/no): ");
                        String inputChoice = scanner.nextLine().trim().toLowerCase();

                        if (inputChoice.equals("yes") || inputChoice.equals("y")) {
                            System.out.println("Printing all reservations...");

                            System.out.println("Reservations have been printed successfully.");
                        } else if (inputChoice.equals("no") || inputChoice.equals("n")) {
                            System.out.println("Returning to reservation menu...");
                        } else {
                            System.out.println("Invalid input. Returning to reservation menu...");
                        }
                    } else {
                        System.out.println("No reservations available.");
                    }
                    break;

                case 4:
                    System.out.print("Enter the table number: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input! Please enter a valid table number.");
                        scanner.nextLine();
                        break;
                    }

                    tableNumber = scanner.nextInt();
                    scanner.nextLine();

                    if (tableNumber < 1 || tableNumber > tables.length) {
                        System.out.println("Invalid table number.");
                        break;
                    }

                    tables[tableNumber - 1].displayOrderHistory();
                    break;

                case 5:
                    System.out.print("Enter the table number: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input! Please enter a valid table number.");
                        scanner.nextLine();
                        break;
                    }

                    tableNumber = scanner.nextInt();
                    scanner.nextLine();

                    if (tableNumber < 1 || tableNumber > tables.length) {
                        System.out.println("Invalid table number.");
                        break;
                    }

                    selectedTable = tables[tableNumber - 1];
                    if (selectedTable.getOrders() == null || selectedTable.getOrders().isEmpty()) {
                        System.out.println("No orders for Table " + tableNumber + ".");
                        break;
                    }

                    double totalBill = Billing.calculateTableBill(selectedTable);

                    Billing.printBillBreakdown(selectedTable);

                    System.out.println("Total bill (including tax, service charge, and 5% discount): RM" + String.format("%.2f", totalBill));

                    System.out.println("Select payment method:");
                    System.out.println("1. Cash");
                    System.out.println("2. Card");

                    int paymentChoice = 0;
                    do {
                        System.out.print("Enter your choice (1 or 2): ");
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input! Please enter 1 for Cash or 2 for Card.");
                            scanner.nextLine();
                            continue;
                        }
                        paymentChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (paymentChoice != 1 && paymentChoice != 2) {
                            System.out.println("Invalid choice! Please enter 1 for Cash or 2 for Card.");
                        }
                    } while (paymentChoice != 1 && paymentChoice != 2);

                    if (paymentChoice == 1) {
                        System.out.println("Payment received in cash.");
                    } else {
                        System.out.print("Enter card number: ");
                        String cardNumber = scanner.nextLine();

                        System.out.print("Enter CVC: ");
                        String cvc = scanner.nextLine();

                        System.out.println("Payment of RM" + String.format("%.2f", totalBill) + " received by card (**** **** **** " + cardNumber.substring(Math.max(0, cardNumber.length() - 4)) + ").");

                    }

                    selectedTable.clearOrders();
                    break;

                case 6:
                    System.out.println("\nChoose an option:");
                    System.out.println("1. Add stock to an existing ingredients");
                    System.out.println("2. Add a new ingredient");
                    System.out.println("3. View all ingredients");
                    System.out.print("Enter your choice: ");

                    int subChoice = getValidIntInput(scanner, "Invalid choice. Please enter 1 or 2.");
                    scanner.nextLine();

                    switch (subChoice) {
                        case 1:
                            inventory.displayIndexedIngredientStock();
                            System.out.print("Enter the ingredient name to add stock to: ");
                            String ingredientName = scanner.nextLine().trim();

                            if (!inventory.hasIngredient(ingredientName)) {
                                System.out.println("Ingredient '" + ingredientName + "' not found in inventory.");
                                break;
                            }

                            System.out.print("Enter the quantity to add: ");
                            int quantityToAdd = getValidIntInput(scanner, "Invalid quantity. Please enter a valid quantity.");
                            scanner.nextLine();

                            if (quantityToAdd <= 0) {
                                System.out.println("Invalid quantity. Please enter a positive number.");
                                break;
                            }

                            inventory.increaseIngredientQuantity(ingredientName, quantityToAdd);
                            break;

                        case 2:
                            System.out.print("Enter the name of the new ingredient: ");
                            String newIngredientName = scanner.nextLine().trim();

                            if (inventory.hasIngredient(newIngredientName)) {
                                System.out.println("Ingredient '" + newIngredientName + "' already exists in inventory.");
                                break;
                            }

                            System.out.print("Enter the initial quantity of the new ingredient: ");
                            int newIngredientQuantity = getValidIntInput(scanner, "Invalid quantity. Please enter a valid quantity.");
                            scanner.nextLine();

                            if (newIngredientQuantity <= 0) {
                                System.out.println("Invalid quantity. Please enter a positive number.");
                                break;
                            }

                            inventory.addNewIngredient(newIngredientName, newIngredientQuantity);
                            break;

                        case 3:
                            System.out.println("Choose how to display the inventory:");
                            System.out.println("1. Original order");
                            System.out.println("2. Sorted by name");
                            System.out.print("Enter your choice: ");

                            int displayChoice = getValidIntInput(scanner, "Invalid choice. Please enter 1, 2, or 3.");
                            scanner.nextLine();

                            switch (displayChoice) {
                                case 1:
                                    System.out.println("Current Inventory Stock (Original Order):");
                                    inventory.displayIndexedIngredientStock();
                                    break;
                                case 2:
                                    System.out.println("Current Inventory Stock (Sorted by Name):");
                                    inventory.displayIndexedSortedIngredientsByName();
                                    break;
                            }
                    }
                    break;

                case 7:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }

        } while (userChoice != 7);

        scanner.close();
    }

}
