package view;

import model.Camera;
import model.RenderModel;
import model.SceneModel;
import view.menu.MyMenuBar;
import view.panels.MainPanel;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private final MyMenuBar menuBar;
    private final MainPanel mainPanel;

    public Frame(SceneModel sceneModel, RenderModel renderModel, Camera camera) {
        menuBar = new MyMenuBar();
        mainPanel = new MainPanel(sceneModel, renderModel, camera);

        setTitle("Raytracing app");
        setMinimumSize(new Dimension(640, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);
        setJMenuBar(menuBar);

        add(mainPanel, BorderLayout.CENTER);
    }

    public MyMenuBar getMyMenuBar() {
        return menuBar;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
} // зернистость + сцены посложнее
