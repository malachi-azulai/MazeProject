package org.example;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
      JFrame frame = new JFrame("מבוך ויזואלי - הגדרות");
      frame.setSize(400,600);
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      MenuPanel menuPanel = new MenuPanel();
      frame.add(menuPanel);
      frame.setVisible(true);


    }
}
