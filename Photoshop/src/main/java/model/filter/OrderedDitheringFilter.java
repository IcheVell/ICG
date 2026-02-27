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

        int qR = clamp(filterModel.getOrderedQuantsR(), 2, 128);
        int qG = clamp(filterModel.getOrderedQuantsG(), 2, 128);
        int qB = clamp(filterModel.getOrderedQuantsB(), 2, 128);

        int nR = getKernelSize(qR);
        int nG = getKernelSize(qG);
        int nB = getKernelSize(qB);

        int[][] bR = bayer(nR);
        int[][] bG = bayer(nG);
        int[][] bB = bayer(nB);

        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                int a = (rgb >>> 24) & 0xFF;
                int r = (rgb >>> 16) & 0xFF;
                int g = (rgb >>> 8) & 0xFF;
                int b = rgb & 0xFF;

                int rr = orderedQuant(r, qR, bR[y % nR][x % nR], nR);
                int gg = orderedQuant(g, qG, bG[y % nG][x % nG], nG);
                int bb = orderedQuant(b, qB, bB[y % nB][x % nB], nB);

                out.setRGB(x, y, (a << 24) | (rr << 16) | (gg << 8) | bb);
            }
        }
        return out;
    }

    private int orderedQuant(int v, int q, int bayerVal, int n) {
        float t = ((bayerVal + 0.5f) / (n * n)) - 0.5f;

        int levels = q - 1;

        float step = 255f / levels;

        float vd = v + t * step;

        if (vd < 0f) vd = 0f;
        if (vd > 255f) vd = 255f;

        int idx = Math.round(vd * levels / 255f);
        if (idx < 0) idx = 0;
        if (idx > levels) idx = levels;

        return Math.round(idx * 255f / levels);
    }

    private static int getKernelSize(int quants) {
        if (quants <= 4) return 2;
        if (quants <= 16) return 4;
        if (quants <= 64) return 8;
        return 16;
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

        for (int y = 0; y < n / 2; y++) {
            for (int x = 0; x < n / 2; x++) {
                int v = s[y][x];
                m[y][x] = 4 * v + 0;
                m[y][x + n / 2] = 4 * v + 2;
                m[y + n / 2][x] = 4 * v + 3;
                m[y + n / 2][x + n / 2] = 4 * v + 1;
            }
        }
        return m;
    }

    private static int clamp(int v, int lo, int hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}