package org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
        final int visual_factor = 3; 

        public final sorting_type type;

        public SubCanvas(int[] array, sorting_type type) {
            this.array = array.clone();
            this.type = type;
            setBackground(Color.BLACK);
        }

        /*public void getTime(){
            //TODO: take into consideration the time spent during a stop?? if we have time
            Consumer<Long> onFinish = (duration) -> {
                SwingUtilities.invokeLater(() -> {
                    timeDisplay.timeLabel.setTime("Sorting finished in " + duration/100000.000 + " ms");
                });
            };

            if (null != type) switch (type) {
                case boggo -> boggoSort(array, onFinish);
                case bubble -> bubbleSort(array, onFinish);
                case insertion -> insertionSort(array, onFinish);
                case selection -> selectionSort(array, onFinish);
            }
        }*/
        public void getTime(){
            Consumer<Long> onFinish = null;
            if (null != type) switch (type) {
                case boggo -> boggoSort(array, onFinish);
                case bubble -> bubbleSort(array, onFinish);
                case insertion -> insertionSort(array, onFinish);
                case selection -> selectionSort(array, onFinish);
            }
            timeDisplay.timeLabel.setTime(getElapsedTime(array, type)/1000000.0 + "ms");
        }

        public void setArray(int[] newArray) {
            System.arraycopy(newArray, 0, this.array, 0, this.array.length);
            repaint();
            timeDisplay.timeLabel.setTime("Array updated");
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
}
