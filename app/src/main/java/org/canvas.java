package org;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class Canvas extends SortingMethods {
    final int array[];
    final int visual_factor = 10; 
    final int min, max, arr_size;

    public Canvas() {
        max = 50;
        min = 10;
        arr_size = 200;
        array = generateArray(arr_size, max, min);
        setBackground(Color.BLACK);
        setPreferredSize(getScreenSize());
        JButton btn_sort = new JButton("Sort");
        btn_sort.addActionListener((ActionEvent e) -> {
            insertionSort(array);
        });
        add(btn_sort);
    }

    private Dimension getScreenSize() {
        int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        return new Dimension(width - 50, height);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = getWidth() / array.length; //make every bars have equal width relative to panel width
        for (int i = 0; i < array.length; ++i) {
            int r = array[i] * 5;
            r = (r > 255) ? 255 : r;
            g.setColor(new Color(r, 100, 0)); //change color for fun
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
