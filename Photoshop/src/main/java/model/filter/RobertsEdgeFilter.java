package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;

public class RobertsEdgeFilter implements Filter {
    private final FilterModel filterModel;

    public RobertsEdgeFilter(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    public BufferedImage applyFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int threshold = filterModel.getThreshold();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width - 1; x++) {
            for (int y = 0; y < height - 1; y++) {
                int oldRGB =  image.getRGB(x, y);
                int a = oldRGB >> 24 & 0xFF;

                int first = getGray(image, x, y);
                int second = getGray(image, x + 1, y);
                int third = getGray(image, x, y + 1);
                int fourth = getGray(image, x + 1, y + 1);

                int gx = first - fourth;
                int gy = second - third;

                int gradient = Math.abs(gx) + Math.abs(gy);

                int value = gradient > threshold ? 255 : 0;

                result.setRGB(x, y, (a << 24) | (value << 16) | (value << 8) | value);
            }
        }
    
        return result;
    }

    private int getGray(BufferedImage image, int x, int y) {
        int rgb =  image.getRGB(x, y);

        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;

        return (r + g + b) / 3;
    }
}
