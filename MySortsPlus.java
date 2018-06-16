/*
__                     ________    _      _
/ /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
__  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, December 15, 2017
*/

import java.util.ArrayList;

public class MySortsPlus {

  /*************
  * Utilities *
  *************/

  //Pre-cond: data.size() >= 1
  public static int max(ArrayList<Integer> data) {

    int max = data.get(0);
    for (int i = 1; i < data.size(); i++)
    if (max < data.get(i)) max = data.get(i);
    return max;

  }

  public static boolean isSorted(ArrayList<Comparable> data) {

    for (int i = 0; i < data.size() - 1; i++)
    if (data.get(i).compareTo(data.get(i + 1)) > 0) return false;
    return true;

  }

  //returns the elements of data in range [min, max)
  public static ArrayList<Comparable> getSublist(ArrayList<Comparable> data, int min, int max) {
    ArrayList<Comparable> ret = new ArrayList<Comparable>();
    for (int i = min; i < max; i++) {
      ret.add(data.get(i));
    }
    return ret;
  }//end splitAL


  /**********
  * Best Case for bubbleSort: An AL in ascending order
  *    Why? Because the way we have it written, it has a constant number
  *    of comparisons (if AL size is constant). However, it only swaps if
  *    the element is in the wrong place. It would be better to tell the
  *    method to stop running once it passes without any swaps
  * Worst Case for bubbleSort: An AL in descending order
  *    Why? As previously stated, bubbleSort will do the same number of
  *    comparisons no matter the order. However, it will do the greatest
  *    number of swaps when the AL is in descending order. (I think it's
  *    about 0.5n^2 - 0.5n swaps . I did some trials and graphing stuff)
  **********/

  // NOTE: INCLUDES EXIT CASE
  // Rearranges elements of input ArrayList
  // postcondition: data's elements sorted in ascending order
  // Best case time: O(n)
  // Worst case time: O(n^2)
  public static void bubbleSort( ArrayList<Comparable> data, boolean print )
  {

    boolean sorted = false;

    if (print) System.out.println("\n***BubbleSort***\n" +
    "Initial: " + data + "\n");

    //make n-1 passes across collection
    for( int passCtr = 1; passCtr < data.size(); passCtr++ ) {

      sorted = true;

      //iterate from first to next-to-last element
      for( int i = 0; i < data.size()-1; i++ ) {
        //if element at i > element at i+1, swap
        if ( data.get(i).compareTo(data.get(i+1) ) > 0 ) {
          swap(data, i, i + 1);
          sorted = false;
        }
      }
      if (print) System.out.println(data);

      if (sorted) break;
    }

    if (print) System.out.println("\nSorted: " + data);

  }//end bubbleSort

  /**********
  * There is no worst or best case scenario for selectionSort  because, no
  * matter what, selectionSort has to do a constant number of comparisons
  * and swaps (even if an element swaps with itself)
  **********/

  // Rearranges elements of input ArrayList
  // postcondition: data's elements sorted in ascending order
  // Best case time: O(n^2)
  // Worst case time: O(n^2)
  public static void selectionSort( ArrayList<Comparable> data, boolean print)
  {
    //note: this version places greatest value at rightmost end,

    //maxPos will point to position of SELECTION (greatest value)
    int maxPos;

    if (print) System.out.println("\n***SelectionSort***\n" +
    "Initial: " + data + "\n");

    for( int pass = data.size()-1; pass > 0; pass-- ) {
      maxPos = 0;
      for( int i = 1; i <= pass; i++ ) {
        if ( data.get(i).compareTo( data.get(maxPos) ) > 0 )
        maxPos = i;
      }
      swap(data, maxPos, pass);
      if (print) System.out.println(data);
    }

    if (print) System.out.println("\nSorted: " + data);

  }//end selectionSort

  /**********
  * Best case scenario for insertionSort: AL sorted in ascending order
  *    Why? Because like this, no elements will swap places. This will also
  *    result in the least number of comparisons
  * Worst case scenario for insertionSort: AL sorted in descending order
  *    Why? Because this way, it works sort of like a type of bubble(ish)
  *    sorting. Starting at element 1 and working towards the last element,
  *    each element will swap with the adjacent element with a lower index
  *    until it reaches index 0. This will result in the greatest number of
  *    swaps and comparisons.
  **********/

