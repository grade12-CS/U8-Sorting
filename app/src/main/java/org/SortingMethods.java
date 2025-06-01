package org;

import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SortingMethods extends JPanel {
    public enum sorting_type {
        boggo("Boggo Sort"), 
        insertion("Insertion Sort"), 
        bubble("Bubble Sort"), 
        selection("Selection Sort");
        public final String name;
        private sorting_type(String name) {
            this.name = name;
        }
    };

    protected long updateDuration = 1; //redraws canvas every 1 milli second

    /**
     * repaint canvas given a duration of milli seconds.
     * sorting method must be called in a separate thread to display sorting in real time.
     */
    protected void refresh() {
        SwingUtilities.invokeLater(() -> repaint());
        try {
            Thread.sleep(updateDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void printArray(int[] array) {
        for(int i : array){
            System.out.print(i + " ");
        }
    }

    protected boolean isShuffled(int[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (i < array.length + 1 && array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    //TODO: use thread pool instead of creating new thread

    //infinite time complexity in the worst case lol
    protected void boggoSort(int[] array, Consumer<Long> onFinish) {
        new Thread(() -> {
            long totalSortTime = 0;
            while (!isShuffled(array)) {
                for (int i = 0; i < array.length; ++i) {
                    long start = System.nanoTime();
                    if(MultiCanvas.stopRequested) {
                        if (onFinish != null) onFinish.accept(totalSortTime);
                        return;
                    }
                    int r_index = (int) (Math.random() * i);
                    int temp = array[i];
                    array[i] = array[r_index];
                    array[r_index] = temp;
                    totalSortTime += System.nanoTime() - start;
                    refresh();
                }
            }
            if (onFinish != null) onFinish.accept(totalSortTime);
        }).start();
    }

    protected void bubbleSort(int[] array, Consumer<Long> onFinish){
        new Thread(() -> {
            long totalSortTime = 0;
            boolean swapped = true;
            while(swapped == true){
                swapped = false;
                for(int i = 1; i < array.length; i++){
                    long start = System.nanoTime();
                    if(MultiCanvas.stopRequested) {
                        if (onFinish != null) onFinish.accept(totalSortTime);
                        return;
                    }
                    if(array[i-1]>array[i]){
                        int tempNum = array[i-1];
                        array[i-1] = array[i];
                        array[i] = tempNum;
                        totalSortTime += System.nanoTime() - start;
                        refresh();
                        swapped = true;
                    }
                }
            }
            if (onFinish != null) onFinish.accept(totalSortTime);
        }).start();
        //printArray(array);
    }

    protected void selectionSort(int[] array, Consumer<Long> onFinish){
        new Thread(() -> {
            long totalSortTime = 0;
            for(int i = 0; i < array.length - 1; i++){
                long start = System.nanoTime();
                if(MultiCanvas.stopRequested) {
                    if (onFinish != null) onFinish.accept(totalSortTime);
                    return;
                }
                int minIndex = i;
                for(int j = i + 1; j < array.length; j++){
                    if(array[j] < array[minIndex]){
                        minIndex = j;
                    }
                }
                int minValue = array[minIndex];
                array[minIndex] = array[i];
                array[i] = minValue;
                totalSortTime += System.nanoTime() - start;
                refresh();
            }
            if (onFinish != null) onFinish.accept(totalSortTime);
        }).start();
        //printArray(array);
    }

    protected void insertionSort(int[] array, Consumer<Long> onFinish){
        new Thread(() -> {
            long totalSortTime = 0;
            for(int i = 0; i < array.length - 1; i++){
                int j = i + 1;
                while(j > 0 && array[j] < array[j - 1]){
                    long start = System.nanoTime();
                    if(MultiCanvas.stopRequested) {
                        if (onFinish != null) onFinish.accept(totalSortTime);
                        return;
                    }
                    int num = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = num;
                    j--;
                    totalSortTime += System.nanoTime() - start;
                    refresh();
                }
            }
            if (onFinish != null) onFinish.accept(totalSortTime);
        }).start();
        //printArray(array);
    }

}
