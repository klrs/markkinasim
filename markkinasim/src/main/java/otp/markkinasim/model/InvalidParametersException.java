package otp.markkinasim.model;

public class InvalidParametersException extends Exception {
    public InvalidParametersException(String errorMessage) {
        super(errorMessage);
    }
}
