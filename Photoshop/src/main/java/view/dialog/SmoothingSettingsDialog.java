package view.dialog;

import model.FilterModel;

import javax.swing.*;
import java.awt.*;

public class SmoothingSettingsDialog extends JDialog {
    private final FilterModel filterModel;
    private final JSpinner spinner;
    private final JSlider slider;
    private final JButton okButton;
    private final JButton cancelButton;

    public SmoothingSettingsDialog(FilterModel filterModel, JFrame parentFrame, int min, int max, int step, int tickSpace, String name) {
        super(parentFrame, name, true);

        this.filterModel = filterModel;

        setLayout(new BorderLayout());
        setSize(300, 300);

        okButton = new JButton("Ok");
        okButton.setFocusable(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);

        spinner = new JSpinner(new SpinnerNumberModel(filterModel.getSmoothingKernelSize(), min, max, step));
        slider = new JSlider(min, max, filterModel.getSmoothingKernelSize());
        slider.setMajorTickSpacing(tickSpace);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        JPanel centerPanel = new JPanel();
        centerPanel.add(Box.createVerticalStrut(100));
        centerPanel.add(new JLabel(name));
        centerPanel.add(spinner);
        centerPanel.add(slider);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JSpinner getSpinner() {
        return spinner;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JSlider getSlider() {
        return slider;
    }
}
