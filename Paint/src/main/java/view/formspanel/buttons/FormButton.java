package view.formspanel.buttons;

import enums.Form;

import javax.swing.*;
import java.awt.*;

public class FormButton extends JToggleButton {

    public FormButton(String formName, String tip) {
        setToolTipText(tip);
        setText(formName);
        setFont(new Font("Times New Roman", Font.BOLD, 14));
        setBackground(Color.LIGHT_GRAY);
        setFocusPainted(false);
    }
}
