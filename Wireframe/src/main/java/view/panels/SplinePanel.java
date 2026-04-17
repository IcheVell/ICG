package view.panels;

import models.CurveModel;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class SplinePanel extends JPanel {
    private final CurveModel curveModel;

    public SplinePanel(CurveModel curveModel) {
        this.curveModel = curveModel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        int centerX = width / 2;
        int centerY = height / 2;

        drawAxis(g2d, centerX, centerY, width, height);
        drawControlPointCircles(g2d);

        drawSplinePoints(g2d);
    }
    
    private void drawAxis(Graphics2D g2d, int centerX, int centerY, int width, int height) {
        g2d.drawLine(centerX, 0, centerX, getHeight());
        g2d.drawLine(0, centerY, getWidth(), centerY);

        double scale = curveModel.getScale();

        int stepX = (int) (width / 21f * scale);
        int stepY = (int) (height / 21f * scale);

        for (int i = 0; i <= 10; i++) {
            g2d.drawLine(centerX + stepX * i, centerY + 5, centerX + stepX * i, centerY - 5);
            g2d.drawLine(centerX + 5, centerY + stepY * i, centerX - 5, centerY + stepY * i);

            g2d.drawLine(centerX + stepX * -i,  centerY + 5, centerX + stepX * -i, centerY - 5);
            g2d.drawLine(centerX + 5, centerY + stepY * -i, centerX - 5, centerY + stepY * -i);
        }
    }

    private void drawControlPointCircles(Graphics2D g2d) {
        int offsetX = getWidth() / 2;
        int offsetY = getHeight() / 2;

        int radius = curveModel.getCircleRadius();
        double scale = curveModel.getScale();

        g2d.setColor(Color.BLUE);

        Point2D prevPoint = null;

        for (Point2D point : curveModel.getControlPoints()) {
            int scaledX = (int) (point.getX() * scale + offsetX);
            int scaledY = (int) (point.getY() * scale  + offsetY);

            if (prevPoint != null) {
                int prevScaledX = (int) (prevPoint.getX() * scale + offsetX);
                int prevScaledY = (int) (prevPoint.getY() * scale + offsetY);
                g2d.drawLine(prevScaledX, prevScaledY, scaledX, scaledY);
            }

            g2d.drawOval((int)(point.getX() * scale - radius + offsetX), (int)(point.getY() * scale - radius + offsetY), 2 * radius, 2 * radius);
            prevPoint = point;
        }
    }

    private void drawSplinePoints(Graphics2D g2d) {
        int offsetX = getWidth() / 2;
        int offsetY = getHeight() / 2;

        double scale = curveModel.getScale();

        g2d.setColor(Color.RED);

        Point2D prevPoint = null;

        for (Point2D point : curveModel.getSplinePoints()) {
            int scaledX = (int) (point.getX() * scale + offsetX);
            int scaledY = (int) (point.getY() * scale  + offsetY);

            if (prevPoint != null) {
                int prevScaledX = (int) (prevPoint.getX() * scale + offsetX);
                int prevScaledY = (int) (prevPoint.getY() * scale + offsetY);
                g2d.drawLine(prevScaledX, prevScaledY, scaledX, scaledY);
            }

            prevPoint = point;
        }
    }
}
