package controllers;

import controllers.actions.ActionFactory;
import models.CurveModel;
import view.Frame;
import view.dialogs.ConfigureDialog;

public class MainController {
    private final Frame frame;
    private final ConfigureDialog configureDialog;
    private final ActionFactory actionFactory;
    private final CurveModel curveModel;

    public MainController() {
        curveModel = new CurveModel();
        actionFactory = new ActionFactory();

        frame = new Frame(curveModel);
        configureDialog = new ConfigureDialog(frame, curveModel);

        new MenuController(frame, actionFactory, curveModel, configureDialog);
        new SplineController(curveModel, configureDialog);
        new FigureController(curveModel, frame);

        frame.setVisible(true);
    }
}
