/**
 * DuckyExceptions serve as the parent exception to all custom
 * validation exceptions while running Ducky.
 */

package ducky.exception;

public class DuckyException extends Exception {
    public DuckyException(String msg) {
        super(msg);
    }
}
