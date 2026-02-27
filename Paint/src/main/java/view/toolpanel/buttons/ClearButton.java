package view.toolpanel.buttons;

import javax.swing.*;
import java.awt.*;

public class ClearButton extends JButton {
    public ClearButton(String tip) {
        setText("Clear");
        setToolTipText(tip);
        setFont(new Font("Times New Roman", Font.BOLD, 14));
        setFocusPainted(false);
        setBackground(Color.LIGHT_GRAY);
    }
}
