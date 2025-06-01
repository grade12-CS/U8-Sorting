package org;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class App extends JFrame {
    final Canvas canvas = new Canvas();

    final JPanel timePanel = new JPanel();
    final timeDisplay bubbleTime = new timeDisplay("Bubble Sort");
    final timeDisplay insertionTime = new timeDisplay("Insertion Sort");
    final timeDisplay selectionTime = new timeDisplay("Selection Sort");
    final GridBagConstraints gbc = new GridBagConstraints();
    
    public App() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setTitle("Sorting Visualization");
        getContentPane().setBackground(Color.BLACK);

        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        timePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 50));
        timePanel.setBackground(Color.BLACK);
        bubbleTime.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        insertionTime.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        selectionTime.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        timePanel.add(bubbleTime, gbc);
        timePanel.add(insertionTime, gbc);
        timePanel.add(selectionTime, gbc);

        add(canvas);
        add(timePanel);
        
        pack();
    }

    public String testBuild() {
        return "build is successful";
    }

    public class timeDisplay extends JPanel{
        timeDisplay(String algorithm) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.BLACK);

            timeLabel = new JLabel();
            time = new JLabel();

            timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
            timeLabel.setBounds(10, 10, WIDTH, HEIGHT);
            timeLabel.setText(algorithm + ": ");
            timeLabel.setForeground(new Color(0, 150, 0));
            time.setText("time will be displayed here");
            time.setForeground(new Color(225, 150, 0));

            add(timeLabel);
            add(time);
        }

        private void setTime(String time) {
            this.time.setText(time);
        }

        private JLabel timeLabel;
        private JLabel time;
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.testBuild());
    }
}
