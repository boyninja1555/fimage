package com.boyninja1555.fimage.lib;

public class RGBA {
    public static final RGBA TRANSPARENT = new RGBA(0f, 0f, 0f, 0f);
    public static final RGBA RED = new RGBA(1f, 0f, 0f);
    public static final RGBA GREEN = new RGBA(0f, 1f, 0f);
    public static final RGBA BLUE = new RGBA(0f, 0f, 1f);
    public static final RGBA HALF_RED = new RGBA(1f, 0f, 0f, .5f);
    public static final RGBA HALF_GREEN = new RGBA(0f, 1f, 0f, .5f);
    public static final RGBA HALF_BLUE = new RGBA(0f, 0f, 1f, .5f);

    public float r;
    public float g;
    public float b;
    public float a;

    public RGBA(float r, float g, float b, float a) {
        r(r);
        g(g);
        b(b);
        a(a);
    }

    public RGBA(float r, float g, float b) {
        r(r);
        g(g);
        b(b);
        a(1f);
    }

    public void r(float r) {
        this.r = Math.clamp(r, 0f, 1f);
    }

    public void g(float g) {
        this.g = Math.clamp(g, 0f, 1f);
    }

    public void b(float b) {
        this.b = Math.clamp(b, 0f, 1f);
    }

    public void a(float a) {
        this.a = Math.clamp(a, 0f, 1f);
    }

    /**
     * @return Whether this color is transparent (does not include translucency)
     */
    public boolean isTransparent() {
        return a == 0f;
    }

    /**
     * @return Whether this color is translucent (does not include transparency)
     */
    public boolean isTranslucent() {
        return a < 1f && !isTransparent();
    }

    /**
     * @return Whether this color is opaque; Uses the <code>RGBA::isTransparent()</code> and <code>RGBA::isTranslucent()</code> methods internally
     */
    public boolean isOpaque() {
        return !isTransparent() && !isTranslucent();
    }

    /**
     * @return This color but opaque
     */
    public RGBA asOpaque() {
        return new RGBA(r, g, b, 1f);
    }

    public boolean equals(RGBA other) {
        return r == other.r && g == other.g && b == other.b && a == other.a;
    }
}
