package controllers;

import com.google.gson.Gson;
import controllers.actions.ActionFactory;
import dto.ModelData;
import enums.Command;
import models.CurveModel;
import view.Frame;
import view.dialogs.ConfigureDialog;
import view.menu.MyMenuBar;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;

public class MenuController {
    private final Frame frame;
    private final ActionFactory actionFactory;
    private final CurveModel curveModel;

    private final ConfigureDialog curveModelDialog;

    public MenuController(Frame frame, ActionFactory actionFactory,  CurveModel curveModel, ConfigureDialog curveModelDialog) {
        this.frame = frame;
        this.actionFactory = actionFactory;
        this.curveModel = curveModel;
        this.curveModelDialog = curveModelDialog;

        initActions();
        bindActions();

        addActionListenersCurveModelConfig();
    }

    private void addActionListenersCurveModelConfig() {
        JButton okButton = curveModelDialog.getSplineConfigPanel().getOkButton();
        okButton.addActionListener(e -> {
            if (curveModel.getControlPoints().size() <= 3) {
                JOptionPane.showMessageDialog(curveModelDialog, "NOT ENOUGH CONTROL POINTS", "INVALID K", JOptionPane.ERROR_MESSAGE);
                return;
            }

            curveModel.calculate3DPoints();
            curveModel.normalize3DPoints();
            curveModel.calculateM1CirclePoints();
            curveModel.rotate3DPoints();
            curveModel.calculateFinalPoints();
            curveModel.convertProjectPointsToScreen(frame.getMainPanel().getWidth(), frame.getMainPanel().getHeight());

            frame.getMainPanel().repaint();
            curveModelDialog.setVisible(false);
        });

        JButton cancelButton = curveModelDialog.getSplineConfigPanel().getCancelButton();
        cancelButton.addActionListener(e -> {
            curveModelDialog.setVisible(false);
        });

        JButton applyButton = curveModelDialog.getSplineConfigPanel().getApplyButton();
        applyButton.addActionListener(e -> {
            if (curveModel.getControlPoints().size() <= 3) {
                JOptionPane.showMessageDialog(curveModelDialog, "NOT ENOUGH CONTROL POINTS", "INVALID K", JOptionPane.ERROR_MESSAGE);
                return;
            }

            curveModel.calculate3DPoints();
            curveModel.normalize3DPoints();
            curveModel.calculateM1CirclePoints();
            curveModel.rotate3DPoints();
            curveModel.calculateFinalPoints();
            curveModel.convertProjectPointsToScreen(frame.getMainPanel().getWidth(), frame.getMainPanel().getHeight());

            frame.getMainPanel().repaint();
        });

        JSpinner spinnerN = curveModelDialog.getSplineConfigPanel().getSpinnerN();
        spinnerN.addChangeListener(e -> {
            curveModel.setN(Integer.parseInt(spinnerN.getValue().toString()));
            curveModel.calculateSplinePoints();

            curveModelDialog.repaint();
        });

        JSpinner spinnerM = curveModelDialog.getSplineConfigPanel().getSpinnerM();
        spinnerM.addChangeListener(e -> {
           curveModel.setM(Integer.parseInt(spinnerM.getValue().toString()));
        });

        JSpinner spinnerM1 = curveModelDialog.getSplineConfigPanel().getSpinnerM1();
        spinnerM1.addChangeListener(e -> {
            curveModel.setM1(Integer.parseInt(spinnerM1.getValue().toString()));
        });
    }

    private void initActions() {
        actionFactory.addAction(Command.CURVE_MODEL, ActionFactory.action("Curve model", this::onCurveModel));
        actionFactory.addAction(Command.RESET_ANGLE, ActionFactory.action("Reset angle", this::onResetAngle));

        actionFactory.addAction(Command.SAVE, ActionFactory.action("Save", this::onSave));
        actionFactory.addAction(Command.OPEN, ActionFactory.action("Open", this::onOpen));

        actionFactory.addAction(Command.ABOUT, ActionFactory.action("About", this::onAbout));
    }

