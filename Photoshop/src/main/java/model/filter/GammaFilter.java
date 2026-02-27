package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;

public class GammaFilter implements Filter {
    private FilterModel filterModel;

    public GammaFilter(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    public BufferedImage applyFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        float invGamma = 1.0f / filterModel.getGamma();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int oldRGB = image.getRGB(x, y);

                int a = oldRGB >> 24 & 0xFF;
                int r = oldRGB >> 16 & 0xFF;
                int g = oldRGB >> 8 & 0xFF;
                int b = oldRGB & 0xFF;

                r = gammaInvert(r, invGamma);
                g = gammaInvert(g, invGamma);
                b = gammaInvert(b, invGamma);

                result.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
            }
        }

        return result;
    }

    private int gammaInvert(int value, float invGamma) {
        double normalized = value / 255.0;
        int corrected =(int) (255 * Math.pow(normalized, invGamma));

        return Math.min(Math.max(corrected, 0), 255);
    }
}
