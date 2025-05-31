package org;


import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class App extends JFrame {
    final MultiCanvas canvas = new MultiCanvas();
    final TimeDisplay timer = new TimeDisplay();

    public App() {
        setVisible(true);
        setTitle("Sorting Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(canvas);
        add(timer);
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
