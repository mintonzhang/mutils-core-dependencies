package cn.minsin.core.tools;

import cn.minsin.core.tools.function.Consumer3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author minton.zhang
 * @since 2020/6/11 16:43
 */
public class ImageUtil {

    public static void getWidthAndHeight(InputStream inputStream, Consumer3<Boolean, Integer, Integer> doubleBiConsumer) {
        try {
            BufferedImage bi = ImageIO.read(inputStream);
            if (bi != null) {
                int height = bi.getHeight();
                int width = bi.getWidth();
                doubleBiConsumer.accept(true, height, width);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        doubleBiConsumer.accept(true, null, null);
    }
}
