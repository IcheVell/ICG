package models;

import enums.MatrixType;
import models.matrices.MatrixFactory;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurveModel {
    private final double[][] splineMatrix = {
            {-1f / 6, 1f / 2, -1f / 2, 1f / 6},
            {1f / 2, -1, 1f / 2, 0},
            {-1f / 2, 0, 1f / 2, 0},
            {1f / 6, 4f / 6, 1f / 6, 0}
    };

    private double scale = 1;
    private double ZN = 1;

    private int N = 1;
    private int K = 0;
    private int M = 2;
    private int M1 = 1;

    private final double rotateScale = 0.05;

    private List<Point> controlPoints;
    private List<Point2D> splinePoints;
    private List<Point3D> figure3DPoints;
    private List<Point3D> circle3DPoints;

    private List<Point3D> additionalCircle3DPoints;

    private List<Point3D> axis3DPoints;

    private List<Point3D> rotatedFigure3DPoints;
    private List<Point3D> rotatedCircle3DPoints;
    private List<Point3D> rotatedAxis3DPoints;

    private List<Point3D> rotatedAdditionalCircle3DPoints;

    private List<Point3D> projectedFigure3DPoints;
    private List<Point3D> projectedCircle3DPoints;
    private List<Point3D> projectedAdditionalCircle3DPoints;

    private List<Point2D> screenFigurePoints;
    private List<Point2D> screenCirclePoints;
    private List<Point2D> screenAdditionalCirclePoints;

    private List<Double> figurePointsDepth;
    private List<Double> circlePointsDepth;
    private List<Double> additionalCirclePointsDepth;

    private double modelRadiansAngleX = 0;
    private double modelRadiansAngleY = 0;

    public CurveModel() {
        controlPoints = new ArrayList<>();
        splinePoints = new ArrayList<>();
        figure3DPoints = new ArrayList<>();
        circle3DPoints = new ArrayList<>();

        axis3DPoints = new ArrayList<>();
        axis3DPoints.add(new Point3D(0f, 0f, 0f));
        axis3DPoints.add(new Point3D(0.2f, 0f, 0f));
        axis3DPoints.add(new Point3D(0f, 0.2f, 0f));
        axis3DPoints.add(new Point3D(0f, 0f, 0.2f));

        rotatedFigure3DPoints = new ArrayList<>();
        rotatedCircle3DPoints = new ArrayList<>();
        rotatedAxis3DPoints = new ArrayList<>(axis3DPoints);

        rotatedAdditionalCircle3DPoints = new ArrayList<>();

        additionalCircle3DPoints = new ArrayList<>();

        projectedFigure3DPoints = new ArrayList<>();
        projectedCircle3DPoints = new ArrayList<>();
        projectedAdditionalCircle3DPoints = new ArrayList<>();

        screenFigurePoints = new ArrayList<>();
        screenCirclePoints = new ArrayList<>();
        screenAdditionalCirclePoints = new ArrayList<>();

        figurePointsDepth = new ArrayList<>();
        circlePointsDepth = new ArrayList<>();
        additionalCirclePointsDepth = new ArrayList<>();
    }

    public int getK() {
        return K;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public int getM1() {
        return M1;
    }

    public List<Point> getControlPoints() {
        return controlPoints;
    }

    public List<Point2D> getSplinePoints() {
        return splinePoints;
    }

    public List<Point3D> getFigure3DPoints() {
        return figure3DPoints;
    }

    public List<Point3D> getCircle3DPoints() {
        return circle3DPoints;
    }

    public int getCircleRadius() {
        return (int) (10 * scale);
    }

    public double getScale() {
        return scale;
    }

    public double getRotateScale() {
        return rotateScale;
    }

    public double getModelRadiansAngleX() {
        return modelRadiansAngleX;
    }

    public double getModelRadiansAngleY() {
        return modelRadiansAngleY;
    }

    public List<Point3D> getRotatedAxis3DPoints() {
        return rotatedAxis3DPoints;
    }

    public double getZN() {
        return ZN;
    }

    public List<Point2D> getScreenFigurePoints() {
        return screenFigurePoints;
    }

    public List<Point2D> getScreenCirclePoints() {
        return screenCirclePoints;
    }

    public List<Point2D> getScreenAdditionalCirclePoints() {
        return screenAdditionalCirclePoints;
    }

    public List<Double> getFigurePointsDepth() {
        return figurePointsDepth;
    }

    public List<Double> getCirclePointsDepth() {
        return circlePointsDepth;
    }

    public List<Double> getAdditionalCirclePointsDepth() {
        return additionalCirclePointsDepth;
    }

    public void setK(int k) {
        K = k;
    }

    public void setN(int n) {
        N = n;
    }

    public void setM1(int m1) {
        M1 = m1;
    }

    public void setM(int m) {
        M = m;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setModelRadiansAngleX(double modelRadiansAngleX) {
        this.modelRadiansAngleX = modelRadiansAngleX;
    }

    public void setModelRadiansAngleY(double modelRadiansAngleY) {
        this.modelRadiansAngleY = modelRadiansAngleY;
    }

    public void setZN(double ZN) {
        this.ZN = ZN;
    }

    public void calculateSplinePoints() {
        splinePoints.clear();

        for (int i = 1; i < controlPoints.size() - 2; i++) {
            double[] xCoords = {controlPoints.get(i - 1).getX(), controlPoints.get(i).getX(), controlPoints.get(i + 1).getX(), controlPoints.get(i + 2).getX()};
            double[] yCoords = {controlPoints.get(i - 1).getY(), controlPoints.get(i).getY(), controlPoints.get(i + 1).getY(),  controlPoints.get(i + 2).getY()};

            for (int j = 0; j <= N; j++) {
                if (j == 0 && i != 1) {
                    continue;
                }

                double t = (double) j / N;

                double[] T = {Math.pow(t, 3), Math.pow(t, 2), t, 1};
                double[] a = new double[4];

                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        a[k] += T[l] * splineMatrix[l][k];
                    }
                }

                double u = 0;
                double v = 0;

                for (int z = 0; z < 4; z++) {
                    u += a[z] * xCoords[z];
                    v += a[z] * yCoords[z];
                }

                splinePoints.add(new Point2D.Double(u, v));
            }
        }
    }

    public void calculate3DPoints() {
        figure3DPoints.clear();
        circle3DPoints.clear();

        for (int i = 0; i < splinePoints.size(); i++) {
            Point2D point = splinePoints.get(i);

            double u = point.getX();
            double v = point.getY();
            for (int j = 0; j < M; j++) {
                double radians = Math.toRadians((j * 360f / M));

                double newX = v * Math.cos(radians);
                double newY = v * Math.sin(radians);

                figure3DPoints.add(new Point3D(newX, newY, u));

                if (i % N == 0) {
                    circle3DPoints.add(new Point3D(newX, newY, u));
                }
            }
        }
    }

    public void normalize3DPoints() {
        double xMin = Double.MAX_VALUE;
        double xMax = -Double.MAX_VALUE;

        double yMin = Double.MAX_VALUE;
        double yMax = -Double.MAX_VALUE;

        double zMin = Double.MAX_VALUE;
        double zMax = -Double.MAX_VALUE;

        for (Point3D point : getFigure3DPoints()) {
            xMin = Math.min(xMin, point.getX());
            xMax = Math.max(xMax, point.getX());

            yMin = Math.min(yMin, point.getY());
            yMax = Math.max(yMax, point.getY());

            zMin = Math.min(zMin, point.getZ());
            zMax = Math.max(zMax, point.getZ());
        }

        for (Point3D point : getCircle3DPoints()) {
            xMin = Math.min(xMin, point.getX());
            xMax = Math.max(xMax, point.getX());

            yMin = Math.min(yMin, point.getY());
            yMax = Math.max(yMax, point.getY());

            zMin = Math.min(zMin, point.getZ());
            zMax = Math.max(zMax, point.getZ());
        }

        double dx = xMax - xMin;
        double dy = yMax - yMin;
        double dz = zMax - zMin;

        double offsetX = xMin + dx / 2;
        double offsetY = yMin + dy / 2;
        double offsetZ = zMin + dz / 2;

        double dMax =  Math.max(dx, dy);
        dMax = Math.max(dMax, dz);

        for (Point3D point : getFigure3DPoints()) {
            double x = point.getX();
            double y = point.getY();
            double z = point.getZ();

            point.setX((x - offsetX) / (dMax / 2));
            point.setY((y - offsetY) / (dMax / 2));
            point.setZ((z - offsetZ) / (dMax / 2));
        }

        for (Point3D point : getCircle3DPoints()) {
            double x = point.getX();
            double y = point.getY();
            double z = point.getZ();

            point.setX((x - offsetX) / (dMax / 2));
            point.setY((y - offsetY) / (dMax / 2));
            point.setZ((z - offsetZ) / (dMax / 2));
        }
    }

    public double[][] mulMatrices(double[][] rotateX, double[][] rotateY) {
        double[][] resMatrix = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    resMatrix[i][j] += rotateX[i][k] * rotateY[k][j];
                }
            }
        }

        return resMatrix;
    }

    public void rotate3DPoints() {
        rotatedFigure3DPoints.clear();
        rotatedCircle3DPoints.clear();
        rotatedAxis3DPoints.clear();
        rotatedAdditionalCircle3DPoints.clear();

        double[][] rotateX = MatrixFactory.create(MatrixType.ROTATE_X, getModelRadiansAngleX());
        double[][] rotateY = MatrixFactory.create(MatrixType.ROTATE_Y, getModelRadiansAngleY());

        double[][] rotateMatrix = mulMatrices(rotateY, rotateX);

        rotate3DPointsArray(figure3DPoints, rotatedFigure3DPoints, rotateMatrix);
        rotate3DPointsArray(circle3DPoints, rotatedCircle3DPoints, rotateMatrix);
        rotate3DPointsArray(axis3DPoints, rotatedAxis3DPoints, rotateMatrix);
        rotate3DPointsArray(additionalCircle3DPoints, rotatedAdditionalCircle3DPoints, rotateMatrix);
    }

    private void rotate3DPointsArray(List<Point3D> points, List<Point3D> rotatedPoints, double[][] rotateMatrix) {
        for (Point3D point : points) {
            double oldX = point.getX();
            double oldY = point.getY();
            double oldZ = point.getZ();

            double newX = 0;
            double newY = 0;
            double newZ = 0;

            newX += oldX * rotateMatrix[0][0];
            newX += oldY * rotateMatrix[0][1];
            newX += oldZ * rotateMatrix[0][2];

            newY += oldX * rotateMatrix[1][0];
            newY += oldY * rotateMatrix[1][1];
            newY += oldZ * rotateMatrix[1][2];

            newZ += oldX * rotateMatrix[2][0];
            newZ += oldY * rotateMatrix[2][1];
            newZ += oldZ * rotateMatrix[2][2];

            Point3D newPoint = new Point3D(newX, newY, newZ);
            rotatedPoints.add(newPoint);
        }
    }

    public void calculateFinalPoints() {
        projectedFigure3DPoints.clear();
        projectedCircle3DPoints.clear();
        projectedAdditionalCircle3DPoints.clear();

        figurePointsDepth.clear();
        circlePointsDepth.clear();
        additionalCirclePointsDepth.clear();

        for (Point3D point : rotatedFigure3DPoints) {
            Point3D camPoint = calculateCamPoint(point);
            figurePointsDepth.add(camPoint.getZ());

            Point3D finalPoint = calculate3DProjectPoint(camPoint);

            projectedFigure3DPoints.add(finalPoint);
        }

        for (Point3D point : rotatedCircle3DPoints) {
            Point3D camPoint = calculateCamPoint(point);
            circlePointsDepth.add(camPoint.getZ());

            Point3D finalPoint = calculate3DProjectPoint(camPoint);

            projectedCircle3DPoints.add(finalPoint);
        }

        for (Point3D point : rotatedAdditionalCircle3DPoints) {
            Point3D camPoint = calculateCamPoint(point);
            additionalCirclePointsDepth.add(camPoint.getZ());

            Point3D finalPoint = calculate3DProjectPoint(camPoint);

            projectedAdditionalCircle3DPoints.add(finalPoint);
        }
    }

    private Point3D calculateCamPoint(Point3D point) {
        return new Point3D(point.getZ(), point.getY(), point.getX() + 10);
    }

    private Point3D calculate3DProjectPoint(Point3D point) {
        double pX = ZN * (point.getX() / point.getZ());
        double pY = ZN * (point.getY() / point.getZ());

        return new Point3D(pX, pY, point.getZ());
    }

    public void convertProjectPointsToScreen(int width, int height) {
        screenFigurePoints.clear();
        screenCirclePoints.clear();
        screenAdditionalCirclePoints.clear();

        int offsetX = width / 2;
        int offsetY = height / 2;

        double scale = Math.min(offsetX, offsetY);

        for (Point3D point : projectedFigure3DPoints) {
            int x = (int) (point.getX() * scale + offsetX);
            int y = (int) (offsetY - point.getY() * scale);

            screenFigurePoints.add(new Point2D.Double(x, y));
        }

        for (Point3D point : projectedCircle3DPoints) {
            int x = (int) (point.getX() * scale + offsetX);
            int y = (int) (offsetY - point.getY() * scale);

            screenCirclePoints.add(new Point2D.Double(x, y));
        }

        for (Point3D point : projectedAdditionalCircle3DPoints) {
            int x = (int) (point.getX() * scale + offsetX);
            int y = (int) (offsetY - point.getY() * scale);

            screenAdditionalCirclePoints.add(new Point2D.Double(x, y));
        }
    }

    public void calculateM1CirclePoints() {
        additionalCircle3DPoints.clear();

        if (M1 <= 1 || M < 2 || circle3DPoints.isEmpty()) {
            return;
        }

        double expectedDelta = 2.0 * Math.PI / M;
        double twoPi = 2.0 * Math.PI;

        for (int base = 0; base + M <= circle3DPoints.size(); base += M) {
            for (int seg = 0; seg < M; seg++) {
                Point3D firstPoint = circle3DPoints.get(base + seg);
                Point3D secondPoint = circle3DPoints.get(base + ((seg + 1) % M));

                double x1 = firstPoint.getX();
                double y1 = firstPoint.getY();
                double z1 = firstPoint.getZ();

                double x2 = secondPoint.getX();
                double y2 = secondPoint.getY();
                double z2 = secondPoint.getZ();

                double firstAngle = Math.atan2(y1, x1);
                double secondAngle = Math.atan2(y2, x2);

                double bestSecondAngle = secondAngle;
                double bestDiff = Math.abs(bestSecondAngle - (firstAngle + expectedDelta));

                for (int shift = -2; shift <= 2; shift++) {
                    double candidate = secondAngle + shift * twoPi;
                    double diff = Math.abs(candidate - (firstAngle + expectedDelta));
                    if (diff < bestDiff) {
                        bestDiff = diff;
                        bestSecondAngle = candidate;
                    }
                }

                for (int k = 1; k < M1; k++) {
                    double t = (double) k / M1;

                    double angle = firstAngle + (bestSecondAngle - firstAngle) * t;

                    double radius1 = Math.hypot(x1, y1);
                    double radius2 = Math.hypot(x2, y2);
                    double radius = radius1 + (radius2 - radius1) * t;

                    double z = z1 + (z2 - z1) * t;

                    double newX = radius * Math.cos(angle);
                    double newY = radius * Math.sin(angle);

                    additionalCircle3DPoints.add(new Point3D(newX, newY, z));
                }
            }
        }
    }
}
