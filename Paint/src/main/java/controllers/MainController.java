package controllers;

import models.CanvasModel;
import view.Frame;

public class MainController {
    private final Frame frame;
    private final CanvasModel canvasModel;

    public MainController() {
        canvasModel = new CanvasModel(1280, 480);
        frame = new Frame();

        new DrawController(canvasModel, frame.getDrawPanel());
        new MyMenuBarController(canvasModel, frame.getMyMenuBar(), frame.getDrawPanel(), frame.getToolPanel());
        new ToolController(canvasModel, frame.getMyMenuBar(), frame.getToolPanel(), frame.getDrawPanel());
        new FormsController(canvasModel, frame.getDrawPanel(), frame.getFormsPanel());
        new ColorController(canvasModel, frame.getColorPanel());

        frame.pack();
        frame.setVisible(true);
    }

}
