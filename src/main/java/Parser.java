import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    public Parser() {}

    public static ArrayList<Object> parse(String taskType, String input) throws DuckyExceptions {
        ArrayList<Object> parsed = new ArrayList<Object>();

        String desc = input.split("/")[0].trim();
        if (desc.isEmpty()) {
            throw new EmptyDescException();
        }
        switch (taskType) {
        case "TODO":
            parsed.add(desc);
            break;

        case "DEADLINE":
            String[] descAndDate = input.split("/by", 2);
            // Either no "/by" or "/by" is empty
            if (descAndDate.length == 1) {
                throw new EmptyDateException("'/by'");
            }
            LocalDateTime date = parseDate(descAndDate[1].trim(),"'/by'");
            parsed.add(desc);
            parsed.add(date);
            break;

        case "EVENT":
            if (!input.contains("/from")) {
                throw new EmptyDateException("'/from'");
            }
            String[] descAndFromTo = input.split("/from", 2);

            if (descAndFromTo.length == 1 || descAndFromTo[1].trim().isEmpty()) {
                throw new InvalidDateException("'/from'");
            }

            if (!input.contains("/to")) {
                throw new EmptyDateException("'/to'");
            }
            String[] descFromAndTo = input.split("/to",2);

            if (descFromAndTo.length == 1 || descFromAndTo[1].trim().isEmpty()) {
                throw new InvalidDateException("'/to'");
            }
            parsed.add(desc);
            parsed.add(descAndFromTo[1].split("/")[0].trim());
            parsed.add(descFromAndTo[1].trim());
            break;
        }
        return parsed;
    }

    /**
     * Returns a standardised
     * @param date A string-form date
     * @return An ISO8601-format date
     */
    public static LocalDateTime parseDate(String date, String fieldName) throws InvalidDateException {
        String[] dateAndTime = date.split(" ");
        if (dateAndTime.length == 2) {
            DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            try {
                return LocalDateTime.parse(date, customFormat);
            } catch (DateTimeParseException e) {
                throw new InvalidDateException(fieldName);
            }
        }
        throw new InvalidDateException(fieldName);
    }
}
