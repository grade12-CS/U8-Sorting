package org;

import java.awt.Color;
import java.awt.Font;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
    private final TimeLabel timeLabel;
    private final Stopwatch sw = new Stopwatch();

    //TODO: fix 0 nanosecond
    public TimeDisplay(String algorithm_name, TimeUnit unit, Supplier<Boolean> is_sorted) {
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 50));
        setBackground(Color.BLACK);
        timeLabel = new TimeLabel(algorithm_name);

        sw.task = () -> {
            if (is_sorted.get() || MultiCanvas.stopRequested || MultiCanvas.refreshRequested)  {
                sw.stop();
                if (MultiCanvas.refreshRequested) {
                    sw.reset();
                }   
            }
            long t = sw.getElapsedTime(unit);
            //TODO: display up to 3 decimal places
            DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
            timeLabel.setText(t + " " + unit.name());
        };

        add(timeLabel);
    }

    public void setLabel(String text) {
        timeLabel.setText(text);
    }

    public void startMeasuring() {  
        if (MultiCanvas.refreshRequested) {
            sw.reset();
        }
        if (MultiCanvas.resumeRequested) {
            sw.resume();
            return;
        }
        sw.start();
    }

    public class Stopwatch {
        private Instant startTime, endTime;
        private boolean started = false, canceled = false, resetted = false, resumed = false;
        private Timer timer = new Timer();
        private Runnable task;
        private final long delay = 0, period = 1; //runs task every 1 milli seconds, with 0 ms delay before start

        public void start() {
            if (canceled || task == null) return;
            started = true;
            resetted = false;
            startTime = Instant.now();
            schedule(task);
        }

        public void stop() {
            if (!started) return; //don't cancel timer if it hasn't started
            endTime = Instant.now();
            timer.cancel();
            canceled = true;
        }

        public void resume() {
            if (task == null) return;
            if (resetted) {
                startTime = Instant.now();
            }
            timer = new Timer();
            schedule(task);
        }

        public void reset() {
            resetted = true;
            started = false;
            canceled = false;
            startTime = Instant.now();
            endTime = Instant.now();
            timer = new Timer(); //i don't like this but java timer can't be resumed once canceled
        }

        private void schedule(Runnable func) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    func.run();
                }
                
            }, delay, period);
        }

        public Duration getDuration() {
            if (started) {
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
