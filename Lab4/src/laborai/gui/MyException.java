package laborai.gui;

/**
 * Nuosava situacija, panaudota dialogo struktūrose įvedamų parametrų
 * tikrinimui.
 */
public class MyException extends RuntimeException {

    // Situacijos reikšmė
    private String value;
    private int code;

    public MyException(String message) {
        this(message, "");
    }

    public MyException(String message, String value) {
        super(message);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public MyException(String message, int code) {
        super(message);
        if (code < -1) {
            throw new IllegalArgumentException("Illegal code in MyException: " + code);
        }
        this.code = code;
    }

    public MyException(String message, Throwable throwable, int code) {
        super(message, throwable);
        if (code < -1) {
            throw new IllegalArgumentException("Illegal code in MyException: " + code);
        }
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
