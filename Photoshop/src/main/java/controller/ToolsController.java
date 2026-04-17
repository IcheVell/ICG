package controller;

import controller.actions.ActionFactory;
import enums.Command;
import model.FilterModel;
import view.imagepanel.ImagePanel;
import view.menu.MyMenuBar;
import view.toolspanel.ToolPanel;

import javax.swing.*;

public class ToolsController {
    private final FilterModel filterModel;
    private final ActionFactory actionFactory;
    private final ToolPanel toolsPanel;

    public ToolsController(FilterModel filterModel, ActionFactory actionFactory, ToolPanel toolsPanel) {
        this.filterModel = filterModel;
        this.actionFactory = actionFactory;
        this.toolsPanel = toolsPanel;

        JButton filtersButton = toolsPanel.getFiltersButton();
        filtersButton.addActionListener(e -> {toolsPanel.getFilterPopupMenu().show(filtersButton, 0, filtersButton.getHeight());});

        JButton modifyButton = toolsPanel.getModifyButton();
        modifyButton.addActionListener(e -> {toolsPanel.getModifyPopupMenu().show(modifyButton, 0, modifyButton.getHeight());});

        bindActions();
    }

    private void bindActions() {
        toolsPanel.getRotateModifyItem().setAction(actionFactory.getAction(Command.ROTATE));
        toolsPanel.getResizeModifyItem().setAction(actionFactory.getAction(Command.RESIZE));

        toolsPanel.getGrayscaleFilterItem().setAction(actionFactory.getAction(Command.GRAYSCALE));
        toolsPanel.getInvertFilterItem().setAction(actionFactory.getAction(Command.INVERT));
        toolsPanel.getSmoothingFilterItem().setAction(actionFactory.getAction(Command.SMOOTHING));
        toolsPanel.getSharpenFilterItem().setAction(actionFactory.getAction(Command.SHARPEN));
        toolsPanel.getEmbossFilterItem().setAction(actionFactory.getAction(Command.EMBOSS));
        toolsPanel.getGammaFilterItem().setAction(actionFactory.getAction(Command.GAMMA));
        toolsPanel.getRobertsEdgeFilterItem().setAction(actionFactory.getAction(Command.ROBERTS));
        toolsPanel.getSobelsEdgeFilterItem().setAction(actionFactory.getAction(Command.SOBEL));
        toolsPanel.getDitheringFilterItem().setAction(actionFactory.getAction(Command.DITHERING));
        toolsPanel.getOrderedDitheringFilterItem().setAction(actionFactory.getAction(Command.ORDERED_DITHERING));
        toolsPanel.getWaterColorFilterItem().setAction(actionFactory.getAction(Command.WATERCOLOR));
        toolsPanel.getOilPaintingFilterItem().setAction(actionFactory.getAction(Command.OIL_PAINTING));
    }



}
