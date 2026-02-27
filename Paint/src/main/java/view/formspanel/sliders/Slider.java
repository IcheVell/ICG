package view.formspanel.sliders;

import javax.swing.*;
import java.awt.*;

public class Slider extends JSlider {
    public Slider(int min, int max, int val, int minor, int major, String tip, Color color, int orientation) {
        setToolTipText(tip);

        setOrientation(orientation);
        setMinimum(min);
        setMaximum(max);
        setPreferredSize(new Dimension(150, 40));
        setValue(val);

        setMajorTickSpacing(major);
        setMinorTickSpacing(minor);
        setPaintLabels(true);
        setPaintTicks(true);
        setPaintTrack(true);
        setForeground(Color.WHITE);
        setFont(new Font("Times New Roman", Font.PLAIN, 9));

        setBackground(color);
    }
}
