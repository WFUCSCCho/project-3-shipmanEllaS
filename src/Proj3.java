import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        if (a.isEmpty()) {     //return if a is empty
            return;
        }
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
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        // Finish Me
    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        // Finish Me
        //return number of swaps
        return 0;
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        // Finish Me
    }

    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {
        // Finish Me
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
        //Return number of swaps
        return 0;
    }

    public static <T extends Comparable> void printList(ArrayList<T> a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i) + ", ");
        }
        System.out.println();
    }

    public static void main(String [] args)  throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        System.out.print("Starting list: ");
        printList(list);
        for (int i = 0; i <= 20; i++) { list.add(i);}
        System.out.println("Sorted bubble: " + bubbleSort(list, list.size()));
        printList(list);
        System.out.println("Sorted merge: ");
        mergeSort(list, 0, list.size()-1);
        printList(list);
        Collections.reverse(list);
        System.out.print("Reversed list: ");
        printList(list);
        System.out.println("Reverse bubble: " + bubbleSort(list, list.size()));
        printList(list);
        System.out.println("Reverse merge: ");
        mergeSort(list, 0, list.size()-1);
        printList(list);
        Collections.shuffle(list);
        System.out.print("Shuffled list: ");
        printList(list);
        System.out.println("Random bubble: " + bubbleSort(list, list.size()));
        printList(list);
        System.out.println("Random merge: ");
        mergeSort(list, 0, list.size()-1);
        printList(list);
    }
}
