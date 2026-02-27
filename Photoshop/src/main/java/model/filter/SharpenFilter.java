package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;

public class SharpenFilter implements Filter {
    private final FilterModel filterModel;

    private final float[][] sharpenKernel = {
            {0 , -1f, 0},
            {-1f, 5, -1f},
            {0, -1f, 0}
    };

    public SharpenFilter(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    public BufferedImage applyFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float newR = 0;
                float newG = 0;
                float newB = 0;
                int a = (image.getRGB(x, y) >> 24) & 0xFF;

                for (int kernelX = -1; kernelX <= 1; kernelX++) {
                    for (int kernelY = -1; kernelY <= 1; kernelY++) {
                        int imgX = Math.min(Math.max(x + kernelX, 0), width - 1);
                        int imgY = Math.min(Math.max(y + kernelY, 0), height - 1);

                        int rgb = image.getRGB(imgX, imgY);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = rgb & 0xFF;

                        float weight = sharpenKernel[kernelX + 1][kernelY + 1];

                        newR += r * weight;
                        newG += g * weight;
                        newB += b * weight;
                    }
                }

                int nr = Math.min(Math.max((int)newR, 0), 255);
                int ng = Math.min(Math.max((int)newG, 0), 255);
                int nb = Math.min(Math.max((int)newB, 0), 255);

                result.setRGB(x, y, (a << 24) | (nr << 16) | (ng << 8) | nb);
            }
        }

        return result;
    }
}
