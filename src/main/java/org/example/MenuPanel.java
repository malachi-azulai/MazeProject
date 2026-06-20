package org.example;
import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private JLabel configLabel;
    private RenderConfig renderConfig;

    public MenuPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        this.renderConfig = new RenderConfig();

        // --- אזור הגדרות הציור מהשרת ---
        JLabel titleConfig = new JLabel("--- הגדרות ציור מהשרת ---");
        titleConfig.setFont(labelFont);
        titleConfig.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(titleConfig);

        configLabel = new JLabel("טוען הגדרות...");
        configLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        configLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(configLabel);

        JButton refreshButton = new JButton("Refresh Config");
        refreshButton.setFont(buttonFont);
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.addActionListener(e -> fetchConfigFromServer());
        this.add(refreshButton);

        this.add(Box.createVerticalStrut(20));

        // --- שדות קלט עבור המידות ---
        JLabel widthLabel = new JLabel("הכנס רוחב מבוך (5-100):");
        widthLabel.setFont(labelFont);
        widthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(widthLabel);
        JTextField widthField = new JTextField("30", 3);
        widthField.setFont(labelFont);
        widthField.setAlignmentX(Component.CENTER_ALIGNMENT);
        widthField.setMaximumSize(new Dimension(100, 40));
        this.add(widthField);

        JLabel heightLabel = new JLabel("הכנס גובה מבוך (5-100):");
        heightLabel.setFont(labelFont);
        heightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(heightLabel);
        JTextField heightField = new JTextField("30", 3);
        heightField.setFont(labelFont);
        heightField.setAlignmentX(Component.CENTER_ALIGNMENT);
        heightField.setMaximumSize(new Dimension(100, 40));
        this.add(heightField);

        this.add(Box.createVerticalStrut(20));
        JButton mazeButton = new JButton("GET MAZE");
        mazeButton.setFont(buttonFont);
        mazeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(mazeButton);

        this.add(Box.createVerticalStrut(10));
        JButton checkSolutionButton = new JButton("Check Solution");
        checkSolutionButton.setFont(buttonFont);
        checkSolutionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(checkSolutionButton);

        MazePanel mazePanel = new MazePanel();

        // כאן אנחנו מעבירים את ה-renderConfig לתוך ה-ActionListener
        MazeActionListener mazeActionListener = new MazeActionListener(widthField, heightField, mazePanel, renderConfig);
        mazeButton.addActionListener(mazeActionListener);
        checkSolutionButton.addActionListener(mazeActionListener);

        this.add(mazePanel);

        fetchConfigFromServer(); // טעינה אוטומטית כשהתוכנית עולה
    }
    private void fetchConfigFromServer() {
        configLabel.setText("מרענן הגדרות...");
        new Thread(() -> {
            try {
                NetWorkManager netManager = new NetWorkManager();
                String jsonResult = netManager.fetchRenderConfigJson();

                // שורה אחת שמבצעת את כל העבודה הקשה דרך המחלקה הייעודית
                ConfigParser parser = new ConfigParser();
                this.renderConfig = parser.parseConfig(jsonResult);

                SwingUtilities.invokeLater(() -> {
                    String text = String.format(
                            "<html><center>" +
                                    "צבע קיר: %s<br>" +
                                    "צבע נתיב: %s<br>" +
                                    "האם לצייר רשת: %b<br>" +
                                    "צבע רשת: %s<br>" +
                                    "דיליי אנימציה: %dms" +
                                    "</center></html>",
                            renderConfig.getWallCellColor(),
                            renderConfig.getPathColor(),
                            renderConfig.isDrawGrid(),
                            renderConfig.getGridColor(),
                            renderConfig.getAnimationDelayMs()
                    );
                    configLabel.setText(text);
                });

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> configLabel.setText("שגיאה בטעינת הגדרות."));
                ex.printStackTrace();
            }
        }).start();
    }

}
