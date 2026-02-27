package controller;

import enums.Mode;
import model.FilterModel;
import view.imagepanel.ImagePanel;
import view.menu.MyMenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageController extends MouseAdapter {
    private final FilterModel filterModel;
    private final ImagePanel imagePanel;
    private final MyMenuBar menuBar;
    private Point dragStart;
    private Point dragEnd;

    public ImageController(FilterModel filterModel, ImagePanel imagePanel,  MyMenuBar menuBar) {
        this.filterModel = filterModel;
        this.imagePanel = imagePanel;
        this.menuBar = menuBar;

        imagePanel.setFilterModel(filterModel);

        imagePanel.addMouseListener(this);
        imagePanel.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        filterModel.setMode(Mode.ORIGINAL);
        menuBar.getViewMenu().getOriginalImageItem().setSelected(true);
        imagePanel.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            dragStart = e.getPoint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragStart = dragEnd = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragStart != null) {
            JScrollPane scrollPane = getScrollPane();

            if (scrollPane != null) {
                dragEnd = e.getPoint();

                int dx = dragStart.x - dragEnd.x;
                int dy = dragStart.y - dragEnd.y;

                scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() + dx);
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue() + dy);

                dragStart = dragEnd;
            }
        }
    }

    private JScrollPane getScrollPane() {
        return (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, imagePanel);
    }
}
