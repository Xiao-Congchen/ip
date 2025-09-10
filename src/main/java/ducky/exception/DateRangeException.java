package ducky.exception;

public class DateRangeException extends DuckyException {
    public DateRangeException() {
        super("Quack?!!\nThe '/from' date should come before the 'to' date!");
    }
}
