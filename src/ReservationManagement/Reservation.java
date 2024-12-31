package ReservationManagement;

import adt.LinkedList;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author Yu Han
 */
public class Reservation {

    private LocalDateTime reservationTime;
    private String guestName;
    private Table assignedTable;
    private int numberOfGuests;
    private static LinkedList<Reservation> allReservations = new LinkedList<>();
    private static final int MAX_RESERVATIONS = 10;

    public Reservation(LocalDateTime reservationTime, String guestName, Table assignedTable) {
        this.reservationTime = reservationTime;

        this.guestName = guestName;
        this.assignedTable = assignedTable;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public String getGuestName() {
        return guestName;
    }

    public Reservation(LocalDateTime reservationTime, int numberOfGuests, String guestName) {
        if (allReservations.getNumberOfEntries() < MAX_RESERVATIONS) {
            this.reservationTime = reservationTime;
            this.numberOfGuests = numberOfGuests;
            this.guestName = guestName;
            allReservations.add(this);
        } else {
            System.out.println("Reservation limit reached. Cannot make new reservations.");
        }
    }

    public static LinkedList<Reservation> getAllReservations() {
        return allReservations;
    }

    public static int getNumberOfReservations() {
        return allReservations.getNumberOfEntries();
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public static Reservation createReservation(LocalDateTime reservationTime, int numberOfGuests, String guestName) {
        if (allReservations.getNumberOfEntries() < MAX_RESERVATIONS) {
            Reservation newReservation = new Reservation(reservationTime, numberOfGuests, guestName);
            allReservations.add(newReservation);
            return newReservation;
        } else {
            System.out.println("Reservation limit reached. Cannot make new reservations.");
            return null;
        }
    }

    private static int getValidIntInput(Scanner scanner, String errorMessage) {
        while (!scanner.hasNextInt()) {
            System.out.println(errorMessage);
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

}
