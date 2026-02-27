package view.toolpanel.comboboxes;

import javax.swing.*;

public class ThicknessComboBox extends JComboBox<String> {
    public ThicknessComboBox() {
        String[] thicknesses = {"1px", "2px", "3px", "4px", "5px", "6px", "7px", "8px", "9px", "10px"};

        setToolTipText("Select thickness");
        for (String thick : thicknesses) {
            addItem(thick);
        }
    }
}
