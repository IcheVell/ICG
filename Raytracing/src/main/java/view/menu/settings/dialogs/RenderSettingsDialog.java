package view.menu.settings.dialogs;

import model.RenderModel;

import javax.swing.*;
import java.awt.*;

public class RenderSettingsDialog extends JDialog {
    private final RenderModel renderModel;

    private final JSpinner gammaSpinner;
    private final JSpinner depthSpinner;

    private Color selectedBackgroundColor;
    private final JButton colorButton;

    private boolean applied = false;

    public RenderSettingsDialog(JFrame owner, RenderModel renderModel) {
        super(owner, "Render settings", true);

        this.renderModel = renderModel;

        selectedBackgroundColor = new Color(renderModel.getBackgroundR(), renderModel.getBackgroundG(), renderModel.getBackgroundB());

        gammaSpinner = new JSpinner(new SpinnerNumberModel(renderModel.getGamma(), 0.0, 10.0, 0.1));

        depthSpinner = new JSpinner(new SpinnerNumberModel((int) renderModel.getDepth(), 1, 10, 1));

        colorButton = new JButton("Choose background color");
        colorButton.setBackground(selectedBackgroundColor);
        colorButton.setOpaque(true);

        initLayout();
        initListeners();

        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void initLayout() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Background:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(colorButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Gamma:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(gammaSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Depth:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(depthSpinner, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> onOk());
        cancelButton.addActionListener(e -> onCancel());

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void initListeners() {
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this,"Choose background color", selectedBackgroundColor);

            if (newColor != null) {
                selectedBackgroundColor = newColor;
                colorButton.setBackground(selectedBackgroundColor);
            }
        });
    }

    private void onOk() {
        double gamma = ((Number) gammaSpinner.getValue()).doubleValue();
        int depth = ((Number) depthSpinner.getValue()).intValue();

        if (gamma < 0.0 || gamma > 10.0) {
            JOptionPane.showMessageDialog(this,"Gamma must be in range 0..10", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (depth < 1 || depth > 10) {
            JOptionPane.showMessageDialog(this,"Depth must be in range 1..10","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        renderModel.setBackgroundR(selectedBackgroundColor.getRed());
        renderModel.setBackgroundG(selectedBackgroundColor.getGreen());
        renderModel.setBackgroundB(selectedBackgroundColor.getBlue());

        renderModel.setGamma(gamma);
        renderModel.setDepth(depth);

        applied = true;
        dispose();
    }

    private void onCancel() {
        applied = false;
        dispose();
    }

    public boolean isApplied() {
        return applied;
    }
}