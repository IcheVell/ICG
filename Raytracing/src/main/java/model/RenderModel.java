package model;

public class RenderModel {
    private int backgroundR = 255;
    private int backgroundG = 255;
    private int backgroundB = 255;

    private double gamma = 1;

    private int depth = 1;

    public int getBackgroundR() {
        return backgroundR;
    }

    public int getBackgroundG() {
        return backgroundG;
    }

    public int getBackgroundB() {
        return backgroundB;
    }

    public double getGamma() {
        return gamma;
    }

    public int getDepth() {
        return depth;
    }


    public void setBackgroundR(int backgroundR) {
        this.backgroundR = backgroundR;
    }

    public void setBackgroundG(int backgroundG) {
        this.backgroundG = backgroundG;
    }

    public void setBackgroundB(int backgroundB) {
        this.backgroundB = backgroundB;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void clear() {
        backgroundR = 255;
        backgroundG = 255;
        backgroundB = 255;

        gamma = 1;
        depth = 1;
    }
}
