package org;

import java.awt.Color;

import javax.swing.JFrame;
/**
 * @author Jiamiao Zhen, Sarah Yoo
 * @version 1.0
 * completion date: 2025, 6, 4  
 * description: an app visualizing and measuring timing for 4 sorting algorithms
 */

/**
 * main app class where program is launched
 */
public class App extends JFrame {

    /**
     * create main frame
     */
    public App() {
        setVisible(true);
        setTitle("Sorting Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.BLACK);
        add(new MultiCanvas());
        pack();
    }

    /**
     * run on AppTest.java. shows a message if build succeds
     * @return
     */
    public String testBuild() {
        return "build is successful";
    }

    /**
     * initiate program
     * @param args
     */
    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.testBuild());
    }
}
