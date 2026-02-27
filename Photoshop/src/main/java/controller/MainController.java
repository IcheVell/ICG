package controller;

import controller.actions.ActionFactory;
import model.FilterModel;
import view.Frame;

public class MainController {
    private final ActionFactory actionFactory;

    public MainController() {
        Frame frame = new Frame();
        actionFactory = new ActionFactory();

        FilterModel filterModel = new FilterModel(640, 480);

        new MenuController(filterModel, frame, actionFactory);
        new ImageController(filterModel, frame.getImagePanel(), frame.getMyMenuBar());
        new ToolsController(filterModel, actionFactory, frame.getToolsPanel());

        frame.pack();
        frame.setVisible(true);
    }
}
