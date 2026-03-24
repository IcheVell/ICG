package view.dialog;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    public AboutDialog(Frame owner) {
        super(owner, "About programm", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(getBackground());
        textArea.setText(
                "ICGFilter — это приложение для обработки растровых изображений " +
                        "с графическим интерфейсом. Программа позволяет открывать изображения " +
                        "в форматах PNG, JPEG, BMP и GIF, просматривать их в реальном размере " +
                        "или с подгонкой под экран, а также применять различные фильтры и эффекты: " +
                        "оттенки серого, негатив, сглаживание, повышение резкости, тиснение, " +
                        "гамма-коррекцию, выделение границ и дизеринг.\n\n" +
                        "Разработчик: Kozin Kirill NSU 23211"
        );

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}