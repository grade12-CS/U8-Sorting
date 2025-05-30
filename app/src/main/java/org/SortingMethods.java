package org;

import java.util.Random;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SortingMethods extends JPanel {
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

    protected int[] generateArray(int size, int max, int min) {
        int result[] = new int[size];
        Random r = new Random();
        for (int i = 0; i < size; ++i) {
            int num = r.nextInt(max) + min;
            result[i] = num;
        }
        return result;
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
    protected void boggoSort(int[] array) {
        new Thread(() -> {
            while (!isShuffled(array)) {
                for (int i = 0; i < array.length; ++i) {
                    int r_index = (int) (Math.random() * i);
                    int temp = array[i];
                    array[i] = array[r_index];
                    array[r_index] = temp;
                    refresh();
                }
            }
        }).start();
    }

    protected void bubbleSort(int[] array){
        new Thread(() -> {
            boolean swapped = true;
            while(swapped == true){
                swapped = false;
                for(int i = 1; i < array.length; i++){
                    if(array[i-1]>array[i]){
                        int tempNum = array[i-1];
                        array[i-1] = array[i];
                        array[i] = tempNum;
                        refresh();
                        swapped = true;
                    }
                }
            }
        }).start();
        printArray(array);
    }

    protected void selectionSort(int[] array){
        new Thread(() -> {
            for(int i = 0; i < array.length - 1; i++){
                int minIndex = i;
                for(int j = i + 1; j < array.length; j++){
                    if(array[j] < array[minIndex]){
                        minIndex = j;
                    }
                }
                int minValue = array[minIndex];
                array[minIndex] = array[i];
                array[i] = minValue;
                refresh();
            }
        }).start();
        printArray(array);
    }

    protected void insertionSort(int[] array){
        new Thread(() -> {
            for(int i = 0; i < array.length - 1; i++){
                int j = i + 1;
                while(j > 0 && array[j] < array[j - 1]){
                    int num = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = num;
                    j--;
                    refresh();
                }
            }
        }).start();
        printArray(array);
    }

}
