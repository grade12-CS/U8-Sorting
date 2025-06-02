package org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.SortingMethods.sorting_type;

public class Canvas extends JPanel {
    private final TimeDisplay timeDisplay; 
    private final SubCanvas canvas;

    public Canvas(int[] array, sorting_type type) {
        timeDisplay = new TimeDisplay(type.name);    
        canvas = new SubCanvas(array, type);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(timeDisplay, BorderLayout.EAST);
    }

    public void setArray(int[] newArray) {
        canvas.setArray(newArray);
    }

    public void startSort() {
        canvas.getTime();
    }

    public class SubCanvas extends SortingMethods {
        final int array[];
        final int visual_factor = 1;

        public final sorting_type type;

        public SubCanvas(int[] array, sorting_type type) {
            this.array = array.clone();
            this.type = type;
            setBackground(Color.BLACK);
        }

        public void getTime(){
            long startTime = System.nanoTime();
            sort();
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            timeDisplay.timeLabel.setTime(duration + " ns");
        }

        public void setArray(int[] newArray) {
            System.arraycopy(newArray, 0, this.array, 0, this.array.length);
            repaint();
            timeDisplay.timeLabel.setTime("Array updated");
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
            int base_line = getHeight() / 2;
            g.setColor(Color.WHITE);
            g.drawLine(0, base_line, getWidth(), base_line); 

            for (int i = 0; i < array.length; ++i) {
                /* for each bar
                * x pos: i * width + 2, add 2 for tiny padding
                * y pos: panel height - bar height --> ensures bars to be drawn at the bottom
                * bar width: width - 2 -> 2 is for gap between bars
                * bar height: value * visual factor
                */
                int num = array[i];
                Color color = num > 0 ? Color.ORANGE : Color.CYAN;
                g.setColor(color); //change color for fun
                int height = Math.abs(num) * visual_factor; //bar height reflects the value of each number in array 
                int xpos = i * width;
                int ypos = num > 0 ? base_line - height : base_line;
                g.fillRect(xpos, ypos, width - 2, height);
            }
        }
    }
}
