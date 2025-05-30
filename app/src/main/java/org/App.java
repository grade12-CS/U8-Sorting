package org;

import javax.swing.JFrame;

public class App extends JFrame {
    final Canvas canvas = new Canvas();
    
    public App() {
        setVisible(true);
        setTitle("Sorting Visualization");
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
