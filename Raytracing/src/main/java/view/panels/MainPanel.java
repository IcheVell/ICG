package view.panels;

import model.*;
import model.Box;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainPanel extends JPanel {
    private final SceneModel sceneModel;
    private final RenderModel renderModel;
    private final Camera camera;
    private BufferedImage renderedImage;

    public MainPanel(SceneModel sceneModel, RenderModel renderModel, Camera camera) {
        this.sceneModel = sceneModel;
        this.renderModel = renderModel;
        this.camera = camera;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                camera.updateAspectRatio(getWidth(), getHeight());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        if (renderedImage != null) {
            g2d.drawImage(renderedImage, 0, 0, getWidth(), getHeight(), null);
            return;
        }

        g2d.setBackground(new Color(renderModel.getBackgroundR(), renderModel.getBackgroundG(), renderModel.getBackgroundB()));

        g2d.setColor(Color.BLACK);

        if (renderModel.getBackgroundR() < 40 && renderModel.getBackgroundB() < 40 && renderModel.getBackgroundG() < 40) {
            g2d.setColor(Color.YELLOW);
        }

        List<Sphere> spheres = sceneModel.getSpheres();
        List<Box> boxes = sceneModel.getBoxes();
        List<Triangle> triangles = sceneModel.getTriangles();
        List<Quadrangle> quadrangles = sceneModel.getQuadrangles();

        for (Sphere sphere : spheres) {
            drawSphere(g2d, sphere, getWidth(), getHeight());
        }

        for (Box box : boxes) {
            drawBox(g2d, box, getWidth(), getHeight());
        }

        for (Triangle triangle : triangles) {
            drawTriangle(g2d, triangle, getWidth(), getHeight());
        }

        for (Quadrangle quadrangle : quadrangles) {
            drawQuadrangle(g2d, quadrangle, getWidth(), getHeight());
        }
    }

    private void drawTriangle(Graphics2D g2d, Triangle triangle, int panelWidth, int panelHeight) {
        List<Point3D> points = triangle.points();

        if (points.size() != 3) {
            return;
        }

        drawLine3D(g2d, points.get(0), points.get(1), panelWidth, panelHeight);
        drawLine3D(g2d, points.get(1), points.get(2), panelWidth, panelHeight);
        drawLine3D(g2d, points.get(2), points.get(0), panelWidth, panelHeight);
    }

    private void drawQuadrangle(Graphics2D g2d, Quadrangle quadrangle, int panelWidth, int panelHeight) {
        List<Point3D> points = quadrangle.points();

        if (points.size() != 4) {
            return;
        }

        drawLine3D(g2d, points.get(0), points.get(1), panelWidth, panelHeight);
        drawLine3D(g2d, points.get(1), points.get(2), panelWidth, panelHeight);
        drawLine3D(g2d, points.get(2), points.get(3), panelWidth, panelHeight);
        drawLine3D(g2d, points.get(3), points.get(0), panelWidth, panelHeight);
    }

    private void drawBox(Graphics2D g2d, Box box, int panelWidth, int panelHeight) {
        List<Point3D> points = box.points();

        if (points.size() != 2) {
            return;
        }

        Point3D min = points.get(0);
        Point3D max = points.get(1);

        double minX = min.getX();
        double minY = min.getY();
        double minZ = min.getZ();

        double maxX = max.getX();
        double maxY = max.getY();
        double maxZ = max.getZ();

        Point3D p000 = new Point3D(minX, minY, minZ);
        Point3D p100 = new Point3D(maxX, minY, minZ);
        Point3D p110 = new Point3D(maxX, maxY, minZ);
        Point3D p010 = new Point3D(minX, maxY, minZ);

        Point3D p001 = new Point3D(minX, minY, maxZ);
        Point3D p101 = new Point3D(maxX, minY, maxZ);
        Point3D p111 = new Point3D(maxX, maxY, maxZ);
        Point3D p011 = new Point3D(minX, maxY, maxZ);

        drawLine3D(g2d, p000, p100, panelWidth, panelHeight);
        drawLine3D(g2d, p100, p110, panelWidth, panelHeight);
        drawLine3D(g2d, p110, p010, panelWidth, panelHeight);
        drawLine3D(g2d, p010, p000, panelWidth, panelHeight);

        drawLine3D(g2d, p001, p101, panelWidth, panelHeight);
        drawLine3D(g2d, p101, p111, panelWidth, panelHeight);
        drawLine3D(g2d, p111, p011, panelWidth, panelHeight);
        drawLine3D(g2d, p011, p001, panelWidth, panelHeight);

        drawLine3D(g2d, p000, p001, panelWidth, panelHeight);
        drawLine3D(g2d, p100, p101, panelWidth, panelHeight);
        drawLine3D(g2d, p110, p111, panelWidth, panelHeight);
        drawLine3D(g2d, p010, p011, panelWidth, panelHeight);
    }

    private void drawSphere(Graphics2D g2d, Sphere sphere, int panelWidth, int panelHeight) {
        List<Point3D> points = sphere.center();

        if (points.size() != 1) {
            return;
        }

        Point3D center = points.get(0);
        double radius = sphere.radius();

        int segments = 48;

        drawSphereCircleXY(g2d, center, radius, segments, panelWidth, panelHeight);
        drawSphereCircleXZ(g2d, center, radius, segments, panelWidth, panelHeight);
        drawSphereCircleYZ(g2d, center, radius, segments, panelWidth, panelHeight);
    }

    private void drawSphereCircleXY(Graphics2D g2d, Point3D center, double radius, int segments, int panelWidth, int panelHeight) {
        Point3D prev = null;

        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;

            Point3D current = new Point3D(
                    center.getX() + radius * Math.cos(angle),
                    center.getY() + radius * Math.sin(angle),
                    center.getZ()
            );

            if (prev != null) {
                drawLine3D(g2d, prev, current, panelWidth, panelHeight);
            }

            prev = current;
        }
    }

    private void drawSphereCircleXZ(Graphics2D g2d, Point3D center, double radius, int segments, int panelWidth, int panelHeight) {
        Point3D prev = null;

        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;

            Point3D current = new Point3D(
                    center.getX() + radius * Math.cos(angle),
                    center.getY(),
                    center.getZ() + radius * Math.sin(angle)
            );

            if (prev != null) {
                drawLine3D(g2d, prev, current, panelWidth, panelHeight);
            }

            prev = current;
        }
    }

    private void drawSphereCircleYZ(Graphics2D g2d, Point3D center, double radius, int segments, int panelWidth, int panelHeight) {
        Point3D prev = null;

        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;

            Point3D current = new Point3D(
                    center.getX(),
                    center.getY() + radius * Math.cos(angle),
                    center.getZ() + radius * Math.sin(angle)
            );

            if (prev != null) {
                drawLine3D(g2d, prev, current, panelWidth, panelHeight);
            }

            prev = current;
        }
    }

    private void drawLine3D(Graphics2D g2d, Point3D p1, Point3D p2, int panelWidth, int panelHeight) {
        Point3D screenP1 = camera.convertModelToScreen(p1, panelWidth, panelHeight);
        Point3D screenP2 = camera.convertModelToScreen(p2, panelWidth, panelHeight);

        if (screenP1 == null || screenP2 == null) {
            return;
        }

        g2d.drawLine(
                (int) Math.round(screenP1.getX()),
                (int) Math.round(screenP1.getY()),
                (int) Math.round(screenP2.getX()),
                (int) Math.round(screenP2.getY())
        );
    }

    public void saveImage(String path) throws IOException {
        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0) {
            throw new IOException("Panel has invalid size");
        }

        BufferedImage image;

        if (renderedImage != null) {
            image = renderedImage;
        } else {
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = image.createGraphics();

            try {
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, width, height);

                paint(g2d);
            } finally {
                g2d.dispose();
            }
        }

        String format = getImageFormat(path);

        if (!ImageIO.write(image, format, new File(path))) {
            throw new IOException("Unsupported image format: " + format);
        }
    }

    private String getImageFormat(String path) {
        String lowerPath = path.toLowerCase();

        if (lowerPath.endsWith(".jpg") || lowerPath.endsWith(".jpeg")) {
            return "jpg";
        }

        if (lowerPath.endsWith(".bmp")) {
            return "bmp";
        }

        return "png";
    }

    public void clearRenderedImage() {
        renderedImage = null;
        repaint();
    }

    public void setRenderedImage(BufferedImage renderedImage) {
        this.renderedImage = renderedImage;
        repaint();
    }
}
