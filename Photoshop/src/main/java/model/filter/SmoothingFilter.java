package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;

public class SmoothingFilter implements Filter {
    private final FilterModel filterModel;

    private final float[][] kernel3x3 = {
            {0.081f, 0.134f, 0.081f},
            {0.134f, 0.220f, 0.134f},
            {0.081f, 0.134f, 0.081f}
    };

    private final float[][] kernel5x5 = {
            {0.002f, 0.013f, 0.022f, 0.013f, 0.002f},
            {0.013f, 0.059f, 0.097f, 0.059f, 0.013f},
            {0.022f, 0.097f, 0.159f, 0.097f, 0.022f},
            {0.013f, 0.059f, 0.097f, 0.059f, 0.013f},
            {0.002f, 0.013f, 0.022f, 0.013f, 0.002f}
    };

    public SmoothingFilter(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    public BufferedImage applyFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int kernelSize = filterModel.getSmoothingKernelSize();
        int half = kernelSize / 2;

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float newR = 0;
                float newG = 0;
                float newB = 0;
                int a = (image.getRGB(x, y) >> 24) & 0xFF;

                for (int kernelX = -half; kernelX <= half; kernelX++) {
                    for (int kernelY = -half; kernelY <= half; kernelY++) {
                        int imgX = Math.min(Math.max(x + kernelX, 0), width - 1);
                        int imgY = Math.min(Math.max(y + kernelY, 0), height - 1);

                        int rgb = image.getRGB(imgX, imgY);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = rgb & 0xFF;

                        float weight = (kernelSize == 3) ? kernel3x3[kernelX + half][kernelY + half] : kernel5x5[kernelX + half][kernelY + half];

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
