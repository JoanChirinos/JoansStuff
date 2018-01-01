/*
       __                     ________    _      _                 
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  

~Joan Chirinos, January 1, 2018
*/

/**********************************************************************
 * Simple library login system. Allows user to create a new library
 * account or login to an existing one.
 * To add (possibly):
 *  - Forgot password? thing
 **********************************************************************/

import jutils.*;
import java.io.*;
import java.util.Calendar;

public class LibraryLogin {

    public static boolean accountExists(String cardnumber) {
	File f = new File(cardnumber);
	return f.exists();
    }//end accountExists

    public static boolean correctPin(String cardnumber, String pin) {
	String f = FileRW.read(cardnumber);
	return f.split(",|\\\n")[0].equals(pin);
    }//end correctPin

    public static String createAccount() {
	String cardnumber, pin, toWrite;
	int month, day, year;
	while (true) {
	    cardnumber = "";
	    for (int i = 0; i < 9; i++)
		cardnumber += (int)(Math.random() * 10);
	    if (!(accountExists(cardnumber))) break;
	}
	while (true) {
	    System.out.print("Set a 4 digit pin: ");
	    pin = Keyboard.readString();
	    try {
		Integer.parseInt(pin);
		if (pin.length() != 4 || pin.substring(0, 1).equals("-")) {
		    System.out.println("Invalid pin!");
		    continue;
		}
		else break;
	    }	    
	    catch (Exception e) { }
	}
	toWrite = pin;
	System.out.print("Full name: ");
	toWrite += "\n" + Keyboard.readString();
	while (true) {
	    System.out.println("Date of birth:");
	    System.out.print("\tMonth (1-12): ");
	    month = Keyboard.readInt();
	    if (month < 1 || month > 12) {
		System.out.println("Invalid DoB");
		continue;
	    }
	    System.out.print("\tDay (1-31): ");
	    day = Keyboard.readInt();
	    if (day < 1 || day > 31) {
		System.out.println("Invalid day");
		continue;
	    }
	    System.out.print("\tYear: ");
	    year = Keyboard.readInt();
	    if (year < 1900 ||
		year > Calendar.getInstance().get(Calendar.YEAR)) {
		System.out.println("Invalid year");
		continue;
	    }
	    toWrite += "\n" + month + "/" + day + "/" + year;
	    break;
	}
	System.out.println("Card number: " + cardnumber + "\nPin: " + pin);
	FileRW.write(toWrite, cardnumber);
	return cardnumber;
    }//end createAccount

    public static String startup() {
	String cardnumber, pin;
	while (true) {
	    System.out.println("1. Login\n2. Create Account\n3. Exit");
	    int choice = Keyboard.readInt();
	    newchoice:
	    if (choice == 1) {
		while (true) {
		    System.out.print("Card Number: ");
		    cardnumber = Keyboard.readString();
		    if (!(accountExists(cardnumber))) {
			System.out.println("Card number not found."+
					   "\nOptions:\n1. Try again" +
					   "\n2. Create new account");
			int tryagain = Keyboard.readInt();
			if (tryagain == 2) {
			    break newchoice;
			}
		    }
		    else break;
		}
		System.out.print("Pin: ");
		pin = Keyboard.readString();
		if (!(correctPin(cardnumber, pin))) {
		    System.out.println("Incorrect pin");
		    break newchoice;
		}
		else return cardnumber;
	    }
	    else if (choice == 2) {
		return createAccount();
	    }
	    else if (choice == 3) {
		return "";
	    }
	}
    }//end startup

    public static void main(String[] args) {
	String cardnumber;
	System.out.println("Welcome to Joan's Library System!");
	cardnumber = startup();
	System.out.println("\n\n");
	if (cardnumber.equals("")) return;
    }//end main

}//end class
