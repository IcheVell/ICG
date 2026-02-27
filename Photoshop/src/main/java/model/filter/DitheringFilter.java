package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;

public class DitheringFilter implements Filter {
    private final FilterModel filterModel;

    public DitheringFilter(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    @Override
    public BufferedImage applyFilter(BufferedImage image) {
        final int w = image.getWidth();
        final int h = image.getHeight();

        final int qR = clamp(filterModel.getQuantsR(), 2, 128);
        final int qG = clamp(filterModel.getQuantsG(), 2, 128);
        final int qB = clamp(filterModel.getQuantsB(), 2, 128);

        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
        float[] errR = new float[w];
        float[] errG = new float[w];
        float[] errB = new float[w];

        float[] nextErrR = new float[w];
        float[] nextErrG = new float[w];
        float[] nextErrB = new float[w];

        for (int y = 0; y < h; y++) {
            boolean ltr = (y % 2 == 0);

            float carryR = 0f, carryG = 0f, carryB = 0f;

            for (int i = 0; i < w; i++) {
                nextErrR[i] = 0f;
                nextErrG[i] = 0f;
                nextErrB[i] = 0f;
            }

            int xStart = ltr ? 0 : (w - 1);
            int xEnd   = ltr ? w : -1;
            int xStep  = ltr ? 1 : -1;

            for (int x = xStart; x != xEnd; x += xStep) {
                int rgb = image.getRGB(x, y);
                int a = (rgb >>> 24) & 0xFF;

                float r = ((rgb >>> 16) & 0xFF) + errR[x] + carryR;
                float g = ((rgb >>> 8)  & 0xFF) + errG[x] + carryG;
                float b = ( rgb         & 0xFF) + errB[x] + carryB;

                int newR = quantizeToLevels(r, qR);
                int newG = quantizeToLevels(g, qG);
                int newB = quantizeToLevels(b, qB);

                result.setRGB(x, y, (a << 24) | (newR << 16) | (newG << 8) | newB);

                float eR = r - newR;
                float eG = g - newG;
                float eB = b - newB;

                if (ltr) {
                    carryR = eR * (7f / 16f);
                    carryG = eG * (7f / 16f);
                    carryB = eB * (7f / 16f);

                    if (x - 1 >= 0) {
                        nextErrR[x - 1] += eR * (3f / 16f);
                        nextErrG[x - 1] += eG * (3f / 16f);
                        nextErrB[x - 1] += eB * (3f / 16f);
                    }

                    nextErrR[x] += eR * (5f / 16f);
                    nextErrG[x] += eG * (5f / 16f);
                    nextErrB[x] += eB * (5f / 16f);

                    if (x + 1 < w) {
                        nextErrR[x + 1] += eR * (1f / 16f);
                        nextErrG[x + 1] += eG * (1f / 16f);
                        nextErrB[x + 1] += eB * (1f / 16f);
                    }
                } else {
                    carryR = eR * (7f / 16f);
                    carryG = eG * (7f / 16f);
                    carryB = eB * (7f / 16f);

                    if (x + 1 < w) {
                        nextErrR[x + 1] += eR * (3f / 16f);
                        nextErrG[x + 1] += eG * (3f / 16f);
                        nextErrB[x + 1] += eB * (3f / 16f);
                    }

                    nextErrR[x] += eR * (5f / 16f);
                    nextErrG[x] += eG * (5f / 16f);
                    nextErrB[x] += eB * (5f / 16f);

                    if (x - 1 >= 0) {
                        nextErrR[x - 1] += eR * (1f / 16f);
                        nextErrG[x - 1] += eG * (1f / 16f);
                        nextErrB[x - 1] += eB * (1f / 16f);
                    }
                }
            }

            float[] tmp;

            tmp = errR; errR = nextErrR; nextErrR = tmp;
            tmp = errG; errG = nextErrG; nextErrG = tmp;
            tmp = errB; errB = nextErrB; nextErrB = tmp;
        }

        return result;
    }

    private static int quantizeToLevels(float v, int q) {
        if (v < 0f) v = 0f;
        if (v > 255f) v = 255f;

        int levels = q - 1;
        int idx = Math.round(v * levels / 255f);
        if (idx < 0) idx = 0;
        if (idx > levels) idx = levels;

        return Math.round(idx * 255f / levels);
    }

    private static int clamp(int v, int lo, int hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}