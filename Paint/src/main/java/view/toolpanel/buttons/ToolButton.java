package view.toolpanel.buttons;

import javax.swing.*;
import java.awt.*;

public class ToolButton extends JToggleButton {
    public ToolButton(String buttonName, String tip) {
        setToolTipText(tip);
        setText(buttonName);
        setFont(new Font("Times New Roman", Font.BOLD, 14));
        setFocusPainted(false);
        setBackground(Color.LIGHT_GRAY);
    }
}
