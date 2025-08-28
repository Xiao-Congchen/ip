public class InvalidDateException extends InvalidException {
    public InvalidDateException(String fieldName) {
        super("That's an invalid " + fieldName + " date/time!\n\t"
                + "Please follow the format \"dd/mm/yyyy hhmm\"!\n\t"
                + "Quack twice and try again~");
    }
}
