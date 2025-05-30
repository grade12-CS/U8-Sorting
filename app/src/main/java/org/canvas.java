package org;

import java.awt.Color;
import java.awt.Graphics;

import org.MultiCanvas.sorting_type;

public class Canvas extends SortingMethods {
    final int array[];
    final int visual_factor = 10; 

    public final sorting_type type;

    public Canvas(int[] array, sorting_type type) {
        this.array = array.clone();
        this.type = type;
        setBackground(Color.BLACK);
    }
    
    public void sort() {
        if (null != type) switch (type) {
            case boggo -> boggoSort(array);
            case bubble -> bubbleSort(array);
            case insertion -> insertionSort(array);
            case selection -> selectionSort(array);
        }
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
