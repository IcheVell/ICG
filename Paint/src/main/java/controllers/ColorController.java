package controllers;

import models.CanvasModel;
import view.colorpanel.ColorPanel;
import view.colorpanel.buttons.ColorButton;

import javax.swing.*;
import java.awt.*;

public class ColorController {
    private final CanvasModel canvasModel;
    private final ColorPanel colorPanel;

    public ColorController(CanvasModel canvasModel, ColorPanel colorPanel) {
        this.canvasModel = canvasModel;
        this.colorPanel = colorPanel;

        ColorButton[] colorButtons = colorPanel.getColorButtons();
        for (ColorButton colorButton : colorButtons) {
            colorButton.addActionListener(e -> {
                if (colorButton.isSelected()) {
                    canvasModel.setColor(colorButton.getBackground());
                    colorPanel.setColorOtherColorButton(Color.LIGHT_GRAY);
                }
            });
        }

        JButton otherColorButton = colorPanel.getOtherColorButton();
        otherColorButton.addActionListener(e -> {
            Color selected = JColorChooser.showDialog(colorPanel, "Choose a color", null);

            if (selected != null) {
                canvasModel.setColor(selected);
                colorPanel.getColorGroup().clearSelection();
                colorPanel.setColorOtherColorButton(selected);
                colorPanel.repaint();
            }
        });


    }
}
