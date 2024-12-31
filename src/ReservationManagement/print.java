package ReservationManagement;

import ReservationManagement.Reservation;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Yu Han
 */
public class print {

    public static void printReservations(int index, int size) {
        if (index > size) {
            return;
        }

        Reservation reservation = Reservation.getAllReservations().getEntry(index);

        System.out.println("Reservation Time: "
                + reservation.getReservationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("Number of Guests: " + reservation.getNumberOfGuests());
        System.out.println("Guest Name: " + reservation.getGuestName());
        System.out.println("----------------------");

        printReservations(index + 2, size);

    }

}
