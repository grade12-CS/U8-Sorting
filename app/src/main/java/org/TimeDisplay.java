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

/**
 * a panel that measures time taken for an algoritm to sort an array and displays the time
 * 
 * actual time: time taken for an algorithm to sort an array without any delay
 * delayed time: time taken for an algorithm to sort an array with delay. 
 * The delay is to better visualize sorting by slowing the canvas down.
 */
public class TimeDisplay extends JPanel{
    private final TimeLabel timeLabel;
    private final Stopwatch sw = new Stopwatch();

    /**
     * sets up display design, and define task to run periodically with stopwatch
     * @param algorithm_name
     * @param unit
     * @param is_sorted
     */
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
            timeLabel.setDelayedTimeText(t + " " + unit.name());
        };

        add(timeLabel);
    }

    /**
     * sets text of label displaying delayed time
     * @param text
     */
    public void setDelayedTimeText(String text) {
        timeLabel.setDelayedTimeText(text);
    }

    /**
     * sets text of label displaying acutal time
     * @param text
     */
    public void setActualTimeText(String text) {
        timeLabel.setActualTimeText(text);
    }

    /**
     * starts timer
     */
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

    /**
     * a stopwatch that starts, stops, resumes, and resets on the user's request
     */
    public class Stopwatch {
        private Instant startTime, endTime;
        private boolean started = false, canceled = false, resetted = false, resumed = false;
        private Timer timer = new Timer();
        private Runnable task;
        private final long delay = 0, period = 1; //runs task every 1 milli seconds, with 0 ms delay before start

        /**
         * starts stopwatch, runs the defined task periodically, in milli seconds
         */
        public void start() {
            if (canceled || task == null) return;
            started = true;
            resetted = false;
            startTime = Instant.now();
            schedule(task);
        }

        /**
         * stops stopwatch, cancelling its timer
         */
        public void stop() {
            if (!started) return; //don't cancel timer if it hasn't started
            endTime = Instant.now();
            timer.cancel();
            canceled = true;
        }

        /**
         * resumes stopwatch by creating a new instance of timer, the same task is run periodically again
         */
        public void resume() {
            if (task == null) return;
            if (resetted) {
                startTime = Instant.now();
            }
            timer = new Timer();
            schedule(task);
        }

        /**
         * resets the boolean logics, and other instances of the stopwatch
         */
        public void reset() {
            resetted = true;
            started = false;
            canceled = false;
            startTime = Instant.now();
            endTime = Instant.now();
            timer = new Timer(); //i don't like this but java timer can't be resumed once canceled
        }

        /**
         * runs a given behaviour at fixed rate
         * @param func function to run (it will be task in stopwatch)
         */
        private void schedule(Runnable func) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    func.run();
                }
                
            }, delay, period);
        }

        /**
         * retreives elapsed time in duration
         * @return
         */
        public Duration getDuration() {
            if (started) {
                return Duration.between(startTime, Instant.now());
            } else {
                return Duration.between(startTime, endTime);
            }
        }

        /**
         * get elapsed time of duration in milli seconds
         * @return
         */
        public long getElapsedTime() {
            return getDuration().toMillis();
        }

        /**
         * get elapsed time by converting the mili seconds into the given time unit
         * @param unit
         * @return
         */
        public long getElapsedTime(TimeUnit unit) {
            return unit.convert(getElapsedTime(), TimeUnit.MILLISECONDS);
        }
    }

    /**
     * a custom-designed label used for TimeDisplay
     */
    public class TimeLabel extends JPanel {
        private final JLabel title; 
        private final JLabel delayedTime, actualTime;

        /**
         * set up design, layout, and define instances
         * @param algorithm_name name of algorithm to be displayed on the label
         */
        public TimeLabel(String algorithm_name) {
            setBackground(Color.BLACK);
            setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            title = new JLabel(algorithm_name + ": ");
            delayedTime = new JLabel("0");
            actualTime = new JLabel("0");
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setBounds(10, 10, WIDTH, HEIGHT);
            title.setForeground(new Color(0, 150, 0));
            delayedTime.setForeground(new Color(225, 150, 0));
            actualTime.setForeground(new Color(225, 150, 0));
            add(title);
            add(delayedTime);
            add(actualTime);
        }

        /**
         * sets text for the delayed time label
         * @param text text to set
         */
        public void setDelayedTimeText(String text) {
            delayedTime.setText(text);
        }

        /**
         * sets text for the actual time label
         * @param text text to set
         */
        public void setActualTimeText(String text) {
            actualTime.setText(text);
        }
    }
}
