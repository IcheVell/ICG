package model.filter;

import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class WaterColorFilter implements Filter {
    public BufferedImage applyFilter(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        int[] colors = new int[25];

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int idx = 0;

                for (int dy = -2; dy <= 2; dy++) {
                    for (int dx = -2; dx <= 2; dx++) {
                        int nx = Math.max(0, Math.min(x + dx, w - 1));
                        int ny = Math.max(0, Math.min(y + dy, h - 1));
                        colors[idx++] = image.getRGB(nx, ny);
                    }
                }

                Arrays.sort(colors);
                result.setRGB(x, y, colors[12]);
            }
        }

        return result;
    }
}
