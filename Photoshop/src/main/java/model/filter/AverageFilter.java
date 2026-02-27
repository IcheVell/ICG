package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;

public class AverageFilter implements Filter {
    private final FilterModel filterModel;

    public AverageFilter(FilterModel filterModel) {
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
                int sumR = 0;
                int sumG = 0;
                int sumB = 0;
                int a = (image.getRGB(x, y) >> 24) & 0xFF;


                for (int kernelX = -half; kernelX <= half; kernelX++) {
                    for (int kernelY = -half; kernelY <= half; kernelY++) {
                        int imgX = Math.min(Math.max(x + kernelX, 0), width - 1);
                        int imgY = Math.min(Math.max(y + kernelY, 0), height - 1);

                        int rgb = image.getRGB(imgX, imgY);
                        sumR += (rgb >> 16) & 0xFF;
                        sumG += (rgb >> 8) & 0xFF;
                        sumB += rgb & 0xFF;
                    }
                }

                int nr = Math.min(Math.max(sumR / (kernelSize * kernelSize), 0), 255);
                int ng = Math.min(Math.max(sumG / (kernelSize * kernelSize), 0), 255);
                int nb = Math.min(Math.max(sumB / (kernelSize * kernelSize), 0), 255);

                result.setRGB(x, y, (a << 24) | (nr << 16) | (ng << 8) | nb);
            }
        }

        return result;
    }
}
