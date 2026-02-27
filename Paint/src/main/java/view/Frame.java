    package view;

    import view.menu.MyMenuBar;
    import view.drawpanel.DrawPanel;
    import view.colorpanel.ColorPanel;
    import view.formspanel.FormsPanel;
    import view.toolpanel.ToolPanel;

    import javax.swing.*;
    import java.awt.*;

    public class Frame extends JFrame {
        private final DrawPanel drawPanel;
        private final FormsPanel formsPanel;
        private final ColorPanel colorPanel;
        private final ToolPanel toolPanel;
        private final MyMenuBar menuBar;

        public Frame() {
            drawPanel = new DrawPanel();
            formsPanel = new FormsPanel();
            colorPanel = new ColorPanel();
            toolPanel = new ToolPanel();
            menuBar = new MyMenuBar();

            JPanel topPanel = new JPanel();
            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
            topPanel.setBackground(Color.DARK_GRAY);

            topPanel.add(toolPanel);
            topPanel.add(formsPanel);
            topPanel.add(colorPanel);

            JScrollPane scrollPane = new JScrollPane(drawPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            setLayout(new BorderLayout());
            setMinimumSize(new Dimension(1280,480));
            setMaximumSize(new Dimension(1920, 1080));
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setJMenuBar(menuBar);

            add(topPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
        }

        public DrawPanel getDrawPanel() {
            return drawPanel;
        }

        public FormsPanel getFormsPanel() {
            return formsPanel;
        }

        public MyMenuBar getMyMenuBar() {
            return menuBar;
        }

        public ColorPanel getColorPanel() {
            return colorPanel;
        }

        public ToolPanel getToolPanel() {
            return toolPanel;
        }
    }
