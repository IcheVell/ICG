package view.drawpanel;

import models.CanvasModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DrawPanel extends JPanel {
    private CanvasModel canvasModel;

    public DrawPanel() {
        setLayout(new BorderLayout());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newWidth = Math.max(canvasModel.getWidth(), getWidth());
                int newHeight = Math.max(canvasModel.getHeight(), getHeight());

                canvasModel.resize(newWidth, newHeight);

                revalidate();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(canvasModel.getImage(), 0, 0, null);

        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(canvasModel.getWidth(), canvasModel.getHeight());
    }

    public void setCanvasModel(CanvasModel canvasModel) {
        this.canvasModel = canvasModel;
    }
}
