/**********************************************************************************************
 * @file : Proj3.java
 * @description : Demonstrating merge, quick, heap, bubble, and transposition sorting methods
 *                over Animal Crossing: New Horizons villagers.
 * @author : Ella Shipman
 * @date : November 9, 2025
 * @acknowledgement : Jessica Li's "Animal Crossing New Horizons Catalog", "villagers.csv" file.
 * https://www.kaggle.com/datasets/jessicali9530/animal-crossing-new-horizons-nookplaza-dataset.
 *********************************************************************************************/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int size) {
        if (a.isEmpty() || size ==1) {     //return if a is empty or a singleton set
            return;
        }
        mergeSort(a, 0, size-1);       //otherwise, initiate recursive mergeSort call
    }

    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left < right) {                             //ensure that singleton sets aren't processed
            int mid = left + ((right - left) / 2);      //find mid of list
            mergeSort(a, left, mid);                //split left side, assume mid included
            mergeSort(a, mid + 1, right);         //split right side, assume mid is not included
            merge(a, left, mid, right);             //merge sides together
        }
    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        ArrayList<T> temp = new ArrayList<T>();     //temporary list to store sorted elements

        int i = left;       //left starting index
        int j = mid+1;      //right starting index

        while (i <= mid && j<= right) {         //while left pointer is below mid and right pointer is below right
            if (a.get(i).compareTo(a.get(j)) <= 0) {     //left side's element is lesser than/equal to
                temp.add(a.get(i));                      //right side's, add to temp
                i++;    //next element in left side
            } else {                                     //right side's is lesser than, add to temp
                temp.add(a.get(j));
                j++;        //next element in right side
            }
        }

        while (i <= mid) {      //add leftover elements of left side to temp
            temp.add(a.get(i));
            i++;
        }

        while (j <= right) {        //add leftover elements of right side to temp
            temp.add(a.get(j));
            j++;
        }

        int num = left;
        while (!temp.isEmpty() && num <= right) {        //update a with sorted parts
            a.set(num, temp.getFirst());
            temp.removeFirst();
            num++;
        }
    }

    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int size) {
        if (a.isEmpty() || size ==1) {     //return if a is empty or a singleton set
            return;
        }
        quickSort(a, 0, size-1);       //otherwise, initiate recursive quickSort call
    }

    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int partition = partition(a, left, right);   //the partition index
            quickSort(a, left, partition-1);        //recursively sort left of partition
            quickSort(a, partition+1, right);        //recursively sort right of partition
        }
    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        T pivot = a.get(right);      //using the last element as pivot
        int index = (left-1);        //index of the smaller element

        for (int curr = left; curr < right; curr++) {
            if (a.get(curr).compareTo(pivot) < 0) {     //if pivot is larger than curr, swap index and curr
                index++;
                swap(a, index, curr);
            }
        }

        swap(a, index+1, right);        //swap right of index and pivot
        return index+1;                   //return pivot's new index
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a) {
        if (a.isEmpty() || a.size() ==1) {     //return if a is empty or a singleton set
            return;
        }
        heapSort(a, a.size());       //otherwise, initiate recursive heapSort call
    }

    public static <T extends Comparable> void heapSort(ArrayList<T> a, int size) {
        for (int curr = (size/2)-1; curr >= 0; curr--) {     //building a minimum heap
            heapify(a, size, curr);
        }

        for (int rt = size-1; rt >= 0; rt--) {      //remove each element to confirm heap order property
            swap(a, 0, rt);          //move root to end of list
            heapify(a, rt, 0);       //min heapify on sub-heap
        }
    }

    public static <T extends Comparable> void heapify (ArrayList<T> a, int size, int i) {
        int min = i;            //root of the subtree to be heapified
        int left = (2*i)+1;     //left child of min
        int right = (2*i)+2;    //right child of min

        //if index is within subtree size and left is less than min, assign left as new min
        if ((left < size) && (a.get(left).compareTo(a.get(min)) > 0)) {
            min = left;
        }
        //if index is within subtree size and right is less than min, assign right as new min
        if ((right < size) && (a.get(right).compareTo(a.get(min)) > 0)) {
            min = right;
        }

        if (min != i) {             //if the min value isn't the root,
            swap(a, i, min);        //swap root and min
            heapify(a, size, min);  //then recursively heapify the remaining subtree
        }
    }

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        int swaps = 0;              //number of swaps made during sorting
        boolean swapped = false;    //tracks whether items in the list have been swapped

        for (int i = 0; i < size-1; i++) {
            for (int j = 0; j < size-1-i; j++) {
                if (a.get(j).compareTo(a.get(j+1)) > 0) {       //sort in increasing order
                    swap(a, j, j+1);
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) {     //if no elements have been swapped, the list is sorted
                break;
            }
        }
        //RETURN number of swaps
        return swaps;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        // Finish Me
        boolean isSorted = false;       //tracks if list is sorted
        int swaps = 0;                  //number of swaps made throughout sorting

        while (!isSorted) {
            isSorted = true;        //if there are no elements out of order, exits loop after running both for loops
            for (int i = 0; i < size-1; i = i +2) {     //Sort even indices (via, essentially, bubble sort)
                if (a.get(i).compareTo(a.get(i+1)) > 0) {       //if next element is smaller, swap
                    swap(a, i, i+1);
                    swaps++;        //update swaps
                    isSorted = false;
                }
            }
            for (int i = 1; i < size-1; i = i +2) {     //Sort odd indicies (via, essentially, bubble sort)
                if (a.get(i).compareTo(a.get(i+1)) > 0) {       //if next element is smaller, swap
                    swap(a, i, i+1);
                    swaps++;        //update swaps
                    isSorted = false;
                }
            }
        }

        //Return number of swaps
        return swaps;
    }

    public static <T extends Comparable> void printList(ArrayList<T> a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i) + ", ");
        }
        System.out.println();
    }

    public static void main(String [] args)  throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 20; i++) { list.add(i);}
        System.out.print("Starting list: ");
        printList(list);
        System.out.println("Sorted bubble: " + bubbleSort(list, list.size()));
        printList(list);
        System.out.println("Sorted merge: ");
        mergeSort(list, list.size());
        printList(list);
        System.out.println("Sorted transposition: " + transpositionSort(list, list.size()));
        printList(list);
        System.out.println("Sorted quick: ");
        quickSort(list, list.size());
        printList(list);
        System.out.println("Sorted heap: ");
        heapSort(list);
        printList(list);
        Collections.reverse(list);
        System.out.print("Reversed list: ");
        printList(list);
        System.out.println("Reverse bubble: " + bubbleSort(list, list.size()));
        printList(list);
        Collections.reverse(list);
        System.out.println("Reverse merge: ");
        mergeSort(list, list.size());
        printList(list);
        Collections.reverse(list);
        System.out.println("Reverse transposition: " + transpositionSort(list, list.size()));
        printList(list);
        Collections.reverse(list);
        System.out.println("Reverse quick: ");
        quickSort(list, list.size());
        printList(list);
        Collections.reverse(list);
        System.out.println("Reverse heap: ");
        heapSort(list);
        printList(list);
        Collections.shuffle(list);
        System.out.print("Shuffled list: ");
        printList(list);
        System.out.println("Random bubble: " + bubbleSort(list, list.size()));
        printList(list);
        Collections.shuffle(list);
        System.out.println("Random merge: ");
        mergeSort(list, list.size());
        printList(list);
        Collections.shuffle(list);
        System.out.println("Random transposition: " + transpositionSort(list, list.size()));
        printList(list);
        Collections.shuffle(list);
        System.out.println("Random quick: ");
        quickSort(list, list.size());
        printList(list);
        Collections.shuffle(list);
        System.out.println("Random heap: ");
        heapSort(list);
        printList(list);
    }
}
