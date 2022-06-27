package br.com.kennedymota.passwordvalidator.utils;

public enum Errors {

    GENERIC_ERROR(500, "Error during execution, check the logs."),
    BAD_PASSWORD(400, "Password failed validation.");

    private final int error_code;
    private final String message;

    Errors(int error_code, String message) {
        this.error_code = error_code;
        this.message = message;
    }

    public int getError_code() {
        return error_code;
    }

    public String getMessage() {
        return message;
    }
}
