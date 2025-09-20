package ducky.exception;

public class InvalidDateException extends InvalidException {
    public InvalidDateException(String fieldName) {
        super("Oops! That " + fieldName + " date/time doesn't look right.\nRemember the format is \"dd/mm/yyyy hhmm\"!\nGive it another quack!");
    }
}
