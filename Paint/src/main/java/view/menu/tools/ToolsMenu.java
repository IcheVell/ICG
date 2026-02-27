package view.menu.tools;

import javax.swing.*;

public class ToolsMenu extends JMenu {
    private final JRadioButtonMenuItem lineToolItem;
    private final JRadioButtonMenuItem stampToolItem;
    private final JRadioButtonMenuItem fillToolItem;

    public ToolsMenu() {
        setText("Tools");

        ButtonGroup buttonGroup = new ButtonGroup();

        lineToolItem = new JRadioButtonMenuItem("Line");
        lineToolItem.setSelected(true);
        stampToolItem = new JRadioButtonMenuItem("Stamp");
        fillToolItem = new JRadioButtonMenuItem("Fill");

        buttonGroup.add(lineToolItem);
        buttonGroup.add(stampToolItem);
        buttonGroup.add(fillToolItem);

        add(lineToolItem);
        add(stampToolItem);
        add(fillToolItem);
    }

    public JMenuItem getLineToolItem() {
        return lineToolItem;
    }

    public JMenuItem getStampToolItem() {
        return stampToolItem;
    }

    public JMenuItem getFillToolItem() {
        return fillToolItem;
    }
}
