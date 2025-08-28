package ducky.stringprocessing;

import ducky.command.Command;
import ducky.command.AddCmd;
import ducky.command.ByeCmd;
import ducky.command.ListCmd;
import ducky.command.MarkCmd;
import ducky.command.DeleteCmd;

import ducky.exception.DuckyExceptions;
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

public class Parser {

    public static Command parse(String input, int listSize) throws DuckyExceptions {
        if (input.isEmpty()) throw new EmptyCommandException();
        String[] keywordAndRest = input.split(" ", 2);
        String cmdType = keywordAndRest[0].toUpperCase();
        if (keywordAndRest.length == 1 && keywordAndRest[0].equalsIgnoreCase("bye")) {
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
                return new AddCmd("T", parsed);
            }

        case "DEADLINE": // Deadline
            if (isValidDesc("Deadline task", desc)) {
                String[] descAndDate = input.split("/by", 2);
                // Either no "/by" or "/by" is empty
                if (descAndDate.length == 1) {
                    throw new EmptyDateException("'/by'");
                }
                LocalDateTime date = parseDate(descAndDate[1].trim(),"'/by'");
                parsed.add(desc);
                parsed.add(date);
                return new AddCmd("D", parsed);
            }

        case "EVENT":
            if (isValidDesc("Event", desc)) {
                String[] descAndFromTo = input.split("/from", 2);
                // Either no "/from" or "/from" is empty
                if (descAndFromTo.length == 1) {
                    throw new EmptyDateException("'/from'");
                }
                LocalDateTime from = parseDate(descAndFromTo[1].split("/to")[0].trim(), "'/from'");

                String[] fromAndTo = input.split("/to",2);
                // Either no "/to" or "/to" is empty
                if (fromAndTo.length == 1 ) {
                    throw new EmptyDateException("'/to'");
                }
                LocalDateTime to = parseDate(fromAndTo[1].trim(), "'/to'");
                parsed.add(desc);
                parsed.add(from);
                parsed.add(to);
                return new AddCmd("E", parsed);
            }

        case "LIST":
            return new ListCmd();

        case "MARK":
            int markId = isValidateSelector(rest, "mark", listSize);
            return new MarkCmd(markId, true);

        case "UNMARK":
            int unmarkId = isValidateSelector(rest, "unmark", listSize);
            return new MarkCmd(unmarkId, false);
        case "DELETE":
            int delId = isValidateSelector(rest, "delete", listSize);
            return new DeleteCmd(delId);

        case "CLEARALL":
            // Negative taskId signals clear all (negative user input will lead to exception)
            return new DeleteCmd(-1);

        default:
            throw new InvalidCommandException();
        }
    }

    private static boolean isValidDesc(String taskType, String desc) throws EmptyDescException {
        if (desc.isEmpty()) {
            throw new EmptyDescException(taskType);
        }
        return true;
    }

    private static int isValidateSelector(String num, String selector, int listSize) throws DuckyExceptions {
        if (num.isEmpty()) throw new EmptySelectorException(selector);
        try {
            int taskId = Integer.parseInt(num);
            if (taskId < 1 || taskId > listSize) throw new InvalidSelectorException();
            return taskId;
        } catch (NumberFormatException e) {
            throw new InvalidSelectorException();
        }
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
