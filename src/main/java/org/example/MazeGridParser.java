package org.example;

import java.awt.image.BufferedImage;

public class MazeGridParser {

    public static boolean[][] extractGridFromImage(BufferedImage img, int rows, int cols) {
        if (img == null) {
            return null;
        }

        boolean[][] mazeGrid = new boolean[rows][cols];
        int pixelCellW = img.getWidth() / cols;
        int pixelCellH = img.getHeight() / rows;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int sampleX = (c * pixelCellW) + (pixelCellW / 2);
                int sampleY = (r * pixelCellH) + (pixelCellH / 2);

                if (sampleX >= img.getWidth()) sampleX = img.getWidth() - 1;
                if (sampleY >= img.getHeight()) sampleY = img.getHeight() - 1;

                int rgb = img.getRGB(sampleX, sampleY);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // אם הפיקסל לבן או קרוב מאוד אליו - מדובר בנתיב פתוח
                if (red > 240 && green > 240 && blue > 240) {
                    mazeGrid[r][c] = true;  // Path
                } else {
                    mazeGrid[r][c] = false; // Wall
                }
            }
        }
        return mazeGrid;
    }
}
