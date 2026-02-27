package controllers;

import enums.Mode;
import models.CanvasModel;
import view.drawpanel.DrawPanel;
import view.menu.MyMenuBar;
import view.toolpanel.ToolPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class MyMenuBarController {
    private final CanvasModel canvasModel;
    private final DrawPanel drawPanel;
    private final ToolPanel toolPanel;
    private final MyMenuBar menuBar;

    public MyMenuBarController(CanvasModel canvasModel, MyMenuBar menuBar, DrawPanel drawPanel, ToolPanel toolPanel) {
        this.canvasModel  = canvasModel;
        this.menuBar = menuBar;
        this.drawPanel = drawPanel;
        this.toolPanel = toolPanel;

        menuBar.getFileMenu().getNewItem().addActionListener(e -> onNewFile());
        menuBar.getFileMenu().getOpenItem().addActionListener(e -> onOpenFile());
        menuBar.getFileMenu().getSaveItem().addActionListener(e -> onSaveFile());

        menuBar.getToolsMenu().getLineToolItem().addActionListener(e -> {
            canvasModel.setMode(Mode.LINE);
            toolPanel.getLineButton().setSelected(true);
            drawPanel.repaint();
        });

        menuBar.getToolsMenu().getFillToolItem().addActionListener(e -> {
            canvasModel.setMode(Mode.FILL);
            toolPanel.getFillButton().setSelected(true);
            drawPanel.repaint();
        });

        menuBar.getToolsMenu().getStampToolItem().addActionListener(e -> {
            canvasModel.setMode(Mode.STAMP);
            toolPanel.getStampButton().setSelected(true);
            drawPanel.repaint();
        });

        menuBar.getAboutMenu().getAboutDeveloperItem().addActionListener(e -> { JOptionPane.showMessageDialog(menuBar, "Paint v1.00\nDeveloper: Kozin Kirill\nNSU group 23211", "About Developer", JOptionPane.INFORMATION_MESSAGE);});
        menuBar.getAboutMenu().getAboutProgramItem().addActionListener(e -> { String message = """
            2D Paint — приложение для рисования двумерных изображений с инструментами «Линия», «Штамп» и «Заливка».
            Позволяет выбирать цвет, толщину линии, форму, радиус и поворот штампов, а также выполнять очистку холста.
            Рабочая область масштабируется с сохранением рисунка, при уменьшении окна появляются полосы прокрутки.
            Поддерживается сохранение и открытие изображений в форматах PNG, JPEG, BMP и GIF.
            """;

            JOptionPane.showMessageDialog(menuBar, message, "About Program", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void onNewFile() {
        canvasModel.clearCanvasModel();
        drawPanel.repaint();
    }

    private void onSaveFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(menuBar) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ImageIO.write(canvasModel.getImage(), "PNG", file);
                JOptionPane.showMessageDialog(menuBar, "Saved");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(menuBar, "Error: Can't save");
            }
        }
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
                canvasModel.setImage(ImageIO.read(selectedFile));

                drawPanel.revalidate();
                drawPanel.repaint();

                JOptionPane.showMessageDialog(menuBar, "Loaded");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(menuBar, "Error: can't load file");
            }
        }
    }
}
