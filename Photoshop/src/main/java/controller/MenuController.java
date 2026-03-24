package controller;

import controller.actions.ActionFactory;
import enums.Command;
import enums.Mode;
import enums.Type;
import model.FilterModel;
import model.filter.*;
import view.Frame;
import view.dialog.*;
import view.imagepanel.ImagePanel;
import view.menu.MyMenuBar;
import view.toolspanel.ToolPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class MenuController {
    private final FilterModel filterModel;
    private final Frame frame;
    private final ActionFactory actionFactory;
    private final MyMenuBar menuBar;
    private final ImagePanel imagePanel;
    private final ToolPanel toolPanel;
    private final SmoothingSettingsDialog smoothingSettingsDialog;
    private final GammaSettingDialog gammaSettingsDialog;
    private final SettingsDialog robertsSettingsDialog;
    private final SettingsDialog sobelSettingsDialog;
    private final SettingsDialog oilPainitingSettingsDialog;
    private final DitheringSettingsDialog ditheringSettingsDialog;
    private final DitheringSettingsDialog orderedDitheringSettingsDialog;
    private final SettingsDialog rotateSettingsDialog;
    private final ResizeSettingsDialog resizeSettingsDialog;
    private final AboutDialog aboutDialog;

    public MenuController(FilterModel filterModel, Frame frame, ActionFactory actionFactory) {
        this.filterModel = filterModel;
        this.frame = frame;
        this.actionFactory = actionFactory;
        menuBar = frame.getMyMenuBar();
        imagePanel = frame.getImagePanel();
        toolPanel = frame.getToolsPanel();


        smoothingSettingsDialog = new SmoothingSettingsDialog(filterModel, frame, 3, 11, 2, 2, "Smoothing settings");
        addSmoothingDialogListeners();

        gammaSettingsDialog = new GammaSettingDialog(filterModel, frame);
        addGammaSettingDialogListeners();

        robertsSettingsDialog = new SettingsDialog(filterModel, frame, "Roberts Edge Settings", 0, 510, 100);
        addRobertsEdgeFilterListeners();

        sobelSettingsDialog = new SettingsDialog(filterModel, frame, "Sobel Edge Settings", 0, 2040, 350);
        addSobelEdgeFilterListeners();

        oilPainitingSettingsDialog = new SettingsDialog(filterModel, frame, "Oil Painting Settings", 16, 256, 32);
        addOilPaintingFilterListeners();

        ditheringSettingsDialog = new DitheringSettingsDialog(filterModel, frame, "Dithering Settings");
        addDitheringFilterListeners(ditheringSettingsDialog, Type.UNORDERED);

        orderedDitheringSettingsDialog = new DitheringSettingsDialog(filterModel, frame, "Ordered Dithering Settings");
        addDitheringFilterListeners(orderedDitheringSettingsDialog, Type.ORDERED);

        rotateSettingsDialog = new SettingsDialog(filterModel, frame, "Rotate Image Settings", -180, 180, 72);
        addRotateImageListeners();

        resizeSettingsDialog = new ResizeSettingsDialog(frame, filterModel);
        addResizeImageListeners();

        aboutDialog = new AboutDialog(frame);

        initActions();
        bindActions();
    }

    private void initActions() {
        actionFactory.addAction(Command.OPEN, ActionFactory.action("Open", this::onOpenFile));
        actionFactory.addAction(Command.SAVE, ActionFactory.action("Save", this::onSaveFile));

        actionFactory.addAction(Command.VIEW_ORIGINAL, ActionFactory.action("Original", this::onOriginalImage));
        actionFactory.addAction(Command.VIEW_FILTERED, ActionFactory.action("Filtered", this::onFilteredImage));

        actionFactory.addAction(Command.ROTATE, ActionFactory.action("Rotate", this::onRotateImage));
        actionFactory.addAction(Command.RESIZE, ActionFactory.action("Resize", this::onResizeImage));

        actionFactory.addAction(Command.GRAYSCALE, ActionFactory.action("Grayscale", this::onGrayscaleFilter));
        actionFactory.addAction(Command.INVERT, ActionFactory.action("Invert", this::onInvertFilter));
        actionFactory.addAction(Command.SMOOTHING, ActionFactory.action("Smoothing", this::onSmoothingFilter));
        actionFactory.addAction(Command.SHARPEN, ActionFactory.action("Sharpen", this::onSharpenFilter));
        actionFactory.addAction(Command.EMBOSS,  ActionFactory.action("Emboss", this::onEmbossFilter));
        actionFactory.addAction(Command.GAMMA, ActionFactory.action("Gamma", this::onGammaFilter));
        actionFactory.addAction(Command.ROBERTS, ActionFactory.action("Roberts", this::onRobertsEdgeFilter));
        actionFactory.addAction(Command.SOBEL, ActionFactory.action("Sobel", this::onSobelsEdgeFilter));
        actionFactory.addAction(Command.DITHERING, ActionFactory.action("Dithering", this::onDitheringFilter));
        actionFactory.addAction(Command.ORDERED_DITHERING, ActionFactory.action("Ordered dithering", this::onOrderedDithreringFilter));
        actionFactory.addAction(Command.WATERCOLOR, ActionFactory.action("WaterColor", this::onWaterColorFilter));
        actionFactory.addAction(Command.OIL_PAINTING, ActionFactory.action("OilPainting", this::onOilPaintingFilter));
        actionFactory.addAction(Command.ABOUT, ActionFactory.action("About", this::onAbout));
    }

    private void bindActions() {
        menuBar.getFileMenu().getOpenMenuItem().setAction(actionFactory.getAction(Command.OPEN));
        menuBar.getFileMenu().getSaveMenuItem().setAction(actionFactory.getAction(Command.SAVE));

        menuBar.getViewMenu().getOriginalImageItem().setAction(actionFactory.getAction(Command.VIEW_ORIGINAL));
        menuBar.getViewMenu().getFilteredImageItem().setAction(actionFactory.getAction(Command.VIEW_FILTERED));

        menuBar.getModifyMenu().getRotateImageItem().setAction(actionFactory.getAction(Command.ROTATE));
        menuBar.getModifyMenu().getResizeImageItem().setAction(actionFactory.getAction(Command.RESIZE));

        menuBar.getFilterMenu().getGrayscaleItem().setAction(actionFactory.getAction(Command.GRAYSCALE));
        menuBar.getFilterMenu().getInvertItem().setAction(actionFactory.getAction(Command.INVERT));
        menuBar.getFilterMenu().getSmoothingItem().setAction(actionFactory.getAction(Command.SMOOTHING));
        menuBar.getFilterMenu().getSharpenItem().setAction(actionFactory.getAction(Command.SHARPEN));
        menuBar.getFilterMenu().getEmbossItem().setAction(actionFactory.getAction(Command.EMBOSS));
        menuBar.getFilterMenu().getGammaItem().setAction(actionFactory.getAction(Command.GAMMA));
        menuBar.getFilterMenu().getRobertsEdgeItem().setAction(actionFactory.getAction(Command.ROBERTS));
        menuBar.getFilterMenu().getSobelsEdgeItem().setAction(actionFactory.getAction(Command.SOBEL));
        menuBar.getFilterMenu().getDitheringItem().setAction(actionFactory.getAction(Command.DITHERING));
        menuBar.getFilterMenu().getOrderedDitheringItem().setAction(actionFactory.getAction(Command.ORDERED_DITHERING));
        menuBar.getFilterMenu().getWaterColorItem().setAction(actionFactory.getAction(Command.WATERCOLOR));
        menuBar.getFilterMenu().getOilPaintingItem().setAction(actionFactory.getAction(Command.OIL_PAINTING));

        menuBar.getHelpMenu().getHelpMenuItem().setAction(actionFactory.getAction(Command.ABOUT));
    }

    private void bindSliderAndSpinner(JSlider slider, JSpinner spinner) {
        slider.addChangeListener(e -> {
            spinner.setValue(slider.getValue());
        });

        spinner.addChangeListener(e -> {
            slider.setValue(Integer.parseInt(spinner.getValue().toString()));
        });
    }

    private void addResizeImageListeners() {
        resizeSettingsDialog.getOkButton().addActionListener(e -> {
            changeOnFilteredImage();
            toolPanel.getResizeModifyItem().setSelected(true);
            menuBar.getModifyMenu().getResizeImageItem().setSelected(true);
            menuBar.getFilterMenu().getFilterButtonGroup().clearSelection();

            switch (resizeSettingsDialog.getInterpolationBox().getSelectedItem().toString()) {
                case "NEAREST_NEIGHBOUR" -> filterModel.setRenderValue(RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                case "BILINEAR" -> filterModel.setRenderValue(RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                case "BICUBIC" -> filterModel.setRenderValue(RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            }

            switch(resizeSettingsDialog.getResizeModeBox().getSelectedItem().toString()) {
                case "DEFAULT" -> filterModel.resizeImage(filterModel.getOriginalImage().getWidth(), filterModel.getOriginalImage().getHeight());
                case "FULLSCREEN" -> {
                    int width = imagePanel.getVisibleRect().width >= 1920 ? 1920 : imagePanel.getVisibleRect().width + 15;
                    int height = imagePanel.getVisibleRect().height >= 1080 ? 1080 : imagePanel.getVisibleRect().height + 15;
                    filterModel.resizeImage(width, height);

                    menuBar.getFilterMenu().getFilterButtonGroup().clearSelection();
                }
            }

            resizeSettingsDialog.setVisible(false);

            imagePanel.repaint();
        });

        resizeSettingsDialog.getCancelButton().addActionListener(e -> resizeSettingsDialog.setVisible(false));
    }

    private void addRotateImageListeners() {
        JSlider slider = rotateSettingsDialog.getSlider();
        JSpinner spinner = rotateSettingsDialog.getSpinner();

        bindSliderAndSpinner(slider, spinner);

        rotateSettingsDialog.getOkButton().addActionListener(e -> {
            changeOnFilteredImage();

            filterModel.setRotateDegrees(slider.getValue());

            filterModel.rotateImage();
            rotateSettingsDialog.setVisible(false);

            menuBar.getFilterMenu().getFilterButtonGroup().clearSelection();
            menuBar.getModifyMenu().getRotateImageItem().setSelected(true);
            toolPanel.getRotateModifyItem().setSelected(true);

            imagePanel.repaint();
        });

        rotateSettingsDialog.getCancelButton().addActionListener(e -> sobelSettingsDialog.setVisible(false));
    }

    private void addDitheringFilterListeners(DitheringSettingsDialog settingsDialog, Type type) {
        JSlider sliderR = settingsDialog.getSliderR();
        JSpinner spinnerR = settingsDialog.getSpinnerR();

        bindSliderAndSpinner(sliderR, spinnerR);

        JSlider sliderG = settingsDialog.getSliderG();
        JSpinner spinnerG = settingsDialog.getSpinnerG();

        bindSliderAndSpinner(sliderG, spinnerG);

        JSlider sliderB = settingsDialog.getSliderB();
        JSpinner spinnerB = settingsDialog.getSpinnerB();

        bindSliderAndSpinner(sliderB, spinnerB);

        settingsDialog.getOkButton().addActionListener(e -> {
            changeOnFilteredImage();

            if (type == Type.UNORDERED) {
                toolPanel.getDitheringFilterItem().setSelected(true);
                menuBar.getFilterMenu().getDitheringItem().setSelected(true);

                filterModel.setQuantsR(Integer.parseInt(spinnerR.getValue().toString()));
                filterModel.setQuantsG(Integer.parseInt(spinnerG.getValue().toString()));
                filterModel.setQuantsB(Integer.parseInt(spinnerB.getValue().toString()));
                filterModel.applyFilter(new DitheringFilter(filterModel));
            }

            if (type ==  Type.ORDERED) {
                toolPanel.getOrderedDitheringFilterItem().setSelected(true);
                menuBar.getFilterMenu().getOrderedDitheringItem().setSelected(true);

                filterModel.setOrderedQuantsR(Integer.parseInt(spinnerR.getValue().toString()));
                filterModel.setOrderedQuantsG(Integer.parseInt(spinnerG.getValue().toString()));
                filterModel.setOrderedQuantsB(Integer.parseInt(spinnerB.getValue().toString()));
                filterModel.applyFilter(new OrderedDitheringFilter(filterModel));
            }

            settingsDialog.setVisible(false);

            imagePanel.repaint();
        });

        ditheringSettingsDialog.getCancelButton().addActionListener(e -> ditheringSettingsDialog.setVisible(false));
    }

    private void addSobelEdgeFilterListeners() {
        JSlider slider = sobelSettingsDialog.getSlider();
        JSpinner spinner = sobelSettingsDialog.getSpinner();

        bindSliderAndSpinner(slider, spinner);

        sobelSettingsDialog.getOkButton().addActionListener(e -> {
            changeOnFilteredImage();

            toolPanel.getSobelsEdgeFilterItem().setSelected(true);
            menuBar.getFilterMenu().getSobelsEdgeItem().setSelected(true);

            filterModel.setThreshold(Integer.parseInt(spinner.getValue().toString()));
            sobelSettingsDialog.setVisible(false);

            filterModel.applyFilter(new SobelEdgeFilter(filterModel));

            imagePanel.repaint();
        });

        sobelSettingsDialog.getCancelButton().addActionListener(e -> sobelSettingsDialog.setVisible(false));
    }

    private void addOilPaintingFilterListeners() {
        JSlider slider = oilPainitingSettingsDialog.getSlider();
        JSpinner spinner = oilPainitingSettingsDialog.getSpinner();

        bindSliderAndSpinner(slider, spinner);

        oilPainitingSettingsDialog.getOkButton().addActionListener(e -> {
            changeOnFilteredImage();

            toolPanel.getRobertsEdgeFilterItem().setSelected(true);
            menuBar.getFilterMenu().getOilPaintingItem().setSelected(true);

            filterModel.setOilBinsCount(Integer.parseInt(spinner.getValue().toString()));
            oilPainitingSettingsDialog.setVisible(false);

            filterModel.applyFilter(new OilPaintingFilter(filterModel));

            imagePanel.repaint();
        });

        oilPainitingSettingsDialog.getCancelButton().addActionListener(e -> oilPainitingSettingsDialog.setVisible(false));
    }

    private void addRobertsEdgeFilterListeners() {
        JSlider slider = robertsSettingsDialog.getSlider();
        JSpinner spinner = robertsSettingsDialog.getSpinner();

        bindSliderAndSpinner(slider, spinner);

        robertsSettingsDialog.getOkButton().addActionListener(e -> {
            changeOnFilteredImage();

            toolPanel.getRobertsEdgeFilterItem().setSelected(true);
            menuBar.getFilterMenu().getRobertsEdgeItem().setSelected(true);

            filterModel.setThreshold(Integer.parseInt(spinner.getValue().toString()));
            robertsSettingsDialog.setVisible(false);

            filterModel.applyFilter(new RobertsEdgeFilter(filterModel));

            imagePanel.repaint();
        });

        robertsSettingsDialog.getCancelButton().addActionListener(e -> robertsSettingsDialog.setVisible(false));
    }

    private void addGammaSettingDialogListeners() {
        JSpinner spinner = gammaSettingsDialog.getSpinner();

        gammaSettingsDialog.getOkButton().addActionListener(e -> {
            changeOnFilteredImage();

            toolPanel.getGammaFilterItem().setSelected(true);
            menuBar.getFilterMenu().getGammaItem().setSelected(true);

            filterModel.setGamma(Float.parseFloat(spinner.getValue().toString()));
            filterModel.applyFilter(new GammaFilter(filterModel));
            imagePanel.repaint();
            gammaSettingsDialog.setVisible(false);
        });

        gammaSettingsDialog.getCancelButton().addActionListener(e -> gammaSettingsDialog.setVisible(false));
    }

    private void addSmoothingDialogListeners() {
        JSlider slider = smoothingSettingsDialog.getSlider();
        JSpinner spinner = smoothingSettingsDialog.getSpinner();

        slider.addChangeListener(e -> {
            int value = slider.getValue();

            if (value % 2 == 0) {
                value--;
            }

            slider.setValue(value);
            spinner.setValue(value);
        });

        spinner.addChangeListener(e -> {
            slider.setValue(Integer.parseInt(spinner.getValue().toString()));
        });

        smoothingSettingsDialog.getOkButton().addActionListener(e -> {
            changeOnFilteredImage();

            toolPanel.getSmoothingFilterItem().setSelected(true);
            menuBar.getFilterMenu().getSmoothingItem().setSelected(true);

            filterModel.setSmoothingKernelSize(Integer.parseInt(spinner.getValue().toString()));
            smoothingSettingsDialog.setVisible(false);
            if (filterModel.getSmoothingKernelSize() < 7) {
                filterModel.applyFilter(new SmoothingFilter(filterModel));
            }

            if (filterModel.getSmoothingKernelSize() >= 7) {
                filterModel.applyFilter(new AverageFilter(filterModel));
            }

            imagePanel.repaint();
        });

        smoothingSettingsDialog.getCancelButton().addActionListener(e -> smoothingSettingsDialog.setVisible(false));
    }

    private void onAbout() {
        aboutDialog.setVisible(true);
    }

    private void onOpenFile() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG", "jpg"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("BMP", "bmp"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("GIF", "gif"));

        int result = fileChooser.showOpenDialog(menuBar);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                menuBar.getFilterMenu().getFilterButtonGroup().clearSelection();

                BufferedImage image = ImageIO.read(selectedFile);

                filterModel.setOriginalImage(image);

                filterModel.setFiltered(copy(image));
                imagePanel.revalidate();
                imagePanel.repaint();

                JOptionPane.showMessageDialog(menuBar, "Loaded");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(menuBar, "Error: can't load file");
            }
        }
    }

    private void onSaveFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(menuBar) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ImageIO.write(filterModel.getFilteredImage(), "PNG", file);
                JOptionPane.showMessageDialog(menuBar, "Saved");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(menuBar, "Error: Can't save");
            }
        }
    }

    private void onOriginalImage() {
        filterModel.setMode(Mode.ORIGINAL);
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void onFilteredImage() {
        filterModel.setMode(Mode.FILTERED);
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void onRotateImage() {
        rotateSettingsDialog.setVisible(true);
    }

    private void onResizeImage() {
        resizeSettingsDialog.setVisible(true);
    }

    private void onGrayscaleFilter() {
        changeOnFilteredImage();

        toolPanel.getGrayscaleFilterItem().setSelected(true);
        menuBar.getFilterMenu().getGrayscaleItem().setSelected(true);

        filterModel.applyFilter(new GrayscaleFilter(filterModel));
        imagePanel.repaint();
    }

    private void onInvertFilter() {
        changeOnFilteredImage();

        toolPanel.getInvertFilterItem().setSelected(true);
        menuBar.getFilterMenu().getInvertItem().setSelected(true);

        filterModel.applyFilter(new InvertFilter(filterModel));
        imagePanel.repaint();
    }

    private void onSmoothingFilter() {
        smoothingSettingsDialog.setVisible(true);
    }

    private void onSharpenFilter() {
        changeOnFilteredImage();

        toolPanel.getSharpenFilterItem().setSelected(true);
        menuBar.getFilterMenu().getSharpenItem().setSelected(true);

        filterModel.applyFilter((new SharpenFilter(filterModel)));
        imagePanel.repaint();
    }

    private void onEmbossFilter() {
        changeOnFilteredImage();

        toolPanel.getEmbossFilterItem().setSelected(true);
        menuBar.getFilterMenu().getEmbossItem().setSelected(true);

        filterModel.applyFilter(new EmbossFilter());
        imagePanel.repaint();
    }

    private void onGammaFilter() {
        gammaSettingsDialog.setVisible(true);
    }

    private void onRobertsEdgeFilter() {
        robertsSettingsDialog.setVisible(true);
    }

    private void onSobelsEdgeFilter() {
        sobelSettingsDialog.setVisible(true);
    }

    private void onDitheringFilter() {
        ditheringSettingsDialog.setVisible(true);
    }

    private void onOrderedDithreringFilter() {
        orderedDitheringSettingsDialog.setVisible(true);
    }

    private void onOilPaintingFilter() {
        oilPainitingSettingsDialog.setVisible(true);
    }

    private void onWaterColorFilter() {
        changeOnFilteredImage();

        toolPanel.getWaterColorFilterItem().setSelected(true);
        menuBar.getFilterMenu().getWaterColorItem().setSelected(true);

        filterModel.applyFilter(new WaterColorFilter());
        imagePanel.repaint();
    }

    private BufferedImage copy(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    private void changeOnFilteredImage() {
        filterModel.setMode(Mode.FILTERED);
        menuBar.getViewMenu().getFilteredImageItem().setSelected(true);
        imagePanel.revalidate();
        imagePanel.repaint();
    }
}
