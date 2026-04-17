package controllers;

import models.CurveModel;
import view.dialogs.ConfigureDialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

public class SplineController extends MouseAdapter {
    private final CurveModel curveModel;
    private final ConfigureDialog configureDialog;

    private Point2D draggedPoint;

    public SplineController(CurveModel curveModel, ConfigureDialog configureDialog) {
        this.curveModel = curveModel;
        this.configureDialog = configureDialog;

        configureDialog.getSplinePanel().addMouseListener(this);
        configureDialog.getSplinePanel().addMouseMotionListener(this);
        configureDialog.getSplinePanel().addMouseWheelListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double scale = curveModel.getScale();
        int offsetX = configureDialog.getSplinePanel().getWidth() / 2;
        int offsetY = configureDialog.getSplinePanel().getHeight() / 2;

        int x = (int) ((e.getX() - offsetX) / scale);
        int y = (int) ((e.getY() - offsetY) / scale);

        if (e.getButton() == MouseEvent.BUTTON1) {
            curveModel.getControlPoints().add(new Point(x, y));

            curveModel.setK(curveModel.getK() + 1);

            configureDialog.getSplineConfigPanel().getSpinnerK().setValue(curveModel.getK());
            configureDialog.getSplineConfigPanel().getSpinnerN().setValue(curveModel.getN());

            curveModel.calculateSplinePoints();

            configureDialog.getSplinePanel().repaint();
        }

        if (e.getButton() == MouseEvent.BUTTON3) {
            int radius = curveModel.getCircleRadius();

            curveModel.getControlPoints()
                    .stream()
                    .filter(p -> (x <= p.getX() + radius && x >= p.getX() - radius && y <= p.getY() + radius && y >= p.getY() - radius))
                    .findFirst()
                    .ifPresent(controlPoint -> {
                        curveModel.getControlPoints().remove(controlPoint);

                        configureDialog.getSplineConfigPanel().getSpinnerN().setValue(curveModel.getN());
                        configureDialog.getSplineConfigPanel().getSpinnerK().setValue(curveModel.getControlPoints().size());
                    });

            curveModel.calculateSplinePoints();

            configureDialog.getSplinePanel().repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int offsetX = configureDialog.getSplinePanel().getWidth() / 2;
        int offsetY = configureDialog.getSplinePanel().getHeight() / 2;

        double scale = curveModel.getScale();

        if (e.getButton() == MouseEvent.BUTTON1) {
            int x = (int) ((e.getX() - offsetX) / scale);
            int y = (int) ((e.getY() - offsetY) / scale);

            int radius = curveModel.getCircleRadius();

            if (draggedPoint == null) {
                curveModel.getControlPoints()
                        .stream()
                        .filter(p -> (x <= p.getX() + radius && x >= p.getX() - radius && y <= p.getY() + radius && y >= p.getY() - radius))
                        .findFirst().ifPresent(p -> draggedPoint = p);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int offsetX = configureDialog.getSplinePanel().getWidth() / 2;
        int offsetY = configureDialog.getSplinePanel().getHeight() / 2;
        double scale = curveModel.getScale();

        int x = (int) ((e.getX() - offsetX) / scale);
        int y = (int) ((e.getY() - offsetY) / scale);

        if (e.getX() > configureDialog.getSplinePanel().getWidth() || e.getX() < 0 || e.getY() > configureDialog.getSplinePanel().getHeight() || e.getY() < 0) {
            return;
        }

        if (draggedPoint != null) {
            draggedPoint.setLocation(new Point2D.Double(x, y));
            curveModel.calculateSplinePoints();

            configureDialog.getSplinePanel().repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && draggedPoint != null) {
            draggedPoint = null;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0 && curveModel.getScale() < 1.5) {
            curveModel.setScale(curveModel.getScale() + 0.05);
        }

        if (e.getWheelRotation() > 0 && curveModel.getScale() > 0.25) {
            curveModel.setScale(curveModel.getScale() - 0.05);
        }

        configureDialog.getSplinePanel().repaint();
    }
}
