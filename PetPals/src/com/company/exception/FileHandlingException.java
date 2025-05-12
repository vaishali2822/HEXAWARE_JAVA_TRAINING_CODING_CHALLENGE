package com.company.exception;


import java.io.IOException;

public class FileHandlingException extends IOException {
    public FileHandlingException(String message) {
        super(message);
    }
}
