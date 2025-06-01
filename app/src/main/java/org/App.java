package org;


import java.awt.Color;

import javax.swing.JFrame;

public class App extends JFrame {
    final MultiCanvas canvas = new MultiCanvas();

    public App() {
        setVisible(true);
        setTitle("Sorting Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.BLACK);
        add(canvas);
        pack();
    }

    public String testBuild() {
        return "build is successful";
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.testBuild());
    }
}
