package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class OilPaintingFilter implements Filter {
    private final FilterModel filterModel;
    private final int kernelSize = 8;

    public OilPaintingFilter(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    public BufferedImage applyFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int binCount = filterModel.getOilBinsCount();

        int[] binList = new int[binCount];

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int[] sumR = new int[binCount];
        int[] sumG = new int[binCount];
        int[] sumB = new int[binCount];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                for (int kx = -(kernelSize / 2); kx <= kernelSize / 2; kx++) {
                    for (int ky = -(kernelSize / 2); ky <= kernelSize / 2; ky++) {
                        int currX = Math.max(0, Math.min(width - 1, x + kx));
                        int currY = Math.max(0, Math.min(height - 1, y + ky));

                        int rgb =  image.getRGB(currX, currY);
                        int r = rgb >> 16 & 0xFF;
                        int g = rgb >> 8 & 0xFF;
                        int b = rgb & 0xFF;

                        int intensity = (r + g + b) / 3;
                        int idx = intensity * binCount / 256;

                        binList[idx]++;
                        sumR[idx] += r;
                        sumG[idx] += g;
                        sumB[idx] += b;
                    }
                }

                int maxBinIndex = getMaxBinIndex(binList);

                int a = image.getRGB(x, y) >> 24 & 0xFF;
                int r = sumR[maxBinIndex] / binList[maxBinIndex];
                int g = sumG[maxBinIndex] / binList[maxBinIndex];
                int b = sumB[maxBinIndex] / binList[maxBinIndex];

                int rgb = (a << 24) | (r << 16) | (g << 8)  | b;

                result.setRGB(x, y, rgb);

                Arrays.fill(binList, 0);
                Arrays.fill(sumR, 0);
                Arrays.fill(sumG, 0);
                Arrays.fill(sumB, 0);
            }
        }

        return result;
    }

    private int getMaxBinIndex(int[] binList) {
        int max = binList[0];
        int maxIdx = 0;

        for (int i = 1; i < binList.length; i++) {
            if (binList[i] > max) {
                max = binList[i];
                maxIdx = i;
            }
        }

        return maxIdx;
    }
}


