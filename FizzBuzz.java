import jutils.*;
import java.util.ArrayList;
import java.util.*;

//an easily expandable FizzBuzz

public class FizzBuzz {
    
    public static void main(String [] args) {

	fizz();
	
    }//end main

    public static void fizz() {
	//prints Fizz for multiples of 3
	//Buzz for multiples of 5
	//FizzBuzz for multiples of both

	//tests for divisibility in order from left to right.
	//so if you want to print
	//FizzJazzBuzz
	//for multiples of 3, 5, and 7:
	//int rem[] = {3, 7, 5};
	//String[] word {"Fizz", "Jazz", "Buzz"};
	
	int[] rem = {3, 5}; //each number corresponds with a word
	String[] word = {"Fizz", "Buzz"}; //each word corresponds with a number

	//you can change the range too
	for (int i = 0; i <= 100; i++) {
	    String p = "";
	    for (int div = 0; div < rem.length; div++) {
		if (i % rem[div] == 0)
		    p += word[div];
	    }
	    if (p.length() == 0)
		System.out.println(i);
	    else
		System.out.println(p);
	}
    }
    
}//end class
