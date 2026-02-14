package com.boyninja1555.fimage;

import com.boyninja1555.fimage.lib.FImageFlags;
import com.boyninja1555.fimage.lib.RGBA;
import org.jetbrains.annotations.ApiStatus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * fImage-specific format utilities
 */
@ApiStatus.Internal
public class FImageFormat {
    public static final byte[] MAGIC = "fimg".getBytes(StandardCharsets.US_ASCII);

    public static boolean hasMagic(DataInputStream in) throws IOException {
        byte[] magic = new byte[MAGIC.length];
        in.readFully(magic);
        return Arrays.equals(magic, MAGIC);
    }

    public static void writeMagic(DataOutputStream out) throws IOException {
        out.write(MAGIC);
    }

    public static FImageFlags readFlags(DataInputStream in) throws IOException {
        return FImageFlags.deserialize(in.readByte());
    }

    public static void writeFlags(DataOutputStream out, FImageFlags flags) throws IOException {
        out.write(flags.serialize());
    }

    public static int readPixelCount(DataInputStream in) throws IOException {
        return in.readInt();
    }

    public static void writePixelCount(DataOutputStream out, int pixelCount) throws IOException {
        out.writeInt(pixelCount);
    }

    public static int readWidth(DataInputStream in) throws IOException {
        return in.readInt();
    }

    public static void writeWidth(DataOutputStream out, int width) throws IOException {
        out.writeInt(width);
    }

    public static RGBA readPixel(DataInputStream in, FImageFlags flags) throws IOException {
        float r = in.readInt() / 255f;
        float g = in.readInt() / 255f;
        float b = in.readInt() / 255f;
        float a = flags.alphaMode() ? in.readInt() / 255f : 1f;
        return new RGBA(r, g, b, a);
    }

    public static void writePixel(DataOutputStream out, FImageFlags flags, RGBA color) throws IOException {
        out.writeInt(Math.round(color.r * 255));
        out.writeInt(Math.round(color.g * 255));
        out.writeInt(Math.round(color.b * 255));

        if (flags.alphaMode())
            out.writeInt(Math.round(color.a * 255));
    }
}
