package com.boyninja1555.fimage;

import com.boyninja1555.fimage.lib.FImageFlags;
import com.boyninja1555.fimage.lib.RGBA;
import com.boyninja1555.fimage.lib.exceptions.FImageResizeException;
import com.boyninja1555.fimage.lib.exceptions.PixelLocatingException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Easily manipulatable image state
 */
public class FImage {
    private FImageFlags flags;
    private int width;
    private List<RGBA> pixels;

    public FImage(@NotNull FImageFlags flags, int width, @NotNull List<RGBA> pixels) throws FImageResizeException {
        this.flags = flags;
        this.pixels = new ArrayList<>(pixels);
        width(width);
    }

    // Getters / setters

    public FImageFlags flags() {
        return flags;
    }

    public int width() {
        return width;
    }

    /**
     * Because fImages do not store their heights, this method calculates it manually. Not storing heights isn't a limitation, it's a storage optimization. This also means there isn't a <code>FImage::height(int)</code> setter method
     * @return Calculated image height
     */
    public int height() {
        return pixels.size() / width;
    }

    /**
     * Collects the image's pixels
     * @return Array of pixels
     */
    public RGBA[] toPixelArray() {
        return pixels.toArray(new RGBA[0]);
    }

    /**
     * Collects the image's pixels
     * @return Unmodifiable list of pixels
     */
    public List<RGBA> toPixelList() {
        return Collections.unmodifiableList(pixels);
    }

    public void flags(FImageFlags flags) {
        this.flags = flags;
    }

    public void width(int width) throws FImageResizeException {
        if (pixels.size() % width != 0)
            throw new FImageResizeException("Resizing this image would create a non-rectangular result");

        this.width = width;
    }

    public void pixels(List<RGBA> pixels) {
        this.pixels = new ArrayList<>(pixels);
    }

    // Generic image utilities

    public int pixelLocationToIndex(int x, int y) {
        return y * width + x;
    }

    public RGBA getPixelAt(int x, int y) throws PixelLocatingException {
        int height = pixels.size() / width;
        if (x < 0 || y < 0 || x == width || y == height)
            throw new PixelLocatingException("Attempted (" + x + "," + y + ") on a " + width + "x" + height + " image! Coordinates start at 0, if that helps");

        if (x > width || y > height)
            throw new PixelLocatingException("Attempted (" + x + "," + y + ") on a " + width + "x" + height + " image");

        return pixels.get(pixelLocationToIndex(x, y));
    }

    public void setPixelAt(int x, int y, RGBA color) throws PixelLocatingException {
        // This is kinda useless, but I didn't wanna somehow move the exception throwers or whatever
        RGBA original = getPixelAt(x, y);

        if (original.equals(color))
            return;

        pixels.set(pixelLocationToIndex(x, y), color);
    }

    // Saving / loading

    /**
     * Saves the current image state to a specified file
     * <p><strong>Warning:</strong> This method is required for updating the image file; No setters will automatically save the current state!</p>
     * @param file Target image file (can be non-existent)
     * @throws IOException Contains reason why the image file could not be written to or created
     */
    public void save(File file) throws IOException {
        if (!file.exists()) {
            boolean created = file.createNewFile();
            if (!created) throw new IOException("Unknown error occurred upon image file creation");
        }

        DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
        FImageFormat.writeMagic(out);
        FImageFormat.writeFlags(out, flags);
        FImageFormat.writePixelCount(out, pixels.size());
        FImageFormat.writeWidth(out, width);

        for (int i = 0; i < pixels.size(); i++) {
            RGBA pixel = pixels.get(i).asOpaque();
            pixels.set(i, pixel);
            FImageFormat.writePixel(out, flags, pixel);
        }

        out.close();
    }

    /**
     * Loads a specified file to the current image state
     * @param file Target image file (must exist)
     * @throws IOException Contains reason why the image file could not be read
     * @throws FImageResizeException Contains reason why the image could not be resized during load
     */
    public void load(File file) throws IOException, FImageResizeException {
        DataInputStream in = new DataInputStream(new FileInputStream(file));

        if (!FImageFormat.hasMagic(in))
            throw new IOException("File is not a valid fImage as it does not start with the magic");

        FImageFlags flags = FImageFormat.readFlags(in);
        List<RGBA> pixels = new ArrayList<>();
        int pixelCount = FImageFormat.readPixelCount(in);
        int width = FImageFormat.readWidth(in);
        for (int i = 0; i < pixelCount; i++)
            pixels.add(FImageFormat.readPixel(in, flags));

        flags(flags);
        width(width);
        pixels(pixels);
    }

    /**
     * Creates a new image state from the specified file; Uses the non-static <code>FImage::load(File)</code> method internally
     * @param file Target image file (must exist)
     * @return New image state
     * @throws IOException Contains reason why the image file could not be read
     * @throws FImageResizeException Contains reason why the image could not be resized during load
     */
    public static FImage fromFile(File file) throws IOException, FImageResizeException {
        FImage image = new FImage(FImageFlags.NULL, 0, Collections.emptyList());
        image.load(file);
        return image;
    }
}
