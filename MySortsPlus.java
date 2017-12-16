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
    public static void bubbleSort( ArrayList<Comparable> data )
    {

	boolean sorted = false;

	System.out.println("\n***BubbleSort***\n" +
			   "Initial: " + data + "\n");
	
	//make n-1 passes across collection
	for( int passCtr = 1; passCtr < data.size(); passCtr++ ) {

	    sorted = true;

	    //iterate from first to next-to-last element
	    for( int i = 0; i < data.size()-1; i++ ) {
		//if element at i > element at i+1, swap
		if ( data.get(i).compareTo(data.get(i+1) ) > 0 ) {
		    data.set( i, data.set(i+1,data.get(i)) );
		    sorted = false;
		}
	    }
	    System.out.println(data);

	    if (sorted) break;
	}

	System.out.println("\nSorted: " + data);
	
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
    public static void selectionSort( ArrayList<Comparable> data ) 
    {
	//note: this version places greatest value at rightmost end,

	//maxPos will point to position of SELECTION (greatest value)
	int maxPos;

	System.out.println("\n***SelectionSort***\n" +
			   "Initial: " + data + "\n");
	
	for( int pass = data.size()-1; pass > 0; pass-- ) {
	    maxPos = 0;
	    for( int i = 1; i <= pass; i++ ) {
		if ( data.get(i).compareTo( data.get(maxPos) ) > 0 )
		    maxPos = i;
	    }
	    data.set( maxPos, ( data.set( pass, data.get(maxPos) ) ) );
	    System.out.println(data);
	}

	System.out.println("\nSorted: " + data);
	
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
    public static void insertionSort( ArrayList<Comparable> data )
    {

	System.out.println("\n***InsertionSort***\n" +
			   "Initial: " + data + "\n");
	
	for( int partition = 1; partition < data.size(); partition++ ) {
	    //partition marks first item in unsorted region

	    //traverse sorted region from right to left
	    for( int i = partition; i > 0; i-- ) {

		// "walk" the current item to where it belongs
		// by swapping adjacent items
		if ( data.get(i).compareTo( data.get(i-1) ) < 0 ) {
		    data.set( i, data.set( i-1, data.get(i) ) ); 
		}
		else 
		    break;
	    }
	    System.out.println(data);
	}

	System.out.println("\nSorted: " + data);
	
    }//end insertionSort

    public static void cocktailSort(ArrayList<Comparable> data) {

	System.out.println("\n***CocktailSort***\n" +
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
		    data.set(i, data.set(i + 1, data.get(i)));
		    sorted = false;
		}
	    }
	    end--;

	    System.out.println(data);
	    
	    if (sorted) break;

	    //bubble sort right to left
	    for (int j = end; j > start; j--) {
		//if data[i] < data[i - 1]...
		if (data.get(j).compareTo( data.get(j - 1) ) < 0) {
		    //...swap them
		    data.set(j, data.set(j - 1, data.get(j)));
		    sorted = false;
		}
	    }
	    start++;

	    System.out.println(data);

		}//end while

	System.out.println("\nSorted: " + data);

    }//end cocktailSort


    public static void main(String[] args) {

	ArrayList<Comparable> random = new ArrayList<Comparable>();
	for (int i = 0; i < 10; i++)
	    random.add( (int)(Math.random() * 100) );

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

	bubbleSort(bubble);
	selectionSort(select);
	insertionSort(insert);
	cocktailSort(cocktail);
	
    }

}