  // Rearranges elements of input ArrayList
  // postcondition: data's elements sorted in ascending order
  // Best case time: O(n)
  // Worst case time: O(n^2)
  public static void insertionSort( ArrayList<Comparable> data, boolean print)
  {

    if (print) System.out.println("\n***InsertionSort***\n" +
    "Initial: " + data + "\n");

    for( int partition = 1; partition < data.size(); partition++ ) {
      //partition marks first item in unsorted region

      //traverse sorted region from right to left
      for( int i = partition; i > 0; i-- ) {

        // "walk" the current item to where it belongs
        // by swapping adjacent items
        if ( data.get(i).compareTo( data.get(i-1) ) < 0 ) {
          swap(data, i, i - 1);
        }
        else
        break;
      }
      if (print) System.out.println(data);
    }

    if (print) System.out.println("\nSorted: " + data);

  }//end insertionSort

  public static void cocktailSort(ArrayList<Comparable> data, boolean print) {

    if (print) System.out.println("\n***CocktailSort***\n" +
    "Initial: " + data + "\n");

    int start = 0, end = data.size() - 1;
    //assume the AL is not sorted
    boolean sorted = false;

    while (!sorted) {

      //reset sorted to true
      sorted = true;

      //bubble sort left to right
      for (int i = start; i < end; i++) {
        //if data[i] > data[i + 1]...
        if (data.get(i).compareTo( data.get(i + 1) ) > 0) {
          //..swap them
          swap(data, i, i + 1);
          sorted = false;
        }
      }
      end--;

      if (print) System.out.println(data);

      if (sorted) break;

      //bubble sort right to left
      for (int j = end; j > start; j--) {
        //if data[i] < data[i - 1]...
        if (data.get(j).compareTo( data.get(j - 1) ) < 0) {
          //...swap them
          swap(data, j, j - 1);
          sorted = false;
        }
      }
      start++;

      if (print) System.out.println(data);

    }//end while

    if (print) System.out.println("\nSorted: " + data);

  }//end cocktailSort

  //pre-cond: elements in data are in the range [0, infinity)
  public static void countingSort(ArrayList<Integer> data, boolean print) {

    if (print) System.out.println("\n***CountingSort***\n" +
    "Initial: " + data);

    int[] arr = new int[max(data) + 1];
    for (int i = 0; i < data.size(); i++) {
      arr[data.get(i)]++;
    }

    //Might want to comment this out if AL really large
    String arrStr = "[";
    for (int i : arr)
    arrStr += i + ",";
    if (print) System.out.println("Counting array: " +
    arrStr.substring(0, arrStr.length() - 1) + "]");

    data = new ArrayList<Integer>();

    for (int i = 0; i < arr.length; i++) {
      while (arr[i] > 0) {
        data.add(i);
        arr[i]--;
        if (print) System.out.println(data);
      }
    }

    if (print) System.out.println("\nSorted: " + data + "\n");

  }//end countingSort

  public static ArrayList<Comparable> mergeSort(ArrayList<Comparable> data) {
    return mergeSortHelper(data);
  }//end mergeSort

  public static ArrayList<Comparable> mergeSortHelper(ArrayList<Comparable> data) {
    if (data.size() == 1) {
      return data;
    }
    return sortSorted(mergeSortHelper(getSublist(data, 0, data.size() / 2)), mergeSortHelper(getSublist(data, data.size() / 2, data.size())));
  }//end mergeSortHelper

  public static ArrayList<Comparable> sortSorted(ArrayList<Comparable> data1, ArrayList<Comparable> data2) {
    ArrayList<Comparable> ret = new ArrayList<Comparable>();
    while (data1.size() > 0 && data2.size() > 0) {
      if (data1.get(0).compareTo(data2.get(0)) < 0) {
        ret.add(data1.get(0));
        data1.remove(0);
      }
      else {
        ret.add(data2.get(0));
        data2.remove(0);
      }
    }
    for (Comparable i : data1) {
      ret.add(i);
    }
    for (Comparable i : data2) {
      ret.add(i);
    }
    return ret;
  }

