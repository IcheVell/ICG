package view.panels;

import models.CurveModel;
import models.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;

public class MainPanel extends JPanel {
    private final CurveModel curveModel;

    public MainPanel(CurveModel curveModel) {
        this.curveModel = curveModel;
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                curveModel.convertProjectPointsToScreen(getWidth(), getHeight());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (curveModel.getControlPoints().isEmpty()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        drawAxis(g2d);
        drawGeneratrix(g2d);
        drawCircles(g2d);
    }

    private void drawAxis(Graphics2D g2d) {
        Point3D zeroPoint = curveModel.getRotatedAxis3DPoints().getFirst();
        Color[] colors = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE};

        for (int i = 1; i < curveModel.getRotatedAxis3DPoints().size(); i++) {
            g2d.setColor(colors[i]);
            drawAxisLines(g2d, zeroPoint, curveModel.getRotatedAxis3DPoints().get(i));
        }

        g2d.setColor(Color.BLACK);
    }

    private void drawGeneratrix(Graphics2D g2d) {
        for (int i = 0; i < curveModel.getM(); i++) {
            Point2D startPoint = curveModel.getScreenFigurePoints().get(i);
            double depth1 = curveModel.getFigurePointsDepth().get(i);

            for (int j = i + curveModel.getM(); j < curveModel.getScreenFigurePoints().size(); j += curveModel.getM()) {
                Point2D endPoint = curveModel.getScreenFigurePoints().get(j);

                double depth2 = curveModel.getFigurePointsDepth().get(j);

                drawLineBresenham(startPoint, endPoint, depth1, depth2, g2d);

                startPoint = endPoint;
                depth1 = depth2;
            }
        }
    }

    private void drawCircles(Graphics2D g2d) {
        int M = curveModel.getM();
        int M1 = curveModel.getM1();

        java.util.List<Point2D> mainPoints = curveModel.getScreenCirclePoints();
        java.util.List<Point2D> additionalPoints = curveModel.getScreenAdditionalCirclePoints();

        if (mainPoints.isEmpty()) {
            return;
        }

        if (M1 == 1) {
            for (int base = 0; base < mainPoints.size(); base += M) {
                for (int seg = 0; seg < M; seg++) {
                    Point2D startPoint = mainPoints.get(base + seg);
                    Point2D endPoint = mainPoints.get(base + ((seg + 1) % M));

                    double depth1 = curveModel.getCirclePointsDepth().get(base + seg);
                    double depth2 = curveModel.getCirclePointsDepth().get(base + ((seg + 1) % M));

                    drawLineBresenham(startPoint, endPoint, depth1, depth2, g2d);
                }
            }

            return;
        }

        int additionalPerCircle = M * (M1 - 1);

        for (int base = 0, additionalBase = 0; base < mainPoints.size(); base += M, additionalBase += additionalPerCircle) {
            for (int seg = 0; seg < M; seg++) {
                Point2D currentPoint = mainPoints.get(base + seg);

                double depth1 = curveModel.getCirclePointsDepth().get(base + seg);

                int segmentAdditionalBase = additionalBase + seg * (M1 - 1);

                for (int k = 0; k < M1 - 1; k++) {
                    Point2D midPoint = additionalPoints.get(segmentAdditionalBase + k);

                    double depth2 = curveModel.getAdditionalCirclePointsDepth().get(segmentAdditionalBase + k);

                    drawLineBresenham(currentPoint, midPoint, depth1, depth2, g2d);

                    currentPoint = midPoint;
                    depth1 = depth2;
                }

                double depth2 = curveModel.getCirclePointsDepth().get(base + ((seg + 1) % M));

                Point2D endPoint = mainPoints.get(base + ((seg + 1) % M));

                drawLineBresenham(currentPoint, endPoint, depth1, depth2, g2d);
            }
        }
    }

    private void drawAxisLines(Graphics2D g2d, Point3D startPoint, Point3D endPoint) {
        int offsetX = getWidth() / 10;
        int offsetY = getHeight() / 10;

        double scale = Math.min(offsetX, offsetY);

        int x1 = (int) (startPoint.getX() * scale + offsetX);
        int y1 = (int) (offsetY - startPoint.getY() * scale);

        int x2 = (int) (endPoint.getX() * scale + offsetX);
        int y2 = (int) (offsetY - endPoint.getY() * scale);

        g2d.drawLine(x1, y1, x2, y2);
    }

    private void drawLineBresenham(Point2D startPoint, Point2D endPoint, double startDepth, double endDepth, Graphics2D g2d) {
        int x1 = (int) startPoint.getX();
        int y1 = (int) startPoint.getY();
        int x2 = (int) endPoint.getX();
        int y2 = (int) endPoint.getY();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int totalSteps = Math.max(dx, dy);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        int x = x1, y = y1;

        double zStep = (startDepth - endDepth) / totalSteps;

        int iter = 0;
        while (x != x2 || y != y2) {
            double depth = startDepth - zStep * iter;

            double t = (depth - 9) / (11 - 9);
            t = Math.max(0.0, Math.min(1.0, t));

            int val = (int) (30 + 190 * t);
            val = Math.max(0, Math.min(255, val));

            g2d.setColor(new Color(val, val, val));

            g2d.fillRect(x, y, 1, 1);

            int e2 = 2 * err;

            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }

            if (e2 < dx) {
                err += dx;
                y += sy;
            }

            iter++;
        }
    }
}
