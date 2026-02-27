package models;

import enums.Form;
import enums.Mode;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class CanvasModel {
    private BufferedImage image;
    private Mode mode = Mode.LINE;
    private Form form = Form.POLYGON;
    private Color color = Color.BLACK;
    private int thickness = 1;
    private int radius = 10;
    private int rotation = 0;
    private int countVertices = 3;

    private Point startPoint;
    private Point endPoint;

    public CanvasModel(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void clearCanvasModel() {
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.dispose();
    }

    public void line() {
        if (endPoint == null ||  startPoint == null) {
            return;
        }

        if (thickness == 1) {
            drawLineBresenham();
        }

        if (thickness > 1) {
            drawLine();
        }

        startPoint = endPoint = null;
    }

    public void fill() {
        drawFill(new Point(startPoint.x, startPoint.y));
    }

    public void stamp() {
        if (form == Form.POLYGON) {
            drawRegularPolygon(startPoint.x, startPoint.y, radius, countVertices, rotation);
        }

        if (form == Form.STAR) {
            drawStar(startPoint.x, startPoint.y, radius, countVertices, rotation);
        }
    }

    private void drawLineBresenham() {
        int x1 = startPoint.x;
        int y1 = startPoint.y;
        int x2 = endPoint.x;
        int y2 = endPoint.y;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        int x = x1, y = y1;
        int newColor = color.getRGB();

        image.setRGB(x, y, newColor);

        while (x != x2 || y != y2) {
            int e2 = 2 * err;

            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }

            if (e2 < dx) {
                err += dx;
                y += sy;
            }

            image.setRGB(x, y, newColor);
        }
    }

    private void drawLine() {
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));

        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }

    private void drawFill(Point startSeed) {
        Stack<Point> stack = new Stack<>();
        stack.push(startSeed);

        int newColor = color.getRGB();
        int seedRGB = image.getRGB(startSeed.x, startSeed.y);

        int imageWidth =  image.getWidth();
        int imageHeight =  image.getHeight();

        if (seedRGB == newColor) {
            return;
        }

        while (!stack.isEmpty()) {
            Point seed = stack.pop();

            if (image.getRGB(seed.x, seed.y) != seedRGB) {
                continue;
            }

            image.setRGB(seed.x, seed.y, newColor);

            int right = seed.x;
            while (right + 1 < imageWidth && (image.getRGB(right + 1, seed.y) == seedRGB)) {
                right++;
                image.setRGB(right, seed.y, newColor);
            }

            int left = seed.x;
            while (left - 1 >= 0 && (image.getRGB(left - 1, seed.y) == seedRGB)) {
                left--;
                image.setRGB(left, seed.y, newColor);
            }

            boolean spanAbove = false;
            boolean spanBelow = false;

            for (int i = left; i <= right; i++) {
                if (seed.y + 1 < imageHeight && image.getRGB(i, seed.y + 1) == seedRGB) {
                    if (!spanBelow) {
                        stack.push(new Point(i, seed.y + 1));
                        spanBelow = true;
                    }
                } else {
                    spanBelow = false;
                }

                if (seed.y - 1 > 0 && image.getRGB(i,seed.y - 1) == seedRGB) {
                    if (!spanAbove) {
                        stack.push(new Point(i, seed.y - 1));
                        spanAbove = true;
                    }
                } else {
                    spanAbove = false;
                }
            }
        }
    }

    private void drawRegularPolygon(int centerX, int centerY, int radius, int numVertices, int rotation) {
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(Color.BLACK);

        GeneralPath path = new GeneralPath();

        double radiansRotation = Math.toRadians(rotation);

        for (int i = 0; i < numVertices; i++) {
            double angle = i * 2 * Math.PI / numVertices + radiansRotation;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        path.closePath();

        g2d.draw(path);
        g2d.dispose();
    }

    private void drawStar(int centerX, int centerY, int radius, int numVertices, double rotation) {
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(Color.BLACK);

        double rotationRadians = Math.toRadians(rotation);

        GeneralPath star = new GeneralPath();
        double angleStep = Math.PI / numVertices;
        double innerRadius = radius * 0.38;

        double angle = rotationRadians - Math.PI / 2;
        star.moveTo(centerX + radius * Math.cos(angle),centerY + radius * Math.sin(angle));

        for (int i = 0; i < numVertices * 2; i++) {
            angle += angleStep;
            double currentRadius = (i % 2 == 0) ? radius : innerRadius;

            if (i == 0) {
                star.moveTo(centerX + currentRadius * Math.cos(angle), centerY + currentRadius * Math.sin(angle));
            }

            if (i != 0) {
                star.lineTo(centerX + currentRadius * Math.cos(angle), centerY + currentRadius * Math.sin(angle));
            }
        }

        star.closePath();

        g2d.draw(star);
        g2d.dispose();
    }

    public Color getColor() {
        return color;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Mode getMode() {
        return mode;
    }

    public Form getForm() {
        return form;
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getRadius() {
        return radius;
    }

    public int getCountVertices() {
        return countVertices;
    }

    public int getRotation() {
        return rotation;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void setCountVertices(int countVertices) {
        this.countVertices = countVertices;
    }

    public void resize(int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = (Graphics2D) newImage.getGraphics();

        g2d.fillRect(0, 0, width, height);

        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        image = newImage;
    }
}
