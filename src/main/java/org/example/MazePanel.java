package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;

public class MazePanel extends JPanel {
    private BufferedImage mazeImage;
    private boolean[][] mazeGrid; // המערך האמיתי שיגיד ל-DFS איפה יש קיר
    private boolean[][] visitedPath;
    private int rows = 30;
    private int cols = 30;
    private RenderConfig config;
    private boolean isAnimating = false;

    public MazePanel() {
    }

    public void setupMaze(BufferedImage img, RenderConfig config) {
        this.mazeImage = img;
        this.config = config;
        this.isAnimating = false;

        if (img != null) {
            this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));

            // קריאה למחלקת העזר החדשה שהפרדנו כדי לקבל את המטריצה!
            this.mazeGrid = MazeGridParser.extractGridFromImage(img, rows, cols);
            this.visitedPath = new boolean[rows][cols];
        }

        if (javax.swing.SwingUtilities.getWindowAncestor(this) != null) {
            javax.swing.SwingUtilities.getWindowAncestor(this).revalidate();
            javax.swing.SwingUtilities.getWindowAncestor(this).repaint();
        }
        repaint();
    }

    // מעבירים ל-ActionListener את המערך האמיתי שפענחנו מהפיקסלים!
    public boolean[][] getMazeGrid() {
        return this.mazeGrid;
    }

    public int getRows() { return this.rows; }
    public int getCols() { return this.cols; }
    public boolean isAnimating() { return this.isAnimating; }

    public void startSolutionAnimation(List<int[]> path) {
        if (isAnimating || path == null || config == null) return;
        this.isAnimating = true;
        this.visitedPath = new boolean[rows][cols];

        new Thread(() -> {
            try {
                int delay = config.getAnimationDelayMs();
                for (int[] point : path) {
                    int r = point[0];
                    int c = point[1];
                    visitedPath[r][c] = true;
                    repaint();
                    Thread.sleep(delay);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.isAnimating = false;
            }
        }).start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mazeImage == null) return;

        g.drawImage(mazeImage, 0, 0, getWidth(), getHeight(), null);

        int cellW = getWidth() / cols;
        int cellH = getHeight() / rows;

        if (config != null && visitedPath != null) {
            Color pathColor = config.convertHexToColor(config.getPathColor());
            g.setColor(pathColor);

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (visitedPath[r][c]) {
                        g.fillRect(c * cellW, r * cellH, cellW, cellH);
                    }
                }
            }
        }

        if (config != null && config.isDrawGrid()) {
            //   Color gridColor = config.convertHexToColor(config.getGridColor());
            Color gridColor = Color.RED;
            g.setColor(gridColor);

            for (int c = 0; c <= cols; c++) {
                g.drawLine(c * cellW, 0, c * cellW, getHeight());
            }

            for (int r = 0; r <= rows; r++) {
                g.drawLine(0, r * cellH, getWidth(), r * cellH);
            }
        }
    }

}