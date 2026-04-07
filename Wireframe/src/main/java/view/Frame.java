package view;

import models.CurveModel;
import view.dialogs.ConfigureDialog;
import view.menu.MyMenuBar;
import view.panels.MainPanel;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private final MyMenuBar menuBar;
    private final MainPanel mainPanel;

    public Frame(CurveModel curveModel) {
        menuBar = new MyMenuBar();
        mainPanel = new MainPanel(curveModel);

        setLayout(new BorderLayout());
        setTitle("Wireframe app");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 480));
        setResizable(true);

        setJMenuBar(menuBar);
        add(mainPanel, BorderLayout.CENTER);
    }

    public MyMenuBar getMyMenuBar() {
        return menuBar;
    }

    public MainPanel getMainPanel() {return mainPanel;}
}