    private void bindActions() {
        frame.getMyMenuBar().getEditMenu().getCurveModelItem().setAction(actionFactory.getAction(Command.CURVE_MODEL));
        frame.getMyMenuBar().getEditMenu().getResetAngleItem().setAction(actionFactory.getAction(Command.RESET_ANGLE));

        frame.getMyMenuBar().getFileMenu().getSaveItem().setAction(actionFactory.getAction(Command.SAVE));
        frame.getMyMenuBar().getFileMenu().getOpenItem().setAction(actionFactory.getAction(Command.OPEN));

        frame.getMyMenuBar().getHelpMenu().getAboutItem().setAction(actionFactory.getAction(Command.ABOUT));
    }

    private void onCurveModel() {
        curveModelDialog.setVisible(true);
    }

    private void onOpen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("JSON files (*.json)", "json"));
        int res = chooser.showOpenDialog(frame);

        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".json")) {
                JOptionPane.showMessageDialog(frame, "Please select a JSON file", "INVALID JSON", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Reader reader = new FileReader(file)) {
                Gson gson = new Gson();
                ModelData modelData = gson.fromJson(reader, ModelData.class);

                curveModel.setScale(modelData.scale());
                curveModel.setZN(modelData.ZN());
                curveModel.setM1(modelData.M1());
                curveModel.setM(modelData.M());
                curveModel.setN(modelData.N());
                curveModel.setK(modelData.K());
                curveModel.setModelRadiansAngleX(modelData.rotateX());
                curveModel.setModelRadiansAngleY(modelData.rotateY());

                curveModel.getControlPoints().clear();
                for (Point point : modelData.controlPoints()) {
                    curveModel.getControlPoints().add(point);
                }

                curveModelDialog.getSplineConfigPanel().getSpinnerK().setValue(modelData.K());
                curveModelDialog.getSplineConfigPanel().getSpinnerM().setValue(modelData.M());
                curveModelDialog.getSplineConfigPanel().getSpinnerM1().setValue(modelData.M1());
                curveModelDialog.getSplineConfigPanel().getSpinnerN().setValue(modelData.N());

                curveModel.calculateSplinePoints();
                curveModel.calculate3DPoints();
                curveModel.normalize3DPoints();
                curveModel.calculateM1CirclePoints();
                curveModel.rotate3DPoints();
                curveModel.calculateFinalPoints();
                curveModel.convertProjectPointsToScreen(frame.getMainPanel().getWidth(), frame.getMainPanel().getHeight());

                frame.getMainPanel().repaint();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Invalid JSON file", "INVALID JSON", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
            }
        }
    }

    private void onSave() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("JSON files (*.json)", "json"));
        int res = chooser.showSaveDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            ModelData data = new ModelData(
                    curveModel.getScale(), curveModel.getZN(),
                    curveModel.getN(), curveModel.getK(),
                    curveModel.getM(), curveModel.getM1(),
                    curveModel.getModelRadiansAngleX(), curveModel.getModelRadiansAngleY(),
                    curveModel.getControlPoints()
            );

            Gson jsonConverter = new Gson();

            String json = jsonConverter.toJson(data);

            File file = chooser.getSelectedFile();
            if (!file.getName().endsWith(".json")) {
                file = new File(file.getAbsolutePath() + ".json");
            }

            try {
                Files.writeString(file.toPath(), json);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onResetAngle() {
        curveModel.setModelRadiansAngleX(0);
        curveModel.setModelRadiansAngleY(0);

        curveModel.rotate3DPoints();
        curveModel.calculateFinalPoints();
        curveModel.convertProjectPointsToScreen(frame.getMainPanel().getWidth(), frame.getMainPanel().getHeight());

        frame.getMainPanel().repaint();
    }

    private void onAbout() {
        JOptionPane.showMessageDialog(frame, """
                ICGWireframe - приложение для построения и визуализации проволочной модели тела вращения по образующей, 
                заданной опорными точками и B-сплайном. Программа позволяет редактировать опорные точки, изменять параметры модели, 
                вращать фигуру, выполнять перспективную проекцию, масштабирование и отображать глубину цветом.
                
                Разработчик: Козин Кирилл NSU 23211
                """, "About program", JOptionPane.INFORMATION_MESSAGE);
    }
}
