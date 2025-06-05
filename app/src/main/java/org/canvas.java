package org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import org.SortingMethods.sorting_type;

/**
 * A canvas allocated for each sorting algorithms. displays timer and sorting visualization canvas 
 */
public class Canvas extends JPanel {
    private final TimeDisplay timeDisplay; 
    private final SubCanvas canvas;

    /**
     * set up sub canvas and timer display 
     * @param array array to sort
     * @param type type of sorting algoritm
     */
    public Canvas(int[] array, sorting_type type) {
        canvas = new SubCanvas(array, type);
        timeDisplay = new TimeDisplay(type.name, TimeUnit.MILLISECONDS, () -> canvas.sorted);    
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(timeDisplay, BorderLayout.EAST);
    }

    /**
     * set array to sort
     * @param newArray array to sort
     */
    public void setArray(int[] newArray) {
        canvas.setArray(newArray);
    }

    /**
     * starts sorting and timer
     */
    public void startSort() {
        if (canvas.sorted) return;
        timeDisplay.startMeasuring();
        canvas.sort();
    }

    /**
     * manually sets sorted boolean for sub canvas
     * @param bool boolean for sorted
     */
    public void setSorted(boolean bool) {
        canvas.sorted = bool;
    }

    /**
     * sets delay time between visualization update (when numbers are swapped in array)
     * @param delay delay time in duration
     */
    public void setDelay(Duration delay) {
        canvas.delay = delay;
    }

    /**
     * A sub canvas class that draws visuals
     */
    public class SubCanvas extends SortingMethods {
        final int array[];
        final int visual_factor = 1;
        public final sorting_type type;

        /**
         * set up array, algoritm type, and background color
         * @param array array to visualize
         * @param type type of sorting algorithm
         */
        public SubCanvas(int[] array, sorting_type type) {
            this.array = array.clone();
            this.type = type;
            setBackground(Color.BLACK);
        }

        /**
         * sets array to visualize
         * @param newArray
         */
        public void setArray(int[] newArray) {
            System.arraycopy(newArray, 0, this.array, 0, this.array.length);
            timeDisplay.setLabel("Array updated");
            repaint();
        }
        
        /**
         * starts sorting the array based on algoritm type
         */
        public void sort() {
            if (null == type) return;
            switch (type) {
                case boggo -> boggoSort(array);
                case bubble -> bubbleSort(array);
                case insertion -> insertionSort(array);
                case selection -> selectionSort(array);
            }
        }

        /**
         * draws bars visualizing array sort
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int width = getWidth() / array.length; //make every bars have equal width relative to panel width
            int base_line = getHeight() / 2;
            g.setColor(Color.WHITE);
            g.drawLine(0, base_line, getWidth(), base_line); 

            for (int i = 0; i < array.length; ++i) {
                int num = array[i];
                Color color = num > 0 ? Color.ORANGE : Color.CYAN;
                g.setColor(color); 
                int height = Math.abs(num) * visual_factor; //bar height reflects the value of each number in array 
                int xpos = i * width;
                int ypos = num > 0 ? base_line - height : base_line;
                g.fillRect(xpos, ypos, width - 2, height);
            }
        }
    }
}
