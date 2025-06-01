package org;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeDisplay extends JPanel{
    public final TimeLabel timeLabel;

    public TimeDisplay(String algorithm_name) {
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 50));
        setBackground(Color.BLACK);
        timeLabel = new TimeLabel(algorithm_name);
        add(timeLabel);
    }

    public class TimeLabel extends JPanel {
        private final JLabel title; 
        private final JLabel time;

        public TimeLabel(String algorithm_name) {
            setBackground(Color.BLACK);
            setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            title = new JLabel(algorithm_name + ": ");
            time = new JLabel("time will be displayed here");
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setBounds(10, 10, WIDTH, HEIGHT);
            title.setForeground(new Color(0, 150, 0));
            time.setForeground(new Color(225, 150, 0));
            add(title);
            add(time);
        }

        public void setTime(String time_string) {
            time.setText(time_string);
        }
    }
}
