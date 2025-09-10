package ducky.exception;

public class DateRangeException extends DuckyException {
    public DateRangeException() {
        super("Quack?!! The '/from' date should come \nBEFORE the 'to' date!");
    }
}
