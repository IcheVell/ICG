package view.menu.edit;

import javax.swing.*;
import java.awt.*;

public class EditMenu extends JMenu {
    private final JMenuItem curveModelItem;
    private final JMenuItem resetAngleItem;

    public EditMenu() {
        setText("Edit");

        curveModelItem = new JMenuItem("Curve Model");
        resetAngleItem = new JMenuItem("Reset Angle");

        add(curveModelItem);
        add(resetAngleItem);
    }

    public JMenuItem getCurveModelItem() {
        return curveModelItem;
    }

    public JMenuItem getResetAngleItem() {
        return resetAngleItem;
    }
}
