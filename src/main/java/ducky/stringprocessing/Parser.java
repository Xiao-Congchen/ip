package ducky.stringprocessing;

import ducky.command.Command;
import ducky.command.AddCmd;
import ducky.command.ByeCmd;
import ducky.command.FindCmd;
import ducky.command.ListCmd;
import ducky.command.MarkCmd;
import ducky.command.DeleteCmd;

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
    public static Command parse(String input, int listSize) throws DuckyException {
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

        ArrayList<Object> parsed = new ArrayList<Object>();

        String rest;
        if (keywordAndRest.length == 2) {
            rest = keywordAndRest[1];
        } else {
            rest = "";
        }

        String desc = rest.split("/")[0].trim();
        switch (cmdType) {
        case "TODO":
            if (isValidDesc("Todo task", desc)) {
                parsed.add(desc);
                lastCmd = "SUCCESS";
                return new AddCmd("T", parsed);
            }

        case "DEADLINE": // Deadline
            if (isValidDesc("Deadline task", desc)) {
                String[] descAndDate = input.split("/by", 2);
                // Either no "/by" or "/by" is empty
                if (descAndDate.length == 1) {
                    lastCmd = "ERROR";
                    throw new EmptyDateException("'/by'");
                }
                LocalDateTime date = parseDate(descAndDate[1].trim(),"'/by'");
                parsed.add(desc);
                parsed.add(date);
                lastCmd = "SUCCESS";
                return new AddCmd("D", parsed);
            }

        case "EVENT":
            if (isValidDesc("Event", desc)) {
                String[] descAndFromTo = input.split("/from", 2);
                // Either no "/from" or "/from" is empty
                if (descAndFromTo.length == 1) {
                    lastCmd = "ERROR";
                    throw new EmptyDateException("'/from'");
                }
                LocalDateTime from = parseDate(descAndFromTo[1].split("/to")[0].trim(), "'/from'");

                String[] fromAndTo = input.split("/to",2);
                // Either no "/to" or "/to" is empty
                if (fromAndTo.length == 1 ) {
                    lastCmd = "ERROR";
                    throw new EmptyDateException("'/to'");
                }
                LocalDateTime to = parseDate(fromAndTo[1].trim(), "'/to'");
                parsed.add(desc);
                parsed.add(from);
                parsed.add(to);
                lastCmd = "SUCCESS";
                return new AddCmd("E", parsed);
            }

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
            // Negative taskId signals clear all (negative user input will lead to exception)
            lastCmd = "DEL";
            return new DeleteCmd(-1);

        default:
            lastCmd = "ERROR";
            throw new InvalidCommandException();
        }
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
