import java.util.ArrayList;

public class Parser {
    public Parser() {}

    public static ArrayList<String> parse(String taskType, String input) throws DuckyExceptions {
        ArrayList<String> parsed = new ArrayList<String>();

        String desc = input.split("/")[0].trim();
        if (desc.isEmpty()) {
            throw new EmptyDescException();
        }
        switch (taskType) {
        case "T":
            parsed.add(desc);
            break;

        case "D":
            if (!input.contains("/by")) {
                throw new EmptyDateException("'/by'");
            }

            String[] descAndDate = input.split("/by", 2);
            if (descAndDate.length == 1 || descAndDate[1].trim().isEmpty()) {
                throw new InvalidDateException("'/by'");
            }
            parsed.add(desc);
            parsed.add(descAndDate[1].trim());
            break;

        case "E":
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
}
