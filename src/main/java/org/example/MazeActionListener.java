package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MazeActionListener implements ActionListener {
    private JTextField widthField;
    private JTextField heightField;
    private MazePanel mazePanel;
    private RenderConfig renderConfig;

    public MazeActionListener(JTextField widthField, JTextField heightField, MazePanel mazePanel, RenderConfig renderConfig) {
        this.widthField = widthField;
        this.heightField = heightField;
        this.mazePanel = mazePanel;
        this.renderConfig = renderConfig;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String buttonText = clickedButton.getText();

        if (buttonText.equals("GET MAZE")) {
            int width = 30;
            int height = 30;

            try {
                width = Integer.parseInt(widthField.getText());
                if (width < 5 || width > 100) width = 30;
            } catch (NumberFormatException ex) {
                width = 30;
            }

            try {
                height = Integer.parseInt(heightField.getText());
                if (height < 5 || height > 100) height = 30;
            } catch (NumberFormatException ex) {
                height = 30;
            }

            try {
                NetWorkManager netManager = new NetWorkManager();
                BufferedImage mazeImage = netManager.fetchMazeImage(width, height);
                System.out.println("Image downloaded. Size: " + mazeImage.getWidth() + "x" + mazeImage.getHeight());

                mazePanel.setupMaze(mazeImage, renderConfig);

                JFrame mazeFrame = new JFrame("Generated Maze");
                mazeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mazeFrame.add(mazePanel);
                mazeFrame.setResizable(false);

                mazeFrame.pack();
                mazeFrame.setLocationRelativeTo(null);
                mazeFrame.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (buttonText.equals("Check Solution")) {
            if (mazePanel.isAnimating()) {
                return;
            }

            boolean[][] grid = mazePanel.getMazeGrid();
            if (grid == null) {
                JOptionPane.showMessageDialog(null, "No maze loaded yet!");
                return;
            }

            int rows = mazePanel.getRows();
            int cols = mazePanel.getCols();

            MazeSolver solver = new MazeSolver();
            List<int[]> path = solver.solve(grid, rows, cols);

            if (path != null) {
                mazePanel.startSolutionAnimation(path);
            } else {
                JOptionPane.showMessageDialog(null, "No solution found");
            }
        }
    }
}

