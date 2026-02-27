package controllers;

import enums.Form;
import models.CanvasModel;
import view.drawpanel.DrawPanel;
import view.formspanel.FormsPanel;
import view.formspanel.buttons.FormButton;
import view.formspanel.sliders.Slider;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FormsController {
    private final CanvasModel canvasModel;
    private final DrawPanel drawPanel;
    private final FormsPanel formsPanel;

    public FormsController(CanvasModel canvasModel, DrawPanel drawPanel, FormsPanel formsPanel) {
        this.canvasModel = canvasModel;
        this.drawPanel = drawPanel;
        this.formsPanel = formsPanel;

        JSpinner countVerticesSpinner = formsPanel.getCountVerticesSpinner();
        countVerticesSpinner.addChangeListener(e -> {
            try {
                int selectedCount = Integer.parseInt(countVerticesSpinner.getValue().toString());
                if (canvasModel.getForm() == Form.STAR && selectedCount < 5) {
                    JOptionPane.showMessageDialog(formsPanel, "Invalid vertices count: must be 5 or more",  "Warning", JOptionPane.WARNING_MESSAGE);
                    countVerticesSpinner.setValue(canvasModel.getCountVertices());
                    return;
                }

                canvasModel.setCountVertices(selectedCount);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(formsPanel, "Invalid vertices count", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        FormButton polygonButton = formsPanel.getPolygonButton();
        polygonButton.addActionListener(e -> {
            canvasModel.setForm(Form.POLYGON);
            drawPanel.repaint();
        });

        FormButton starButton = formsPanel.getStarButton();
        starButton.addActionListener(e -> {
            if (canvasModel.getCountVertices() < 5) {
                canvasModel.setCountVertices(5);
                JOptionPane.showMessageDialog(formsPanel, "Minimum number of vertices for this form is " + canvasModel.getCountVertices(), "Warning", JOptionPane.WARNING_MESSAGE);
                countVerticesSpinner.setValue(5);
            }
            canvasModel.setForm(Form.STAR);
            drawPanel.repaint();
        });

        Slider radiusSlider = formsPanel.getRadiusSlider();
        radiusSlider.addChangeListener(e -> {
            canvasModel.setRadius(radiusSlider.getValue());
            formsPanel.getRadiusSpinner().setValue(canvasModel.getRadius());
        });

        Slider rotationSlider = formsPanel.getRotationSlider();
        rotationSlider.addChangeListener(e -> {
            canvasModel.setRotation(rotationSlider.getValue());
            formsPanel.getRotationSpinner().setValue(canvasModel.getRotation());
        });

        JSpinner radiusSpinner = formsPanel.getRadiusSpinner();
        radiusSpinner.addChangeListener(e -> {
            canvasModel.setRadius(Integer.parseInt(radiusSpinner.getValue().toString()));
            formsPanel.getRadiusSlider().setValue(canvasModel.getRadius());
        });

        JSpinner rotationSpinner = formsPanel.getRotationSpinner();
        rotationSpinner.addChangeListener(e -> {
            canvasModel.setRotation(Integer.parseInt(rotationSpinner.getValue().toString()));
            formsPanel.getRotationSlider().setValue(canvasModel.getRotation());
        });
    }
}
