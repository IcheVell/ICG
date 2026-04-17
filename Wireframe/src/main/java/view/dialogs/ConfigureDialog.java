package view.dialogs;

import models.CurveModel;
import view.panels.SplineConfigPanel;
import view.panels.SplinePanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ConfigureDialog extends JDialog {
    private final SplinePanel splinePanel;
    private final SplineConfigPanel splineConfigPanel;

    public ConfigureDialog(JFrame parentFrame, CurveModel curveModel) {
        super(parentFrame, "B-Spline configure", true);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(640, 480));
        setResizable(false);

        splinePanel = new SplinePanel(curveModel);
        splineConfigPanel = new SplineConfigPanel(curveModel);

        add(splinePanel, BorderLayout.CENTER);
        add(splineConfigPanel, BorderLayout.SOUTH);
    }

    public SplinePanel getSplinePanel() {
        return splinePanel;
    }

    public SplineConfigPanel getSplineConfigPanel() {
        return splineConfigPanel;
    }
}
