package com.boyninja1555.fimage.lib.exceptions;

public class FImageResizeException extends Exception {

    public FImageResizeException(String... message) {
        super(String.join(" ", message));
    }
}
