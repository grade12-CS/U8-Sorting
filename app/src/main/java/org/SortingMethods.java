package org;

import java.time.Duration;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * a class containing sorting algoritms, extended by SubCanvas class
 */
public class SortingMethods extends JPanel {
    /**
     * enum of sorting algorithm types
     */
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

    //used to return actual total time taken after sorting is done or stopped
    protected  Consumer<Long> onFinish; 

    //tells if an array is sorted
    protected boolean sorted = false;

    //a duration delay for canvas update
    protected Duration delay = Duration.ofNanos(1);

    /**
     * repaint canvas given a duration of milli seconds.
     * sorting method must be called in a separate thread to display sorting in real time.
     */
    protected void refresh() {
        SwingUtilities.invokeLater(() -> repaint());
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * print numbers of array in the terminal
     * @param array array to print its values
     */
    protected void printArray(int[] array) {
        for(int i : array){
            System.out.print(i + " ");
        }
    }

    /**
     * checks if an array is sorted by iterating it
     * @param array array to check
     * @return boolean if an array is sorted or not
     */
    protected boolean isSorted(int[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (i + 1 < array.length && array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    //TODO: use thread pool instead of creating new thread

    /**
     * a sorting method that randomly sorts array until it is sorted
     * in the worst case, the time complexity is infinite
     * @param array array to sort
     */
    protected void boggoSort(int[] array) {
        new Thread(() -> {
            long totalSortTime = 0;
            while (!isSorted(array)) {
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
            sorted = true;
        }).start();
    }

    /**
     * sort an array using bubble sort algoritm
     * @param array array to sort
     */
    protected void bubbleSort(int[] array){
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
            sorted = true;
        }).start();
        //printArray(array);
    }

    /**
     * sort an array using selection sort algoritm
     * @param array array to sort
     */
    protected void selectionSort(int[] array){
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
            sorted = true;
        }).start();
        //printArray(array);
    }

    /**
     * sort an array using insertion sort algoritm
     * @param array array to sort
     */
    protected void insertionSort(int[] array){
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
            sorted = true;
        }).start();
        //printArray(array);
    }
}
