/**********************************************************************************************
 * @file : Proj3.java
 * @description : Demonstrating merge, quick, heap, bubble, and transposition sorting methods
 *                over Animal Crossing: New Horizons villagers.
 * @author : Ella Shipman
 * @date : November 13, 2025
 * @acknowledgement : Jessica Li's "Animal Crossing New Horizons Catalog", "villagers.csv" file.
 * https://www.kaggle.com/datasets/jessicali9530/animal-crossing-new-horizons-nookplaza-dataset.
 *********************************************************************************************/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Scanner;

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
            int pivot = medianOfThree(a, left, right);   //using the median as pivot index (at right-1 position)
            int partition = partition(a, left, right);   //the partition index
            quickSort(a, left, partition-1);        //recursively sort left of partition
            quickSort(a, partition+1, right);        //recursively sort right of partition
        }
    }

    //reference: 7-3.pdf class notes on Canvas
    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        T pivot = a.get(right-1);                          //current place of the pivot
        int index = (left);                                //start of list

        for (int curr = left; curr < right-1; curr++) {
            if (a.get(curr).compareTo(pivot) < 0) {     //if pivot is larger than curr, swap index and curr
                swap(a, index, curr);
                index++;
            }
        }

        swap(a, index, right-1);        //swap right of index (index++ from for loop) and pivot
        return index;                     //return pivot's new position
    }

    //reference: 7-3.pdf class notes on Canvas
    public static <T extends Comparable> int medianOfThree(ArrayList<T> a, int left, int right) {
        int mid = left + ((right-left)/2);

        if (a.get(left).compareTo(a.get(mid)) > 0) {        //if left larger then mid, swap
            swap(a, left, mid);
        }
        if (a.get(left).compareTo(a.get(right)) > 0) {      //if left larger than right, swap
            swap(a, left, right);
        }
        if (a.get(mid).compareTo(a.get(right)) > 0) {       //if mid larger than right, swap
            swap(a, mid, right);
        }
        //Place the new median (currently at mid) at right-1 for partitioning
        swap(a, mid, (right-1));
        return (right-1);
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

    // Bubble Sort, returns number of swaps performed
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

    // Odd-Even Transposition Sort, returns number of swaps performed
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        // Finish Me
        boolean isSorted = false;       //tracks if list is sorted
        int swaps = 0;                  //number of swaps made throughout sorting

        while (!isSorted) {
            isSorted = true;        //if there are no elements out of order, exits loop after running both for loops
            for (int i = 0; i < size-1; i = i + 2) {     //Sort even indices (via, essentially, bubble sort)
                if (a.get(i).compareTo(a.get(i+1)) > 0) {       //if next element is smaller, swap
                    swap(a, i, i+1);
                    swaps++;        //update swaps
                    isSorted = false;
                }
            }
            for (int i = 1; i < size-1; i = i + 2) {     //Sort odd indicies (via, essentially, bubble sort)
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

    //Swaps elements in arraylist a
    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    //Prints list to the console
    public static <T extends Comparable> void printList(ArrayList<T> a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i) + ", ");
        }
        System.out.println();
    }

    //Prints list in a file given a filewriter
    public static <T extends Comparable> void printListInFile(FileWriter writer, ArrayList<T> a) {
        try {
            for (int i = 0; i < a.size(); i++) {
                writer.write(a.get(i).toString() + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Uh-oh! File not found in java Proj3, printListInFile!");
        }
    }


    //Main method
    public static void main(String [] args)  throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 3) {
            System.err.println("Usage: java Proj3 <input file> <sorting method> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];             //name of input/data file
        String sortingMethod = args[1];             //sorting method
        int numLines = Integer.parseInt(args[2]);   //number of lines to be processed

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        //PROCESS FILE----------------------------------------------------
        //Read file and fill out arraylist of datatype Villager
        ArrayList<Villager> villagers = new ArrayList<>();
        String currLine = null;
        for (int i=2; i <= numLines; i++) {
            currLine = inputFileNameScanner.nextLine();     //get next line in file
            String[] villInfo = null;
            if (!currLine.isEmpty()) {
                villInfo = currLine.split(",");
            }
            String[] shortInfo = new String[4];
            shortInfo[0] = villInfo[0];     //name
            shortInfo[1] = villInfo[3];     //personality
            shortInfo[2] = villInfo[4];     //hobby
            shortInfo[3] = villInfo[7];     //favorite song

            Villager v = null;
            try {
                v = new Villager(shortInfo[0], shortInfo[1], shortInfo[2], shortInfo[3]);
            } catch (ArrayIndexOutOfBoundsException e) {}

            if (shortInfo[1] != null) {         //add object to villagers list
                villagers.add(v);
            } else {
                System.out.println("insert failed - line " + i);
            }
        }

        //SORTING METHOD---------------------------------------------------

        System.out.print("-------------------------------------------------------------------------------------------\n");
        //Sorting methods used
        sortingMethod = sortingMethod.toLowerCase();
        String sortedData = "";                               //sorted data metrics for given sorting method
        String reversedData = "";                             //reversed data metrics for given sorting method
        String shuffledData = "";                             //shuffled data metrics for given sorting method
        long startTime;                                  //start time for timed sortings (not bubble or odd-even)
        long endTime;                                    //end time for timed sortings (not bubble or odd-even)

        //Write sorted list to file-------------------------------------
        FileWriter sortedWriter = null;
        try {
            sortedWriter = new FileWriter("src/sorted.txt");
        }
        catch (IOException e) { System.out.println("FileNotFound!"); }
        if (sortingMethod.contains("bubble")) {     //Case: bubble sort
            sortingMethod = "Bubble sort";
            //sorted
            Collections.sort(villagers);
            startTime = System.nanoTime();
            sortedData += bubbleSort(villagers, villagers.size());
            endTime = System.nanoTime();
            sortedData += (";" + (endTime-startTime));
            printListInFile(sortedWriter, villagers);
            //reversed
            Collections.sort(villagers, Collections.reverseOrder());
            startTime = System.nanoTime();
            reversedData += bubbleSort(villagers, villagers.size());
            endTime = System.nanoTime();
            reversedData += (";" + (endTime-startTime));
            printListInFile(sortedWriter, villagers);
            //shuffled
            Collections.shuffle(villagers);
            startTime = System.nanoTime();
            shuffledData += bubbleSort(villagers, villagers.size());
            endTime = System.nanoTime();
            shuffledData += (";" + (endTime-startTime));
            printListInFile(sortedWriter, villagers);
        } else if (sortingMethod.contains("merge")) {       //Case: merge sort
            sortingMethod = "Merge sort";
            //sorted
            Collections.sort(villagers);
            startTime = System.nanoTime(); mergeSort(villagers, villagers.size()); endTime = System.nanoTime();
            sortedData += endTime - startTime;
            printListInFile(sortedWriter, villagers);
            //reversed
            Collections.sort(villagers, Collections.reverseOrder());
            startTime = System.nanoTime(); mergeSort(villagers, villagers.size()); endTime = System.nanoTime();
            reversedData += endTime - startTime;
            printListInFile(sortedWriter, villagers);
            //shuffled
            Collections.shuffle(villagers);
            startTime = System.nanoTime(); mergeSort(villagers, villagers.size()); endTime = System.nanoTime();
            shuffledData += endTime - startTime;
            printListInFile(sortedWriter, villagers);
        } else if (sortingMethod.contains("quick")) {       //Case: quick sort
            sortingMethod = "Quick sort";
            //sorted
            Collections.sort(villagers);
            startTime = System.nanoTime(); quickSort(villagers, villagers.size()); endTime = System.nanoTime();
            sortedData += endTime - startTime;
            printListInFile(sortedWriter, villagers);
            //reversed
            Collections.sort(villagers, Collections.reverseOrder());
            startTime = System.nanoTime(); quickSort(villagers, villagers.size()); endTime = System.nanoTime();
            reversedData += endTime - startTime;
            printListInFile(sortedWriter, villagers);
            //shuffled
            Collections.shuffle(villagers);
            startTime = System.nanoTime(); quickSort(villagers, villagers.size()); endTime = System.nanoTime();
            shuffledData += endTime - startTime;
            printListInFile(sortedWriter, villagers);
        } else if (sortingMethod.contains("heap")) {
            sortingMethod = "Heap sort";
            //sorted
            Collections.sort(villagers);
            startTime = System.nanoTime(); heapSort(villagers); endTime = System.nanoTime();
            sortedData += endTime - startTime;
            printListInFile(sortedWriter, villagers);
            //reversed
            Collections.sort(villagers, Collections.reverseOrder());
            startTime = System.nanoTime(); heapSort(villagers); endTime = System.nanoTime();
            reversedData += endTime - startTime;
            printListInFile(sortedWriter, villagers);
            //shuffled
            Collections.shuffle(villagers);
            startTime = System.nanoTime(); heapSort(villagers); endTime = System.nanoTime();
            shuffledData += endTime - startTime;
            printListInFile(sortedWriter, villagers);
        }  else if (sortingMethod.contains("transposition") ||      //Case: transposition/odd-even sort
                (sortingMethod.contains("odd") && sortingMethod.contains("even"))) {
            sortingMethod = "Transposition sort";
            //sorted
            Collections.sort(villagers);
            sortedData += transpositionSort(villagers, villagers.size());
            printListInFile(sortedWriter, villagers);
            //reversed
            Collections.sort(villagers, Collections.reverseOrder());
            reversedData += transpositionSort(villagers, villagers.size());
            printListInFile(sortedWriter, villagers);
            //shuffled
            Collections.shuffle(villagers);
            shuffledData += transpositionSort(villagers, villagers.size());
            printListInFile(sortedWriter, villagers);
        } else {        //Case: unrecognized method
            System.out.println("Opps! The sorting method you entered is not recognized.");
            System.out.print("Please choose: \n - merge sort \n - quick sort \n - heap sort \n - bubble sort \n - transposition sort \n");
            sortedWriter.close();
            System.exit(1);
        }
        System.out.print("Number of lines read from dataset: " + numLines + "/392\n");
        System.out.println("Sorting method:   " + sortingMethod);
        System.out.println("Sorted dataset:   " + sortedData);
        System.out.println("Reversed dataset: " + reversedData);
        System.out.println("Shuffled dataset: " + shuffledData);

        System.out.print("-------------------------------------------------------------------------------------------\n");

        //Close out sorted.txt writer
        sortedWriter.flush();
        sortedWriter.close();

        //Write results in analysis.txt--------------------------------------------
        FileWriter analysisWriter = null;
        try {       //assume output.txt will be manually reset
            analysisWriter = new FileWriter("src/analysis.txt", true);
        }
        catch (IOException e) { System.out.println("FileNotFound!"); }

        //Append a line to analysis.txt with the information above, in CSV format
        //analysisWriter.write("Sorting Method,Number of Lines,Sorted Data Output,Reversed Data Output,Shuffled Data Output\n");
        analysisWriter.write(sortingMethod + "," + numLines + "," +
                sortedData + "," + reversedData + "," + shuffledData + "\n");
        analysisWriter.flush();
        analysisWriter.close();
    }
}
