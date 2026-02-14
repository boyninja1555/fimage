package com.boyninja1555.fimage.lib;

import java.util.BitSet;

public record FImageFlags(boolean alphaMode) {
    public static final FImageFlags NULL = new FImageFlags(false);

    /**
     * Serializes the flags into a single byte
     * @return Flags as a byte
     */
    public byte serialize() {
        BitSet result = new BitSet(1);
        result.set(0, alphaMode);

        byte[] rawResult = result.toByteArray();
        return rawResult.length > 0 ? rawResult[0] : 0;
    }

    /**
     * Deserializes a byte into flags
     * @return New flags instance
     */
    public static FImageFlags deserialize(byte raw) {
        BitSet cooked = new BitSet(raw);
        return new FImageFlags(cooked.get(0));
    }
}