  public static void hopeSort(ArrayList<Comparable> data, boolean print) {

    if (print) System.out.println("\n***HopeSort***\n" +
    "Initial: " + data + "\n");

    int attemptCount = 0;

    while (true) {

      //Commented out for safety lol
      //System.out.println(data);

      if (isSorted(data)) break;
      int i = (int)(Math.random() * data.size());
      int j = (int)(Math.random() * data.size());

      swap(data, i, j);

      attemptCount++;

    }

    if (print) System.out.println("\nSorted: " + data + "\n" +
    "How many tries did it take? " +
    attemptCount + "\n");

  }

  private static void quickSortHelper(ArrayList<Comparable> data, int left, int right) {
    if (left < right) {
      int pvt = partition(data, left, right);
      quickSortHelper(data, left, pvt - 1);
      quickSortHelper(data, pvt + 1, right);
    }
  }

  private static int partition(ArrayList<Comparable> data, int left, int right) {
    int pvtPos = left + (int)(Math.random() * (right - left - 1));

    Comparable pvtVal = data.get(pvtPos);
    swap(data, pvtPos, right);
    int storePos = left;
    for (int i = left; i < right; i++) {
      if (data.get(i).compareTo(pvtVal) < 0) {
        swap(data, storePos, i);
        storePos++;
      }
    }
    swap(data, storePos, right);
    return storePos;
  }

  public static void quickSort(ArrayList<Comparable> data, boolean print) {
    if (print) System.out.println("\n***QuickSort***\nInitial: " + data);
    quickSortHelper(data, 0, data.size() - 1);
    if (print) System.out.println("\nSorted: " + data);
  }

  private static void heapify(ArrayList<Comparable> data, int maxIndex) {
    int toAdd = 1;
    while (toAdd < maxIndex) {
      int cIndex = toAdd;
      int pIndex = (cIndex - 1) / 2;
      while (cIndex > 0 && data.get(cIndex).compareTo(data.get(pIndex)) > 0) {
        swap(data, cIndex, pIndex);
        cIndex = pIndex;
        pIndex = (cIndex - 1) / 2;
      }
      toAdd++;
    }
  }

  public static void heapSort(ArrayList<Comparable> data, boolean print) {
    if (print) System.out.println("\n***HeapSort***\n" +
    "Initial: " + data);
    int heapifyTo = data.size();
    while (heapifyTo > 1) {
      heapify(data, heapifyTo);
      swap(data, 0, heapifyTo - 1);
      heapifyTo--;
    }
    if (print) System.out.println("\nSorted: " + data + "\n");
  }

  private static void heapify(int[] data, int maxIndex) {
    int toAdd = 1;
    while (toAdd < maxIndex) {
      int cIndex = toAdd;
      int pIndex = (cIndex - 1) / 2;
      while (cIndex > 0 && data[cIndex] > data[pIndex]) {
        swap(data, cIndex, pIndex);
        cIndex = pIndex;
        pIndex = (cIndex - 1) / 2;
      }
      toAdd++;
    }
  }

  public static void heapSort(int[] data, boolean print) {
    if (print) System.out.println("\n***HeapSort***\n" +
    "Initial: " + data);
    int heapifyTo = data.length;
    while (heapifyTo > 1) {
      heapify(data, heapifyTo);
      swap(data, 0, heapifyTo - 1);
      heapifyTo--;
    }
    if (print) System.out.println("\nSorted: " + data + "\n");
  }

  private static void swap(ArrayList<Comparable> data, int index1, int index2) {
    data.set(index1, data.set(index2, data.get(index1)));
  }

  private static void swap(int[] data, int index1, int index2) {
    int t = data[index1];
    data[index1] = data[index2];
    data[index2] = t;
  }


