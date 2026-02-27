package view.dialog;

import model.FilterModel;

import javax.swing.*;
import java.awt.*;

public class DitheringSettingsDialog extends JDialog {
    private final FilterModel filterModel;
    private final JSpinner spinnerR;
    private final JSlider sliderR;
    private final JSpinner spinnerG;
    private final JSlider sliderG;
    private final JSpinner spinnerB;
    private final JSlider sliderB;
    private final JButton okButton;
    private final JButton cancelButton;

    public DitheringSettingsDialog(FilterModel filterModel, Frame frame, String title) {
        super(frame, title, true);

        this.filterModel = filterModel;

        setLayout(new BorderLayout());
        setSize(300, 300);

        okButton = new JButton("Ok");
        okButton.setFocusable(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);

        spinnerR = new JSpinner(new SpinnerNumberModel(filterModel.getQuantsR(), 2, 128, 1));
        sliderR = new JSlider(2, 128, filterModel.getQuantsR());
        sliderR.setMajorTickSpacing(16);
        sliderR.setPaintTicks(true);
        sliderR.setPaintLabels(true);

        spinnerG = new JSpinner(new SpinnerNumberModel(filterModel.getQuantsG(), 2, 128, 1));
        sliderG = new JSlider(2, 128, filterModel.getQuantsG());
        sliderG.setMajorTickSpacing(16);
        sliderG.setPaintTicks(true);
        sliderG.setPaintLabels(true);

        spinnerB = new JSpinner(new SpinnerNumberModel(filterModel.getQuantsB(), 2, 128, 1));
        sliderB = new JSlider(2, 128, filterModel.getQuantsB());
        sliderB.setMajorTickSpacing(16);
        sliderB.setPaintTicks(true);
        sliderB.setPaintLabels(true);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 5, 5));;
        centerPanel.add(spinnerR);
        centerPanel.add(sliderR);
        centerPanel.add(spinnerG);
        centerPanel.add(sliderG);
        centerPanel.add(spinnerB);
        centerPanel.add(sliderB);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
    }

    public JButton getOkButton() {
        return okButton;
    }
    public JButton getCancelButton() {
        return cancelButton;
    }

    public JSpinner getSpinnerR() {
        return spinnerR;
    }

    public JSlider getSliderR() {
        return sliderR;
    }

    public JSpinner getSpinnerG() {
        return spinnerG;
    }

    public JSlider getSliderG() {
        return sliderG;
    }

    public JSpinner getSpinnerB() {
        return spinnerB;
    }

    public JSlider getSliderB() {
        return sliderB;
    }
}
