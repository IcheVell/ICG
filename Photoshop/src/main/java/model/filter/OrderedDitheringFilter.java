package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;

public class OrderedDitheringFilter implements Filter {
    private final FilterModel filterModel;

    public OrderedDitheringFilter(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    @Override
    public BufferedImage applyFilter(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        int qR = filterModel.getOrderedQuantsR();
        int qG = filterModel.getOrderedQuantsG();
        int qB = filterModel.getOrderedQuantsB();

        int kernelSize = getKernelSize(qR);

        int[][] bR = bayer(kernelSize);
        int[][] bG = bayer(kernelSize);
        int[][] bB = bayer(kernelSize);


        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                int a = (rgb >> 24) & 0xFF;
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int rr = orderedQuant(r, qR, bR[y % kernelSize][x % kernelSize], kernelSize);
                int gg = orderedQuant(g, qG, bG[y % kernelSize][x % kernelSize], kernelSize);
                int bb = orderedQuant(b, qB, bB[y % kernelSize][x % kernelSize], kernelSize);

                out.setRGB(x, y, (a << 24) | (rr << 16) | (gg << 8) | bb);
            }
        }

        return out;
    }

    private int orderedQuant(int v, int q, int bayerVal, int n) {
        float threshold = (((float)bayerVal) / (n * n)) - 0.5f;
        int levels = q - 1;
        float step = 255f / levels;

        float shiftedValue = v + threshold * step;
        shiftedValue = Math.max(0f, Math.min(shiftedValue, 255f));

        int idx = Math.round(shiftedValue * levels / 255f);
        idx = Math.max(0, Math.min(idx, levels));

        return Math.round(idx * 255f / levels);
    }

    private static int[][] bayer(int n) {
        if (n == 2) {
            return new int[][]{
                    {0, 2},
                    {3, 1}
            };
        }

        int[][] s = bayer(n / 2);
        int[][] m = new int[n][n];

        for (int x = 0; x < n / 2; x++) {
            for (int y = 0; y < n / 2; y++) {
                int v = s[x][y];
                m[x][y] = 4 * v;
                m[x][y + n / 2] = 4 * v + 2;
                m[x + n / 2][y] = 4 * v + 3;
                m[x + n / 2][y + n / 2] = 4 * v + 1;
            }
        }

        return m;
    }

    private int getKernelSize(int q) {
        int n = 4;
        int threshold = 256 / q;

        while (n < threshold) {
            n *= 2;
        }

        return n;
    }
}