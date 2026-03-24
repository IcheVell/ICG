    package view;

    import view.imagepanel.ImagePanel;
    import view.menu.MyMenuBar;
    import view.toolspanel.ToolPanel;

    import javax.swing.*;
    import java.awt.*;

    public class Frame extends JFrame {
        private final MyMenuBar menuBar;
        private final ToolPanel toolsPanel;
        private final ImagePanel imagePanel;

        public Frame() {
            toolsPanel = new ToolPanel();
            menuBar = new MyMenuBar();
            imagePanel = new ImagePanel();

            JScrollPane scrollPane = new JScrollPane(imagePanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            setMinimumSize(new Dimension(640, 480));
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setJMenuBar(menuBar);

            add(toolsPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
        }

        public ToolPanel getToolsPanel() {
            return toolsPanel;
        }

        public MyMenuBar getMyMenuBar() {
            return menuBar;
        }

        public ImagePanel getImagePanel() {
            return imagePanel;
        }
    }