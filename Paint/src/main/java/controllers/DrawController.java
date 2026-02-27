package controllers;

import enums.Mode;
import models.CanvasModel;
import view.drawpanel.DrawPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawController extends MouseAdapter {
    private final CanvasModel canvasModel;
    private final DrawPanel drawPanel;

    public DrawController(CanvasModel canvasModel, DrawPanel drawPanel) {
        this.canvasModel = canvasModel;
        this.drawPanel = drawPanel;

        drawPanel.setCanvasModel(canvasModel);

        canvasModel.clearCanvasModel();
        drawPanel.repaint();

        drawPanel.addMouseListener(this);
        drawPanel.addMouseMotionListener(this);
    }

    public void mousePressed(MouseEvent e) {
        if (canvasModel.getMode() == Mode.LINE) {
            canvasModel.setStartPoint(e.getPoint());
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (canvasModel.getMode() == Mode.LINE) {
            canvasModel.line();
            drawPanel.repaint();
        }
    }

    public void mouseClicked(MouseEvent e) {
        canvasModel.setStartPoint(e.getPoint());

        switch (canvasModel.getMode()) {
            case STAMP -> canvasModel.stamp();

            case FILL -> canvasModel.fill();
        }

        drawPanel.repaint();
    }

    public void mouseDragged(MouseEvent e) {
        if (canvasModel.getMode() == Mode.LINE) {
            Point p = e.getPoint();
            if (p.x < 0) {
                p.x = 0;
            }

            if (p.y < 0) {
                p.y = 0;
            }

            if (p.x >= canvasModel.getWidth()) {
                p.x = canvasModel.getWidth() - 1;
            }

            if (p.y >= canvasModel.getHeight()) {
                p.y = canvasModel.getHeight() - 1;
            }

            canvasModel.setEndPoint(p);
        }
    }

}
