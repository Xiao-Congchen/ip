package ducky.inputprocessing;

import ducky.command.Command;
import ducky.command.AddCmd;
import ducky.command.ByeCmd;
import ducky.command.FindCmd;
import ducky.command.ListCmd;
import ducky.command.MarkCmd;
import ducky.command.DeleteCmd;

import ducky.datahandling.TaskList;
import ducky.exception.DateConflictException;
import ducky.exception.DateRangeException;
import ducky.exception.DuckyException;
import ducky.exception.EmptyCommandException;
import ducky.exception.EmptyDateException;
import ducky.exception.InvalidCommandException;
import ducky.exception.EmptyDescException;
import ducky.exception.EmptySelectorException;
import ducky.exception.InvalidSelectorException;
import ducky.exception.InvalidDateException;

import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The Parser helps to process evey user input into an appropriate Command.
 * Possible command types include: Add, Bye, List, Mark, Delete.
 * It will also throw the relevant exceptions faced while parsing.
 */
public class Parser {
    // lastCmd can be used to determine dialog box styling and program termination
    static String lastCmd = "SUCCESS";

    /**
     * Returns a specific Command object based on input.
     * Appropriate DuckyException will be thrown during the validation process.
     *
     * @param input User-defined command string.
     * @param listSize Size of current task list.
     * @return Command object.
     * @throws DuckyException if presence and type validations fail.
     */
    public static Command parse(String input, TaskList tasklist, int listSize) throws DuckyException {
        input = input.trim(); // Removes leading and trailing spaces
        if (input.isEmpty()) {
            lastCmd = "ERROR";
            throw new EmptyCommandException();
        }
        String[] keywordAndRest = input.split(" ", 2);
        String cmdType = keywordAndRest[0].toUpperCase();
        if (keywordAndRest[0].equalsIgnoreCase("bye")) {
            lastCmd = "BYE";
            return new ByeCmd();
        }

        String rest = keywordAndRest.length == 2 ? keywordAndRest[1] : "";
        String desc = rest.split("/")[0].trim();

        switch (cmdType) {
        // Claude Sonnet 3.5 used to abstract out todo, deadline and event parsing into separate methods
        case "TODO":
            return handleTodoCommand(desc);
        case "DEADLINE":
            return handleDeadlineCommand(input, desc);
        case "EVENT":
            return handleEventCommand(input, desc, tasklist);
        case "FIND":
            lastCmd = "SUCCESS";
            return new FindCmd(desc);
        case "LIST":
            lastCmd = "LIST";
            return new ListCmd();
        case "MARK":
            int markId = isValidSelector(rest, "mark", listSize);
            lastCmd = "SUCCESS";
            return new MarkCmd(markId, true);
        case "UNMARK":
            int unmarkId = isValidSelector(rest, "unmark", listSize);
            lastCmd = "SUCCESS";
            return new MarkCmd(unmarkId, false);
        case "DELETE":
            int delId = isValidSelector(rest, "delete", listSize);
            assert delId >= 0;  // isValidateSelector should only return a valid id
            lastCmd = "DEL";
            return new DeleteCmd(delId);
        case "CLEARALL":
            lastCmd = "DEL";
            return new DeleteCmd(-1);
        default:
            lastCmd = "ERROR";
            throw new InvalidCommandException();
        }
    }

    private static Command handleTodoCommand(String desc) throws EmptyDescException {
        if (isValidDesc("Todo task", desc)) {
            ArrayList<Object> parsed = new ArrayList<>();
            parsed.add(desc);
            lastCmd = "SUCCESS";
            return new AddCmd("T", parsed);
        }
        return null;
    }

    private static Command handleDeadlineCommand(String input, String desc) throws DuckyException {
        if (isValidDesc("Deadline task", desc)) {
            String[] descAndDate = input.split("/by", 2);
            // Either no "/by" or no date after "/by"
            if (descAndDate.length == 1) {
                lastCmd = "ERROR";
                throw new EmptyDateException("'/by'");
            }
            LocalDateTime date = parseDate(descAndDate[1].trim(), "'/by'");
            ArrayList<Object> parsed = new ArrayList<>();
            parsed.add(desc);
            parsed.add(date);
            lastCmd = "SUCCESS";
            return new AddCmd("D", parsed);
        }
        return null;
    }

    private static Command handleEventCommand(String input, String desc, TaskList tasklist) throws DuckyException {
        if (isValidDesc("Event", desc)) {
            String[] descAndFromTo = input.split("/from", 2);
            // Either no "/from" or no date after "/from"
            if (descAndFromTo.length == 1) {
                lastCmd = "ERROR";
                throw new EmptyDateException("'/from'");
            }
            LocalDateTime from = parseDate(descAndFromTo[1].split("/to")[0].trim(), "'/from'");

            String[] fromAndTo = input.split("/to", 2);
            // Either no "/to" or no date after "/to"
            if (fromAndTo.length == 1) {
                lastCmd = "ERROR";
                throw new EmptyDateException("'/to'");
            }
            LocalDateTime to = parseDate(fromAndTo[1].trim(), "'/to'");

            if (to.isBefore(from)) {
                lastCmd = "ERROR";
                throw new DateRangeException();
            }
            int indexOfConflict = tasklist.findConflict(from, to);
            if (indexOfConflict > 0) {
                lastCmd = "ERROR";
                throw new DateConflictException("event", indexOfConflict);
            }
            ArrayList<Object> parsed = new ArrayList<>();
            parsed.add(desc);
            parsed.add(from);
            parsed.add(to);
            lastCmd = "SUCCESS";
            return new AddCmd("E", parsed);
        }
        return null;
    }

    private static boolean isValidDesc(String taskType, String desc) throws EmptyDescException {
        if (desc.isEmpty()) {
            lastCmd = "ERROR";
            throw new EmptyDescException(taskType);
        }
        return true;
    }

    private static int isValidSelector(String num, String selector, int listSize) throws DuckyException {
        if (num.isEmpty()) {
            lastCmd = "ERROR";
            throw new EmptySelectorException(selector);
        }
        try {
            int taskId = Integer.parseInt(num);
            if (taskId < 1 || taskId > listSize) {
                lastCmd = "ERROR";
                throw new InvalidSelectorException();
            }
            return taskId;
        } catch (NumberFormatException e) {
            lastCmd = "ERROR";
            throw new InvalidSelectorException();
        }
    }

    /**
     * Returns a standardised ISO date to be used with the DateTime object.
     * @param date A string-form date
     * @return An ISO 8601-format date
     */
    public static LocalDateTime parseDate(String date, String fieldName) throws InvalidDateException {
        String[] dateAndTime = date.split(" ");
        if (dateAndTime.length == 2) {
            DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            try {
                return LocalDateTime.parse(date, customFormat);
            } catch (DateTimeParseException e) {
                lastCmd = "ERROR";
                throw new InvalidDateException(fieldName);
            }
        }
        lastCmd = "ERROR";
        throw new InvalidDateException(fieldName);
    }

    public static String getLastCmd() {
        return lastCmd;
    }
}
