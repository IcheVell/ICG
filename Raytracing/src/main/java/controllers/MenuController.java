package controllers;

import enums.Mode;
import io.RenderFileParser;
import io.RenderFileSaver;
import io.SceneFileParser;
import model.Camera;
import model.Point3D;
import model.RenderModel;
import model.SceneModel;
import render.RayTracer;
import view.Frame;
import view.menu.MyMenuBar;
import view.menu.settings.dialogs.RenderSettingsDialog;
import view.panels.MainPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MenuController {
    private final Frame frame;
    private final SceneModel sceneModel;
    private final RenderModel renderModel;
    private final Camera camera;

    private Camera initialCamera;

    private Mode mode = Mode.VIEW;
    private boolean rendering = false;
    private SwingWorker<BufferedImage, Void> renderWorker;

    public MenuController(Frame frame, SceneModel sceneModel, RenderModel renderModel, Camera camera) {
        this.frame = frame;
        this.sceneModel = sceneModel;
        this.renderModel = renderModel;
        this.camera = camera;

        bindActions(frame.getMyMenuBar());
    }

    public void bindActions(MyMenuBar menuBar) {
        menuBar.getFileMenu().getOpenSceneItem().addActionListener(e -> onOpen());
        menuBar.getFileMenu().getSaveImageItem().addActionListener(e -> onSaveImage());

        menuBar.getSettingsMenu().getResetCameraItem().addActionListener(e -> onResetCamera());
        menuBar.getSettingsMenu().getLoadRenderSettingsItem().addActionListener(e -> onLoadRenderSettings());
        menuBar.getSettingsMenu().getSaveRenderSettingsItem().addActionListener(e -> onSaveRenderSettings());
        menuBar.getSettingsMenu().getRenderSettingsItem().addActionListener(e -> onRenderSettings());

        menuBar.getModeMenu().getViewModeItem().addActionListener(e -> onSelectView());
        menuBar.getModeMenu().getRenderModeItem().addActionListener(e -> onRender());

        menuBar.getHelpMenu().getAboutItem().addActionListener(e -> onAbout());
    }

    private void onOpen() {
        JFileChooser fileChooser = new JFileChooser("/home/home/NSU/ICG/Raytracing/src/main/resources");

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(".scene", "scene"));

        int result = fileChooser.showOpenDialog(frame);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File sceneFile = fileChooser.getSelectedFile();

        if (sceneFile == null) {
            return;
        }

        try {
            sceneModel.clear();
            renderModel.clear();

            initialCamera = null;

            SceneFileParser sceneFileParser = new SceneFileParser(sceneFile.getAbsolutePath(), sceneModel);
            sceneFileParser.parseSceneFileHeader();

            mode = Mode.VIEW;
            frame.getMyMenuBar().getModeMenu().getViewModeItem().setSelected(true);

            File renderFile = getRenderFileForScene(sceneFile);

            if (renderFile.exists() && renderFile.isFile()) {
                RenderFileParser renderFileParser = new RenderFileParser(renderFile.getAbsolutePath(), renderModel, camera);

                renderFileParser.parseRenderFile();
                camera.recalculate();
            } else {
                camera.setEye(new Point3D(-25, 0, 4));
                camera.setView(new Point3D(0, 0, 1));
                camera.setUp(new Point3D(0, 0, 1));

                camera.setZn(8);
                camera.setZf(60);

                camera.setSw(12);
                camera.setSh(9);

                camera.recalculate();
            }

            saveInitialCamera();

            mode = Mode.VIEW;
            rendering = false;
            frame.getMainPanel().clearRenderedImage();

            frame.getMainPanel().repaint();

        } catch (Exception e) {
            sceneModel.clear();
            renderModel.clear();
            initialCamera = null;

            JOptionPane.showMessageDialog(frame, "Can't open file:\n" + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLoadRenderSettings() {
        JFileChooser fileChooser = new JFileChooser("/home/home/NSU/ICG/Raytracing/src/main/resources");

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(".render", "render"));

        int result = fileChooser.showOpenDialog(frame);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();

        if (file == null) {
            return;
        }

        try {
            RenderFileParser renderFileParser = new RenderFileParser(
                    file.getAbsolutePath(),
                    renderModel,
                    camera
            );

            renderFileParser.parseRenderFile();
            camera.recalculate();
            saveInitialCamera();

            frame.getMainPanel().clearRenderedImage();
            frame.getMainPanel().setBackground(new Color(renderModel.getBackgroundR(), renderModel.getBackgroundG(), renderModel.getBackgroundB()));
            frame.getMainPanel().repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,"Can't open render settings:\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveInitialCamera() {
        if (camera.getEye() == null || camera.getView() == null || camera.getUp() == null) {
            return;
        }

        camera.recalculate();
        initialCamera = camera.copy();
    }

    private void onResetCamera() {
        if (initialCamera != null) {
            camera.copyFrom(initialCamera);
        }

        camera.recalculate();
        frame.getMainPanel().repaint();
    }

    private File getRenderFileForScene(File sceneFile) {
        String sceneName = sceneFile.getName();

        int dotIndex = sceneName.lastIndexOf('.');

        String baseName;
        if (dotIndex == -1) {
            baseName = sceneName;
        } else {
            baseName = sceneName.substring(0, dotIndex);
        }

        return new File(sceneFile.getParentFile(), baseName + ".render");
    }

    private void onSaveRenderSettings() {
        JFileChooser fileChooser = new JFileChooser("/home/home/NSU/ICG/Raytracing/src/main/resources");

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(".render", "render"));

        int result = fileChooser.showSaveDialog(frame);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();

        if (file == null) {
            return;
        }

        if (!file.getName().toLowerCase().endsWith(".render")) {
            file = new File(file.getParentFile(), file.getName() + ".render");
        }

        try {
            RenderFileSaver saver = new RenderFileSaver(file.getAbsolutePath(), renderModel,camera);

            saver.save();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Can't save render settings:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSaveImage() {
        JFileChooser fileChooser = new JFileChooser("/home/home/NSU/ICG/Raytracing/src/main/resources");

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG image", "png"));

        int result = fileChooser.showSaveDialog(frame);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();

        if (file == null) {
            return;
        }

        if (!file.getName().toLowerCase().endsWith(".png")) {
            file = new File(file.getParentFile(), file.getName() + ".png");
        }

        try {
            frame.getMainPanel().saveImage(file.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Can't save image:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onRenderSettings() {
        RenderSettingsDialog dialog = new RenderSettingsDialog(frame, renderModel);
        dialog.setVisible(true);

        if (dialog.isApplied()) {
            frame.getMainPanel().setBackground(new Color(renderModel.getBackgroundR(),renderModel.getBackgroundG(), renderModel.getBackgroundB()));

            frame.getMainPanel().repaint();
        }
    }

    private void onSelectView() {
        if (rendering) {
            return;
        }

        mode = Mode.VIEW;

        frame.getMainPanel().clearRenderedImage();
        frame.getMainPanel().repaint();
    }

    private void onRender() {
        if (rendering) {
            return;
        }

        rendering = true;
        mode = Mode.RENDER;

        MainPanel mainPanel = frame.getMainPanel();

        JDialog progressDialog = new JDialog(frame, "Rendering", false);
        JProgressBar progressBar = new JProgressBar(0, 100);

        progressBar.setStringPainted(true);
        progressBar.setValue(0);

        progressDialog.setLayout(new BorderLayout());
        progressDialog.add(new JLabel("Rendering scene...", SwingConstants.CENTER), BorderLayout.NORTH);
        progressDialog.add(progressBar, BorderLayout.CENTER);
        progressDialog.setSize(300, 100);
        progressDialog.setLocationRelativeTo(frame);
        progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        renderWorker = new SwingWorker<BufferedImage, Void>() {
            @Override
            protected BufferedImage doInBackground() {
                RayTracer rayTracer = new RayTracer(sceneModel, renderModel, camera);

                return rayTracer.render(
                        mainPanel.getWidth(),
                        mainPanel.getHeight(),
                        this::setProgress
                );
            }

            @Override
            protected void done() {
                try {
                    BufferedImage image = get();
                    mainPanel.setRenderedImage(image);
                } catch (Exception e) {
                    mode = Mode.VIEW;
                    mainPanel.clearRenderedImage();

                    JOptionPane.showMessageDialog(
                            frame,
                            "Render error:\n" + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } finally {
                    rendering = false;
                    progressDialog.dispose();
                }
            }
        };

        renderWorker.addPropertyChangeListener(event -> {
            if ("progress".equals(event.getPropertyName())) {
                int progress = (int) event.getNewValue();
                progressBar.setValue(progress);
            }
        });

        renderWorker.execute();
        progressDialog.setVisible(true);
    }

    private void onAbout() {
        String message = """
            ICGRaytracing
            
            Лабораторная работа по компьютерной графике.
            
            Программа реализует алгоритм трассировки лучей для построения изображения
            трёхмерной сцены.
            
            Возможности программы:
            • загрузка сцены из .scene файла;
            • загрузка и сохранение настроек рендеринга из .render файла;
            • отображение сцены в проволочном режиме;
            • выбор ракурса камеры;
            • управление камерой мышью, колёсиком и клавиатурой;
            • построение изображения методом трассировки лучей;
            • учёт освещения, теней и отражений;
            • настройка цвета фона, gamma и глубины трассировки;
            • сохранение проволочной модели или рассчитанного изображения в файл.
            
            Поддерживаемые примитивы:
            • сферы;
            • боксы;
            • треугольники;
            • четырёхугольники.
            
            Разработчик: Козин Кирилл
            NSU, группа 23211
            """;

        JOptionPane.showMessageDialog(
                frame,
                message,
                "About ICGRaytracing",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public boolean canControlCamera() {
        return mode == Mode.VIEW && !rendering;
    }
}
