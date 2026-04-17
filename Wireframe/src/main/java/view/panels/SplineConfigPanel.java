package view.panels;

import models.CurveModel;

import javax.swing.*;
import java.awt.*;

public class SplineConfigPanel extends JPanel {
    private final JButton okButton;
    private final JButton cancelButton;
    private final JButton applyButton;

    private final JSpinner spinnerN;
    private final JSpinner spinnerK;
    private final JSpinner spinnerM;
    private final JSpinner spinnerM1;

    public SplineConfigPanel(CurveModel curveModel) {
        setLayout(new GridLayout(3, 4, 5, 0));
        setBackground(Color.LIGHT_GRAY);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        applyButton = new JButton("Apply");

        spinnerK = new JSpinner(new SpinnerNumberModel(curveModel.getK(), 0, null, 1));
        spinnerK.setEnabled(false);

        spinnerN = new JSpinner(new SpinnerNumberModel(curveModel.getN(), 1, null, 1));

        spinnerM = new JSpinner(new SpinnerNumberModel(curveModel.getM(), 2, null, 1));
        spinnerM1 = new JSpinner(new SpinnerNumberModel(curveModel.getM1(), 1, null, 1));

        add(new JLabel("N: "));
        add(spinnerN);

        add(new JLabel("K: "));
        add(spinnerK);

        add(new JLabel("M1: "));
        add(spinnerM1);

        add(new JLabel("M: "));
        add(spinnerM);

        add(okButton);

        add(cancelButton);
        add(applyButton);
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getApplyButton() {
        return applyButton;
    }

    public JSpinner getSpinnerN() {
        return spinnerN;
    }

    public JSpinner getSpinnerK() {
        return spinnerK;
    }

    public JSpinner getSpinnerM() {
        return spinnerM;
    }

    public JSpinner getSpinnerM1() {
        return spinnerM1;
    }
}
