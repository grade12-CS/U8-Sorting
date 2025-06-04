package org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.SortingMethods.sorting_type;

public class MultiCanvas extends JPanel {
    JRadioButton insertion, bubble, selection, boggo;
    JButton btn_sort, btn_refresh, btn_stop;
    final int min, max, array_size;
    int[] array;
    final Canvas[] canvases = new Canvas[4];
    volatile static boolean stopRequested = false, refreshRequested = false, resumeRequested = false;

    private final double array_size_to_screen_ratio = 0.2604167; //ratio obtained from my laptop screen

    public MultiCanvas() {
        max = 200;
        min = -200;
        array_size = (int) (getScreenSize().width * array_size_to_screen_ratio);
        array = generateArray(array_size, max, min);
        setLayout(new BorderLayout());
        setPreferredSize(getScreenSize());
        add(groupButton(), BorderLayout.PAGE_START);
        add(groupCanvas(), BorderLayout.CENTER);
    }
    
    private JPanel groupCanvas() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setSize(getSize().width, (int)(getSize().height * (7/8)));
        p.setBackground(Color.BLACK);

        canvases[0] = new Canvas(array, sorting_type.boggo);
        canvases[1] = new Canvas(array, sorting_type.bubble);
        canvases[2] = new Canvas(array, sorting_type.insertion);
        canvases[3] = new Canvas(array, sorting_type.selection);

        canvases[0].setVisible(false);
        canvases[1].setVisible(false);
        canvases[2].setVisible(false);
        canvases[3].setVisible(false);
        
        p.add(canvases[0]);
        p.add(canvases[1]);
        p.add(canvases[2]);
        p.add(canvases[3]);
        
        final ActionListener al = (ActionEvent e) -> {
            if (e.getSource().getClass() != JRadioButton.class) return;
            JRadioButton btn = (JRadioButton) e.getSource();
            if (btn == boggo) {
                canvases[0].setVisible(btn.isSelected());
            } else if (btn == bubble) {
                canvases[1].setVisible(btn.isSelected());
            } else if (btn == insertion) {
                canvases[2].setVisible(btn.isSelected());
            } else if (btn == selection) {
                canvases[3].setVisible(btn.isSelected());
            }
            p.revalidate();
        };
        
        insertion.addActionListener(al);
        bubble.addActionListener(al);
        selection.addActionListener(al);
        boggo.addActionListener(al);
        return p;
    }
    
    private int[] generateArray(int size, int max, int min) {
        int result[] = new int[size];
        Random r = new Random();
        for (int i = 0; i < size; ++i) {
            int num = r.nextInt(max - min + 1) + min;
            result[i] = num;
        }
        return result;
    }
    
    private JPanel groupButton() {
        JPanel p = new JPanel();
        p.setSize(getScreenSize().width, (int) (getScreenSize().height * (1/8)));
        p.setBorder(BorderFactory.createLineBorder(Color.gray));
        p.setForeground(Color.white);
        insertion = new JRadioButton(sorting_type.insertion.name);
        bubble = new JRadioButton(sorting_type.bubble.name);
        selection = new JRadioButton(sorting_type.selection.name);
        boggo = new JRadioButton(sorting_type.boggo.name);

        btn_refresh = new JButton("Refresh");
        btn_stop = new JButton("Stop");
        btn_sort = new JButton("Sort");

        btn_refresh.addActionListener((ActionEvent e) -> {
            refreshRequested = true;
            stopRequested = true;
            array = generateArray(array_size, max, min);
            for (Canvas c : canvases) { 
                c.setArray(array);
                c.setSorted(false); //i don't like this
            }
        });
        btn_stop.addActionListener((ActionEvent e) -> {
            stopRequested = true;
            refreshRequested = false;
        });
        btn_sort.addActionListener((ActionEvent e) -> {
            if (stopRequested && !refreshRequested) {
                resumeRequested = true;
            } 
            for (Canvas c : canvases) {
                if (c.isVisible()) {
                    c.startSort();
                }
            }
            stopRequested = false;
            refreshRequested = false;
            resumeRequested = false;
        });
        p.add(insertion);
        p.add(bubble);
        p.add(selection);
        p.add(boggo);
        p.add(btn_sort);
        p.add(btn_stop);
        p.add(btn_refresh);
        return p;
    }

    private Dimension getScreenSize() {
        int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        return new Dimension(width, height);
    }
}
