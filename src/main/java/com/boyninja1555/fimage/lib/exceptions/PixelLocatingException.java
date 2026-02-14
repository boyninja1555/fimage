package com.boyninja1555.fimage.lib.exceptions;

/**
 * Thrown when a pixel could not be located, often out of the bounds of an image
 */
public class PixelLocatingException extends IndexOutOfBoundsException {

    public PixelLocatingException(String... message) {
        super(String.join(" ", message));
    }
}
