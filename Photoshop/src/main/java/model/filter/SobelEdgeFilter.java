package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;

public class SobelEdgeFilter implements Filter {
    private final FilterModel filterModel;

    private final int[][] sobelX = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };

    private final int[][] sobelY = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
    };

    public SobelEdgeFilter(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    public BufferedImage applyFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int threshold = filterModel.getThreshold();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                int gX = 0;
                int gY = 0;

                for (int kx = -1;  kx <= 1; kx++) {
                    for (int ky = -1; ky <= 1; ky++) {
                        int gray = getGray(image, x + kx, y + ky);

                        gX += gray * sobelX[kx + 1][ky + 1];
                        gY += gray * sobelY[kx + 1][ky + 1];
                    }
                }

                int gradient = Math.abs(gX) + Math.abs(gY);

                int value = gradient > threshold ? 255 : 0;

                result.setRGB(x, y, (255 << 24) | (value << 16) | (value << 8) | value);
            }
        }

        return result;
    }

    private int getGray(BufferedImage image, int x, int y) {
        int rgb = image.getRGB(x, y);

        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;

        return (r + g + b) / 3;
    }
}
