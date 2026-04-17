package controllers;

import models.CurveModel;
import view.Frame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

public class FigureController extends MouseAdapter {
    private final CurveModel curveModel;
    private final Frame frame;

    private Point2D dragStart;
    private Point2D dragEnd;

    public FigureController(CurveModel curveModel, Frame frame) {
        this.curveModel = curveModel;
        this.frame = frame;

        frame.getMainPanel().addMouseListener(this);
        frame.getMainPanel().addMouseMotionListener(this);
        frame.getMainPanel().addMouseWheelListener(this);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0 && curveModel.getZN() < 5) {
            curveModel.setZN(curveModel.getZN() + 0.05);
        }

        if (e.getWheelRotation() > 0 && curveModel.getZN() > 0.2) {
            curveModel.setZN(curveModel.getZN() - 0.05);
        }

        curveModel.calculateFinalPoints();
        curveModel.convertProjectPointsToScreen(frame.getMainPanel().getWidth(), frame.getMainPanel().getHeight());

        frame.getMainPanel().repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragStart = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragStart == null) {
            return;
        }

        dragEnd = e.getPoint();

        double dx = dragEnd.getX() - dragStart.getX();
        double dy = dragEnd.getY() - dragStart.getY();

        double scale = curveModel.getRotateScale();

        double rotateRadiansY = Math.toRadians(dx * scale);
        double rotateRadiansX = Math.toRadians(dy * scale);

        curveModel.setModelRadiansAngleX(rotateRadiansX + curveModel.getModelRadiansAngleX());
        curveModel.setModelRadiansAngleY(rotateRadiansY + curveModel.getModelRadiansAngleY());

        curveModel.rotate3DPoints();
        curveModel.calculateFinalPoints();
        curveModel.convertProjectPointsToScreen(frame.getMainPanel().getWidth(), frame.getMainPanel().getHeight());

        frame.getMainPanel().repaint();

        dragStart = dragEnd;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragEnd = dragStart = null;
    }
}
