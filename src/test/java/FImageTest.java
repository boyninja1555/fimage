import com.boyninja1555.fimage.FImage;
import com.boyninja1555.fimage.lib.FImageFlags;
import com.boyninja1555.fimage.lib.RGBA;
import com.boyninja1555.fimage.lib.exceptions.FImageResizeException;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

class FImageTest {

    @Test
    void exampleImage() throws FImageResizeException, IOException {
        FImage image = new FImage(new FImageFlags(false), 4, List.of(
                RGBA.HALF_RED, RGBA.GREEN, RGBA.BLUE, RGBA.TRANSPARENT,
                RGBA.RED, RGBA.HALF_GREEN, RGBA.BLUE, RGBA.RED,
                RGBA.RED, RGBA.GREEN, RGBA.HALF_BLUE, RGBA.RED,
                RGBA.TRANSPARENT, RGBA.GREEN, RGBA.BLUE, RGBA.HALF_RED
        ));
        image.save(new File(".", "example.fimg"));

        image.setPixelAt(2, 3, RGBA.TRANSPARENT);
        image.save(new File(".", "example.fimg"));
        RGBA specialPixel = image.getPixelAt(2, 3);
        System.out.print("Special pixel is " + (specialPixel.isOpaque() ? "opaque" : (specialPixel.isTransparent() ? "transparent" : "translucent")));

        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        int x = 0;
        int y = 0;
        for (RGBA pixel : image.toPixelList()) {
            g2d.setColor(new Color(pixel.r, pixel.g, pixel.b, pixel.a));
            g2d.fillRect(x, y, 1, 1);
            x++;

            if (x >= image.width()) {
                x = 0;
                y++;
            }
        }

        g2d.dispose();
        File file = new File("example.png");
        ImageIO.write(bufferedImage, "PNG", file);
    }
}
