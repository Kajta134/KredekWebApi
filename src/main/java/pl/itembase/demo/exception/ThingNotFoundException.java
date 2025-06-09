package pl.itembase.demo.exception;

public class ThingNotFoundException extends RuntimeException {
    public ThingNotFoundException(String message) {
        super(message);
    }
}
