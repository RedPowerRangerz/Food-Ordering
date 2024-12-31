package ReservationManagement;

/**
 *
 * @author Yu Han
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class validateDateTime {

    public static boolean validateFutureDate(String dateTimeStr) {
        try {
            LocalDateTime enteredDateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime currentDateTime = LocalDateTime.now();

            if (enteredDateTime.isBefore(currentDateTime)) {
                System.out.println("Error: The entered date and time must be in the future.");
                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use 'yyyy-MM-dd HH:mm'.");
            return false;
        }
    }

    public static boolean validateDateTimeRegex(String dateTime) {
        String dateTimeRegex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]) ([01]\\d|2[0-3]):[0-5]\\d$";
        return dateTime.matches(dateTimeRegex);
    }

    public static boolean validateDateTime(String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime.parse(dateTime, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
