package controllers;

import enums.Mode;
import io.SceneFileParser;
import model.Camera;
import model.RenderModel;
import model.SceneModel;
import view.Frame;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class MainController {
    private final Frame frame;
    private final SceneModel sceneModel;
    private final RenderModel renderModel;
    private final Camera camera;

    public MainController() {
        sceneModel = new SceneModel();
        renderModel = new RenderModel();
        camera = new Camera();

        frame = new Frame(sceneModel, renderModel, camera);

        MenuController menuController = new MenuController(frame, sceneModel, renderModel, camera);
        new WireframeController(frame.getMainPanel(), camera, menuController::canControlCamera);

        frame.setVisible(true);
    }
}
