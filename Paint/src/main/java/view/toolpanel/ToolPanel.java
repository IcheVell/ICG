package view.toolpanel;

import models.CanvasModel;
import view.drawpanel.DrawPanel;
import view.toolpanel.buttons.ClearButton;
import view.toolpanel.comboboxes.ThicknessComboBox;
import view.toolpanel.buttons.ToolButton;

import javax.swing.*;
import java.awt.*;

public class ToolPanel extends JPanel {
    private ToolButton lineButton;
    private ToolButton stampButton;
    private ToolButton fillButton;
    private ClearButton clearButton;
    private ThicknessComboBox thicknessComboBox;

    public ToolPanel() {
        lineButton = new ToolButton("Line", "Press and drag mouse");
        lineButton.setSelected(true);
        stampButton = new ToolButton("Stamp", "Select radius, rotation and press left mouse button");
        fillButton = new ToolButton("Fill", "Select color and press left mouse button");
        clearButton = new ClearButton("Press to clear canvas");

        ButtonGroup toolGroup = new ButtonGroup();
        toolGroup.add(lineButton);
        toolGroup.add(stampButton);
        toolGroup.add(fillButton);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 2, 2));

        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(lineButton);
        buttonPanel.add(stampButton);
        buttonPanel.add(fillButton);
        buttonPanel.add(clearButton);

        thicknessComboBox = new ThicknessComboBox();

        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        setBackground(Color.DARK_GRAY);

        add(buttonPanel);
        add(thicknessComboBox);
    }

    public ToolButton getLineButton() {
        return lineButton;
    }

    public ToolButton getStampButton() {
        return stampButton;
    }

    public ToolButton getFillButton() {
        return fillButton;
    }

    public ClearButton getClearButton() {
        return clearButton;
    }

    public ThicknessComboBox getThicknessComboBox() {
        return thicknessComboBox;
    }
}
