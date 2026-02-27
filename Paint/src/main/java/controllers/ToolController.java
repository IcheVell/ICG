package controllers;

import enums.Mode;
import models.CanvasModel;
import view.drawpanel.DrawPanel;
import view.menu.MyMenuBar;
import view.toolpanel.ToolPanel;
import view.toolpanel.buttons.ClearButton;
import view.toolpanel.buttons.ToolButton;
import view.toolpanel.comboboxes.ThicknessComboBox;

public class ToolController {
    private final CanvasModel canvasModel;
    private final MyMenuBar menuBar;
    private final ToolPanel toolPanel;
    private final DrawPanel drawPanel;

    public ToolController(CanvasModel canvasModel, MyMenuBar menuBar, ToolPanel toolPanel, DrawPanel drawPanel) {
        this.canvasModel = canvasModel;
        this.menuBar = menuBar;
        this.toolPanel = toolPanel;
        this.drawPanel = drawPanel;

        ToolButton lineButton = toolPanel.getLineButton();
        lineButton.addActionListener(e -> {if (lineButton.isSelected()) {
            canvasModel.setMode(Mode.LINE);
            menuBar.getToolsMenu().getLineToolItem().setSelected(true);
        }});

        ToolButton fillButton = toolPanel.getFillButton();
        fillButton.addActionListener(e -> {if (fillButton.isSelected()) {
            canvasModel.setMode(Mode.FILL);
            menuBar.getToolsMenu().getFillToolItem().setSelected(true);
        }});

        ToolButton stampButton = toolPanel.getStampButton();
        stampButton.addActionListener(e -> {if (stampButton.isSelected()) {
            canvasModel.setMode(Mode.STAMP);
            menuBar.getToolsMenu().getStampToolItem().setSelected(true);
        }});

        ClearButton clearButton = toolPanel.getClearButton();
        clearButton.addActionListener(e -> {
            canvasModel.clearCanvasModel();
            drawPanel.repaint();
        });

        ThicknessComboBox thicknessComboBox = toolPanel.getThicknessComboBox();
        thicknessComboBox.addActionListener(e -> {canvasModel.setThickness(thicknessComboBox.getSelectedIndex() + 1);});
    }
}
