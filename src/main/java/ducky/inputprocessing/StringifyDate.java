package ducky.inputprocessing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class to format date outputs
 *
 * Helps to convert a LocalDateTime object into a human-friendly date representation
 */
public class StringifyDate {
    public static String friendlyDate(LocalDateTime dateTime) {
        String niceDateTime = dateTime.format(DateTimeFormatter.ofPattern("d'th' MMMM yyyy, h:mma"));
        // Replace the th with appropriate suffixes
        if (niceDateTime.startsWith("1th")) {
            niceDateTime = niceDateTime.replace("th" ,"st");
        } else if (niceDateTime.startsWith("2th")) {
            niceDateTime = niceDateTime.replace("th" ,"nd");
        } else if (niceDateTime.startsWith("3th")) {
            niceDateTime = niceDateTime.replace("th" ,"rd");
        }
        // Delete seconds
        if (niceDateTime.contains(":00")) {
            niceDateTime = niceDateTime.replace(":00", "");
        }
        return niceDateTime;
    }
}
