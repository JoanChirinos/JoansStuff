/*
       __                     ________    _      _                 
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  

~Joan Chirinos, <DATE>
*/

/**********************************************************************
 * A basic calculator that takes in user input and prints the answer.
 * User-friendly interface can take lines like "5*3+5" rather than
 * "multiply(5, 3)"
 * Note: To subtract, add a negative
 * add: x+y
 * subtract: x+-y
 * multiply: x*y
 * divide: x/y
 * raise to a power: x^(y)
 * sin, cos, tan: sin(x), cos(x), tan(x)
 * parentheses: (x)
 * log base 10: log(x)
 **********************************************************************/

import jutils.Keyboard;

public class Calculator {

    public static double add(String xs, String ys) {
	try {
	    double x = (Double.parseDouble(xs));
	    double y = (Double.parseDouble(ys));
	    return x + y;
	}
	catch (NumberFormatException e) {
	    return mathify(xs) + mathify(ys);
	}
	catch (Exception e) {
	    throw new IllegalArgumentException("\n\tSomething went wrong in" +
					       " the add method");
	}
    }//end add

    public static double subtract(String xs, String ys) {
	try {
	    double x = (Double.parseDouble(xs));
	    double y = (Double.parseDouble(ys));
	    return x - y;
	}
	catch (NumberFormatException e) {
	    return mathify(xs) - mathify(ys);
	}
	catch (Exception e) {
	    throw new IllegalArgumentException("\n\tSomething went wrong in" +
					       " the subtract method");
	}
    }//end subtract

    public static double multiply(String xs, String ys) {
	try {
	    double x = (Double.parseDouble(xs));
	    double y = (Double.parseDouble(ys));
	    return x * y;
	}
	catch (NumberFormatException e) {
	    return mathify(xs) * mathify(ys);
	}
	catch (Exception e) {
	    throw new IllegalArgumentException("\n\tSomething went wrong in" +
					       " the multiply method");
	}
    }//end multiply

    public static double divide(String xs, String ys) {
	try {
	    double x = (Double.parseDouble(xs));
	    double y = (Double.parseDouble(ys));
	    return x / y;
	}
	catch (NumberFormatException e) {
	    return mathify(xs) / mathify(ys);
	}
	catch (Exception e) {
	    throw new IllegalArgumentException("\n\tSomething went wrong in" +
					       " the multiply method");
	}
    }//end subtract

    public static double raise(String a, String b) {
	try {
	    a = a.split("\\+|\\-|\\/|\\*|\\)")[a.split("\\+|\\-|\\/|\\*|\\)").length - 1];
	    double x = Double.parseDouble(a);
	    double y = Double.parseDouble(b);
	    return Math.pow(x, y);
	}
	catch (NumberFormatException e) {
	    return Math.pow(mathify(a), mathify(b));
	}
	catch (Exception e) {
	    throw new IllegalArgumentException("\n\tSomething went wrong in" +
					       " the raise method");
	}
    }//end raise
    
    public static double mathify(String toSolve) {

				   
	
	if (toSolve.indexOf("^(") != -1) {
	    String[] solve = toSolve.split("\\^\\(|\\)");
	    return raise(solve[0], solve[1]);
	}
	
	else if (toSolve.indexOf("*") != -1 || toSolve.indexOf("/") != -1) {
	    if (toSolve.indexOf("/") == -1) {
		String[] solve = toSolve.split("\\*", 2);
		return multiply(solve[0], solve[1]);
	    }
	    else if (toSolve.indexOf("*") == -1) {
		String[] solve = toSolve.split("\\/", 2);
		return divide(solve[0], solve[1]);
	    }
	    else if (toSolve.indexOf("/") < toSolve.indexOf("*")) {
		String[] solve = toSolve.split("\\/", 2);
		return divide(solve[0], solve[1]);
	    }
	    else {
		String[] solve = toSolve.split("\\*", 2);
		return multiply(solve[0], solve[1]);
	    }	    
	}

	else if (toSolve.indexOf("+") != -1) {
	    String[] solve = toSolve.split("\\+", 2);
	    return add(solve[0], solve[1]);
	}

	else if (toSolve.indexOf("-") != -1) {
	    String[] solve = toSolve.split("-", 2);
	    return subtract(solve[0], solve[1]);
	}

	return Double.parseDouble(toSolve);

    }//end mathify

    public static void main(String[] args) {

	System.out.println("Joan's Calculator!\nType \"exit\" to exit");

	while (true) {

	    String toSolve = Keyboard.readString().toLowerCase();
	    if (toSolve.equals("exit")) break;

	    try {
		System.out.println(mathify(toSolve));
	    }
	    catch (Exception e) {
		System.out.println("ERROR");
	    }
	    
	}
	
    }//end main

}//end class
