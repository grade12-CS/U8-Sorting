package main.java.org;

public class SortingMethods {

    public static void bubbleSort(int[] array){
        boolean swapped = true;
        while(swapped == true){
            swapped = false;
            for(int i = 1; i < array.length; i++){
                if(array[i-1]>array[i]){
                    int tempNum = array[i-1];
                    array[i-1] = array[i];
                    array[i] = tempNum;
                    swapped = true;
                }
            }
        }
        for(int i : array){
            System.out.print(i + " ");
        }
    }

    public static void selectionSort(int[] array){
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
        }
        for(int i : array){
            System.out.print(i + " ");
        }
    }

    public static void insertionSort(int[] array){
        for(int i = 0; i < array.length - 1; i++){
            int j = i + 1;
            while(j > 0 && array[j] < array[j-1]){
                int num = array[j - 1];
                array[j - 1] = array[j];
                array[j] = num;
                j--;
            }
        }
        for(int i : array){
            System.out.print(i + " ");
        }
    }

}
