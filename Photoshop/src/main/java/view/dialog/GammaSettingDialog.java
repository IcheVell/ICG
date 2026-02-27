package view.dialog;

import model.FilterModel;

import javax.swing.*;
import java.awt.*;

public class GammaSettingDialog extends JDialog {
    private final FilterModel filterModel;
    private final JSpinner spinner;
    private final JButton okButton;
    private final JButton cancelButton;

    public GammaSettingDialog(FilterModel filterModel, JFrame parentFrame) {
        super(parentFrame, "Gamma Settings", true);

        this.filterModel = filterModel;

        setLayout(new BorderLayout());
        setSize(300, 300);

        okButton = new JButton("Ok");
        okButton.setFocusable(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);

        spinner = new JSpinner(new SpinnerNumberModel(1, 0.1, 10, 1));

        JPanel centerPanel = new JPanel();
        centerPanel.add(Box.createVerticalStrut(100));
        centerPanel.add(new JLabel("Gamma Settings"));
        centerPanel.add(spinner);

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
}
