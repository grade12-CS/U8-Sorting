package org;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeDisplay extends JPanel{
    final TimeLabel bubbleTime = new TimeLabel("Bubble Sort");
    final TimeLabel insertionTime = new TimeLabel("Insertion Sort");
    final TimeLabel selectionTime = new TimeLabel("Selection Sort");

    public TimeDisplay() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 50));
        setBackground(Color.BLACK);
        add(bubbleTime);
        add(insertionTime); 
        add(selectionTime);
    }

    public void setTime(String time) {
        bubbleTime.setTime(time);
        insertionTime.setTime(time);
        selectionTime.setTime(time);
    }

    public class TimeLabel extends JPanel {
        private final JLabel title; 
        private final JLabel time;

        public TimeLabel(String algorithm) {
            title = new JLabel(algorithm + ": ");
            time = new JLabel("time will be displayed here");
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setBounds(10, 10, WIDTH, HEIGHT);
            title.setForeground(new Color(0, 150, 0));
            time.setForeground(new Color(225, 150, 0));
            setBackground(Color.BLACK);
            setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(title);
            add(time);
        }

        public void setTime(String time_string) {
            time.setText(time_string);
        }
    }
}
