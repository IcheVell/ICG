package view.colorpanel.buttons;

import javax.swing.*;
import java.awt.*;

public class ColorButton extends JToggleButton {
    public ColorButton(Color color) {
        setBackground(color);
        setFocusable(false);
        setFont(new Font("Times New Roman", Font.BOLD, 14));
    }
}
