package view.colorpanel;

import view.colorpanel.buttons.ColorButton;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel {
    private ColorButton[] colorButtons;
    private final ButtonGroup colorGroup;
    private JButton otherColorButton;
    private final Color[] panelColors = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};

    public ColorPanel() {
        colorGroup = new ButtonGroup();

        setPanelColors(colorGroup);
        setOtherColorButton();

        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        setBackground(Color.DARK_GRAY);
    }

    private void setPanelColors(ButtonGroup colorGroup) {
        JPanel colorPanel = new JPanel(new GridLayout(2, 2, 2, 2));
        colorPanel.setBackground(Color.DARK_GRAY);

        colorButtons = new ColorButton[4];
        int idx = 0;

        for (Color c : panelColors) {
            ColorButton colorButton = new ColorButton(c);

            if (c == Color.BLACK) {
                colorButton.setSelected(true);
            }

            colorButton.setPreferredSize(new Dimension(60, 30));

            colorGroup.add(colorButton);
            colorPanel.add(colorButton);
            colorButtons[idx++] = colorButton;
        }

        add(colorPanel);
    }

    private void setOtherColorButton() {
        otherColorButton = new JButton("Other");
        otherColorButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        otherColorButton.setToolTipText("Other colors");
        otherColorButton.setBackground(Color.LIGHT_GRAY);
        otherColorButton.setFocusable(false);

        add(otherColorButton);
    }

    public ColorButton[] getColorButtons() {
        return colorButtons;
    }

    public JButton getOtherColorButton() {
        return otherColorButton;
    }

    public void setColorOtherColorButton(Color color) {
        otherColorButton.setBackground(color);
    }

    public ButtonGroup getColorGroup() {
        return colorGroup;
    }
}
