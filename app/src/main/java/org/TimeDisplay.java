package org;

import java.awt.Color;
import java.awt.Font;
import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeDisplay extends JPanel{
    public final TimeLabel timeLabel;
    private final Stopwatch sw = new Stopwatch();

    public TimeDisplay(String algorithm_name) {
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 50));
        setBackground(Color.BLACK);
        timeLabel = new TimeLabel(algorithm_name);
        add(timeLabel);
    }
    
    public void startMeasuring(TimeUnit unit, Supplier<Boolean> when_to_stop) {  
        if (MultiCanvas.refreshRequested) {
            sw.reset();
        }
        if (MultiCanvas.resumeRequested) {
            sw.resume();
            return;
        }
        sw.start(() -> {
            if (when_to_stop.get() || MultiCanvas.stopRequested || MultiCanvas.refreshRequested)  {
                sw.stop();
                if (MultiCanvas.refreshRequested) {
                    sw.reset();
                }   
            }
            long t = sw.getElapsedTime(unit);
            timeLabel.setText(String.valueOf(t) + " " + unit.name());
        });
    }

    public class Stopwatch {
        private Instant startTime, endTime;
        private boolean running = false;
        private Timer timer = new Timer();
        private Runnable task;
        private final long delay = 0, period = 1; //runs task every 1 milli seconds, with 0 ms delay before start

        public void start(Runnable task) {
            this.task = task;
            running = true;
            startTime = Instant.now();
            schedule(task);
        }

        public void stop() {
            endTime = Instant.now();
            timer.cancel();
            running = false;
        }

        public void resume() {
            timer = new Timer();
            running = true;
            schedule(task);
        }

        public void reset() {
            running = false;
            startTime = Instant.now();
            endTime = Instant.now();
            timer = new Timer(); //i don't like this but java timer can't be resumed once canceled
        }

        private void schedule(Runnable func) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    func.run();
                }
            }, delay, period);
        }

        public Duration getDuration() {
            if (running) {
                return Duration.between(startTime, Instant.now());
            } else {
                return Duration.between(startTime, endTime);
            }
        }

        public long getElapsedTime() {
            return getDuration().toMillis();
        }

        public long getElapsedTime(TimeUnit unit) {
            return unit.convert(getElapsedTime(), TimeUnit.MILLISECONDS);
        }
    }

    public class TimeLabel extends JPanel {
        private final JLabel title; 
        private final JLabel time;

        public TimeLabel(String algorithm_name) {
            setBackground(Color.BLACK);
            setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            title = new JLabel(algorithm_name + ": ");
            time = new JLabel("00:00:00");
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setBounds(10, 10, WIDTH, HEIGHT);
            title.setForeground(new Color(0, 150, 0));
            time.setForeground(new Color(225, 150, 0));
            add(title);
            add(time);
        }

        public void setText(String text) {
            time.setText(text);
        }
    }
}