  public static void main(String[] args) {

    ArrayList<Comparable> random = new ArrayList<Comparable>();
    for (int i = 0; i < 10; i++)
      random.add( (int)(Math.random() * 100));

    ArrayList<Comparable> bubble = new ArrayList<Comparable>();
    for (int i = 0; i < 10; i++)
      bubble.add(random.get(i));

    ArrayList<Comparable> select = new ArrayList<Comparable>();
    for (int i = 0; i < 10; i++)
      select.add(random.get(i));

    ArrayList<Comparable> insert = new ArrayList<Comparable>();
    for (int i = 0; i < 10; i++)
      insert.add(random.get(i));

    ArrayList<Comparable> cocktail = new ArrayList<Comparable>();
    for (int i = 0; i < 10; i++)
      cocktail.add(random.get(i));

    ArrayList<Integer> counting = new ArrayList<Integer>();
    for (int i = 0; i < 10; i++)
      counting.add((Integer)(random.get(i)));

    ArrayList<Comparable> hope = new ArrayList<Comparable>();
    for (Comparable i : random)
      hope.add(i);

    ArrayList<Comparable> heap = new ArrayList<Comparable>();
    for (int i = 0; i < 10; i++)
        heap.add(random.get(i));

    ArrayList<Comparable> quick = new ArrayList<Comparable>();
    for (int i = 0; i < 10; i++)
        quick.add(random.get(i));

    bubbleSort(bubble, true);
    selectionSort(select, true);
    insertionSort(insert, true);
    cocktailSort(cocktail, true);
    countingSort(counting, true);
    hopeSort(hope, true);
    heapSort(heap, true);
    quickSort(quick, true);
    //MergeSort stuff
    ArrayList<Comparable> merge = new ArrayList<Comparable>();
    for (int i = 0; i < 10; i++) {
      merge.add((Integer)(random.get(i)));
    }
    System.out.println("\n***MergeSort***\nInitial: " + merge);
    merge = mergeSort(merge);
    System.out.println("Sorted: " + merge);

    System.out.println("\nNow with 30,000 values (hopeSort will not be tested)\n");

    random = new ArrayList<Comparable>();
    for (int i = 0; i < 30000; i++)
      random.add( (int)(Math.random() * 100000));

    bubble = new ArrayList<Comparable>();
    for (int i = 0; i < 30000; i++)
      bubble.add(random.get(i));

    select = new ArrayList<Comparable>();
    for (int i = 0; i < 30000; i++)
      select.add(random.get(i));

    insert = new ArrayList<Comparable>();
    for (int i = 0; i < 30000; i++)
      insert.add(random.get(i));

    cocktail = new ArrayList<Comparable>();
    for (int i = 0; i < 30000; i++)
      cocktail.add(random.get(i));

    counting = new ArrayList<Integer>();
    for (int i = 0; i < 30000; i++)
      counting.add((Integer)(random.get(i)));

    heap = new ArrayList<Comparable>();
    for (int i = 0; i < 30000; i++)
        heap.add(random.get(i));

    quick = new ArrayList<Comparable>();
    for (int i = 0; i < 30000; i++)
        quick.add(random.get(i));

    merge = new ArrayList<Comparable>();
    for (int i = 0; i < 30000; i++)
      merge.add(random.get(i));

    int[] heapArr = new int[30000];
    for (int i = 0; i < heapArr.length; i++) {
      heapArr[i] = (Integer) heap.get(i);
    }

    long start, end;

    start = System.nanoTime();
    bubbleSort(bubble, false);
    end = System.nanoTime();
    System.out.println("BubbleSort Took " + ((end - start) / 1000000000.) + " seconds");

    start = System.nanoTime();
    selectionSort(select, false);
    end = System.nanoTime();
    System.out.println("SelectionSort Took " + ((end - start) / 1000000000.) + " seconds");

    start = System.nanoTime();
    insertionSort(insert, false);
    end = System.nanoTime();
    System.out.println("InsertionSort Took " + ((end - start) / 1000000000.) + " seconds");

    start = System.nanoTime();
    cocktailSort(cocktail, false);
    end = System.nanoTime();
    System.out.println("CocktailShakerSort Took " + ((end - start) / 1000000000.) + " seconds");

    start = System.nanoTime();
    countingSort(counting, false);
    end = System.nanoTime();
    System.out.println("CountingSort Took " + ((end - start) / 1000000000.) + " seconds");

    start = System.nanoTime();
    mergeSort(merge);
    end = System.nanoTime();
    System.out.println("MergeSort Took " + ((end - start) / 1000000000.) + " seconds");

    start = System.nanoTime();
    quickSort(quick, false);
    end = System.nanoTime();
    System.out.println("QuickSort Took " + ((end - start) / 1000000000.) + " seconds");

    start = System.nanoTime();
    heapSort(heap, false);
    end = System.nanoTime();
    System.out.println("HeapSort Took " + ((end - start) / 1000000000.) + " seconds");

    System.out.println("\nNow testing HeapSort with an int array rather than an AL");
    start = System.nanoTime();
    heapSort(heapArr, false);
    end = System.nanoTime();
    System.out.println("HeapSort(Array) Took " + ((end - start) / 1000000000.) + " seconds");



  }

}
