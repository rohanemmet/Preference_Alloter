package com.cultofcthulhu.projectallocation.exceptions;

public class ParseException extends Exception {
    private String message;

    public ParseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "There was an error during parsing: " + message;
    }
}
