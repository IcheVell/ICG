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

            for (int j = i + curveModel.getM(); j < curveModel.getScreenFigurePoints().size(); j += curveModel.getM()) {
                Point2D endPoint = curveModel.getScreenFigurePoints().get(j);

                int x1 = (int) startPoint.getX();
                int y1 = (int) startPoint.getY();

                int x2 = (int) endPoint.getX();
                int y2 = (int) endPoint.getY();

                double depth1 = curveModel.getFigurePointsDepth().get(i);
                double depth2 = curveModel.getFigurePointsDepth().get(j);

                double averageDepth = (depth1 + depth2) / 2;
                if (averageDepth <= 10) {
                    g2d.setColor(Color.BLACK);
                } else {
                    g2d.setColor(Color.GRAY);
                }
                
                g2d.drawLine(x1, y1, x2, y2);

                startPoint = endPoint;
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

                    double averageDepth = (depth1 + depth2) / 2;
                    if (averageDepth <= 10) {
                        g2d.setColor(Color.BLACK);
                    } else {
                        g2d.setColor(Color.GRAY);
                    }

                    g2d.drawLine((int) startPoint.getX(), (int) startPoint.getY(), (int) endPoint.getX(), (int) endPoint.getY());
                }
            }

            return;
        }

        int additionalPerCircle = M * (M1 - 1);

        for (int base = 0, additionalBase = 0;
             base < mainPoints.size();
             base += M, additionalBase += additionalPerCircle) {

            for (int seg = 0; seg < M; seg++) {
                Point2D currentPoint = mainPoints.get(base + seg);

                int segmentAdditionalBase = additionalBase + seg * (M1 - 1);

                for (int k = 0; k < M1 - 1; k++) {
                    Point2D midPoint = additionalPoints.get(segmentAdditionalBase + k);

                    double depth1 = curveModel.getCirclePointsDepth().get(base + seg);
                    double depth2 = curveModel.getAdditionalCirclePointsDepth().get(segmentAdditionalBase + k);

                    double averageDepth = (depth1 + depth2) / 2;
                    if (averageDepth <= 10) {
                        g2d.setColor(Color.BLACK);
                    } else {
                        g2d.setColor(Color.GRAY);
                    }

                    g2d.drawLine((int) currentPoint.getX(), (int) currentPoint.getY(), (int) midPoint.getX(), (int) midPoint.getY());

                    currentPoint = midPoint;
                }

                double depth2 = curveModel.getCirclePointsDepth().get(base + ((seg + 1) % M1));
                double averageDepth = (depth2 + depth2) / 2;

                if (averageDepth <= 10) {
                    g2d.setColor(Color.BLACK);
                } else {
                    g2d.setColor(Color.GRAY);
                }

                Point2D endPoint = mainPoints.get(base + ((seg + 1) % M));

                g2d.drawLine((int) currentPoint.getX(), (int) currentPoint.getY(), (int) endPoint.getX(), (int) endPoint.getY());
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
}
