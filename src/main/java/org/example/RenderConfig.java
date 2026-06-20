package org.example;

import java.awt.Color;

public class RenderConfig {
    private String wallCellColor;
    private String pathColor;
    private boolean drawGrid;
    private String gridColor;
    private int animationDelayMs;

    public RenderConfig() {
        // ערכי דיפולט זמניים למניעת NullPointerException עד שהשרת עונה
        this.wallCellColor = "#000000";
        this.pathColor = "#00FF00";
        this.gridColor = "#CCCCCC";
        this.animationDelayMs = 50;
    }

    public String getWallCellColor() { return this.wallCellColor; }
    public void setWallCellColor(String wallCellColor) { this.wallCellColor = wallCellColor; }

    public String getPathColor() { return this.pathColor; }
    public void setPathColor(String pathColor) { this.pathColor = pathColor; }

    public boolean isDrawGrid() { return this.drawGrid; }
    public void setDrawGrid(boolean drawGrid) { this.drawGrid = drawGrid; }

    public String getGridColor() { return this.gridColor; }
    public void setGridColor(String gridColor) { this.gridColor = gridColor; }

    public int getAnimationDelayMs() { return this.animationDelayMs; }
    public void setAnimationDelayMs(int animationDelayMs) { this.animationDelayMs = animationDelayMs; }

    public Color convertHexToColor(String hexStr) {
        if (hexStr == null || hexStr.trim().isEmpty()) {
            return Color.GREEN; //
        }
        try {
            return Color.decode(hexStr);
        } catch (NumberFormatException e) {
            return Color.BLACK;
        }
    }
}