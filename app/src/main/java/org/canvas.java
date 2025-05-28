package org;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class canvas extends JPanel {
    int array[] = {10, 9, 8, 6, 7, 5};

    public canvas() {
        setPreferredSize(new Dimension(600, 500));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int visual_factor = 10; //adjust bar height to be visible
        int width = getWidth() / array.length; //make every bars have equal width relative to panel width
        for (int i = 0; i < array.length; ++i) {
            g.setColor(new Color(i * 20, 100, 0)); //change color for fun
            int height = array[i] * visual_factor; //bar height reflects the value of each number in array 
            /* for each bar
             * x pos: i * width + 2, add 2 for tiny padding
             * y pos: panel height - bar height --> ensures bars to be drawn at the bottom
             * bar width: width - 2 -> 2 is for gap between bars
             * bar height: value * visual factor
             */
            g.fillRect(i * width + 2, getHeight() - height, width - 2, height);
        }
    }
}
