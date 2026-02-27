package view.dialog;

import model.FilterModel;

import javax.swing.*;
import java.awt.*;

public class ResizeSettingsDialog extends JDialog {
    private final FilterModel filterModel;
    private final JComboBox<String> interpolationBox;
    private final JComboBox<String> resizeModeBox;
    private final JButton okButton;
    private final JButton cancelButton;

    public ResizeSettingsDialog(JFrame frame, FilterModel filterModel) {
        super(frame, "Resize Settings", true);

        this.filterModel = filterModel;

        setLayout(new BorderLayout());
        setSize(300, 300);

        okButton = new JButton("Ok");
        okButton.setFocusable(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);

        interpolationBox = new JComboBox<>();
        interpolationBox.addItem("NEAREST_NEIGHBOUR");
        interpolationBox.addItem("BILINEAR");
        interpolationBox.addItem("BICUBIC");
        interpolationBox.setSelectedIndex(1);

        resizeModeBox = new JComboBox<>();
        resizeModeBox.addItem("DEFAULT");
        resizeModeBox.addItem("FULLSCREEN");
        resizeModeBox.setSelectedIndex(0);

        JPanel centerPanel = new JPanel();
        centerPanel.add(Box.createVerticalStrut(100));
        centerPanel.add(new JLabel("Resize Settings"));
        centerPanel.add(interpolationBox);
        centerPanel.add(resizeModeBox);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JComboBox<String> getInterpolationBox() {
        return interpolationBox;
    }

    public JComboBox<String> getResizeModeBox() {
        return resizeModeBox;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
