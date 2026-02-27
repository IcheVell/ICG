package view.formspanel;

import view.formspanel.buttons.FormButton;
import view.formspanel.sliders.Slider;

import javax.swing.*;
import java.awt.*;

public class FormsPanel extends JPanel {
    private final JSpinner countVerticesSpinner;
    private final FormButton polygonButton;
    private final FormButton starButton;
    private final Slider radiusSlider;
    private final Slider rotationSlider;
    private final JSpinner radiusSpinner;
    private final JSpinner rotationSpinner;

    public FormsPanel() {
        ButtonGroup buttonGroup = new ButtonGroup();

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        buttonPanel.setBackground(Color.DARK_GRAY);

        polygonButton = new FormButton("Polygon", "Stamp type polygon");
        polygonButton.setSelected(true);

        starButton = new FormButton("Star", "Stamp type star");

        buttonGroup.add(polygonButton);
        buttonGroup.add(starButton);

        buttonPanel.add(polygonButton);
        buttonPanel.add(starButton);

        countVerticesSpinner = new JSpinner(new SpinnerNumberModel(3, 3, 16, 1));

        radiusSlider = new Slider(1, 100, 10, 5, 10, "Select radius", Color.DARK_GRAY, SwingConstants.HORIZONTAL);
        radiusSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));

        rotationSlider = new Slider(0, 180, 0, 15, 30, "Select rotation",  Color.DARK_GRAY, SwingConstants.HORIZONTAL);
        rotationSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 180, 1));

        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        setBackground(Color.DARK_GRAY);

        add(buttonPanel);
        add(countVerticesSpinner);
        add(radiusSlider);
        add(radiusSpinner);
        add(rotationSlider);
        add(rotationSpinner);
    }

    public JSpinner getCountVerticesSpinner() {
        return countVerticesSpinner;
    }

    public FormButton getPolygonButton() {
        return polygonButton;
    }

    public FormButton getStarButton() {
        return starButton;
    }

    public Slider getRadiusSlider() {
        return radiusSlider;
    }

    public Slider getRotationSlider() {
        return rotationSlider;
    }

    public JSpinner getRadiusSpinner() {
        return radiusSpinner;
    }

    public JSpinner getRotationSpinner() {
        return rotationSpinner;
    }
}
