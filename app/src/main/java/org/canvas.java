package org;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public final class canvas extends JPanel {
    final int array[];
    final int visual_factor = 10; 
    final int min, max, arr_size, canvas_width, canvas_height;

    public canvas() {
        max = 50;
        min = 10;
        arr_size = 200;
        canvas_width = 1920; 
        canvas_height = 1080;
        array = generate_array(arr_size, max, min);
        setBackground(Color.BLACK);
        setSize(new Dimension(canvas_width, canvas_height));
        var btn_sort = new JButton("Sort");
        btn_sort.addActionListener((ActionEvent e) -> {
            boggo_sort(array);
        });
        add(btn_sort);
    }

    int[] generate_array(int size, int max, int min) {
        int result[] = new int[size];
        Random r = new Random();
        for (int i = 0; i < size; ++i) {
            int num = r.nextInt(max) + min;
            result[i] = num;
        }
        return result;
    }

    boolean is_shuffled(int[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (i < array.length + 1 && array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    //infinite time complexity in the worst case lol
    void boggo_sort(int[] array) {
        new Thread(() -> {
            while (!is_shuffled(array)) {
                for (int i = 0; i < array.length; ++i) {
                    int r_index = (int) (Math.random() * i);
                    int temp = array[i];
                    array[i] = array[r_index];
                    array[r_index] = temp;
                    SwingUtilities.invokeLater(() -> repaint());
                    try {
                        Thread.sleep((long)1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
