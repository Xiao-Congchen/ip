/**
 * DuckyExceptions serve as the parent exception to all custom
 * validation exceptions while running Ducky.
 */

package ducky.exception;

public class DuckyExceptions extends Exception {
    public DuckyExceptions(String msg) {
        super(msg);
    }
}
