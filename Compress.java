/*
       __                     ________    _      _                 
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  

~Joan Chirinos, 2-9-18
*/

/**********************************************************************
 * A compression utility
 * Limitations:
 *   Puts quotes in place of non alphanumeric characters
 *   All lowercase
 *   Replaces new lines with spaces
 *   Probaby doesn't actually compress because the file should be
 *     written in bits. However, it is written in ordinary chars.
 * Due to the last limitation, this is just a proof of concept
 **********************************************************************/

import jutils.FileRW;
import jutils.Keyboard;

public class Compress {

    private static final String[] CHARS = {" ", "e", "t", "a", "o", "i", "n", ".", ",",  "s", "r", "h", "l", "d", "c", "u", "m", "f", "p", "g", "w", "y", "b", "v", "k", "x", "j", "q", "z", "?", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    
    private static final String[] VALS = {"010", "101", "0000", "0010", "0110", "1101", "1001", "1111", "00010", "00110", "01110", "11101", "11001", "10001", "000110", "001110", "011110", "111001", "110001", "100001", "0001110", "0011110", "0111110", "0111111", "1110001", "1100001", "1000001", "1000000", "00011110", "00111110", "00111111", "11100001", "11000001", "11000000", "000111111", "000111110", "1110000001", "1110000000", "1110000010", "1110000010"};

    //Util
    public static int indexOf(String search) {
	for (int i = 0; i < CHARS.length; i++) {
	    if (CHARS[i].equals(search)) return i;
	}
	return 29;
    }//end indexOf


    public static void compress(String fileName) {
	long start = System.nanoTime();
	String fileContents = FileRW.read(fileName).toLowerCase();
	
	//Strings are stored as 2 bytes
	System.out.println("Original file size: " +
			   (fileContents.length() * 16) +
			   " bits");
	fileContents = fileContents.replaceAll("\n", " ");
	fileContents = fileContents.replaceAll("/[^a-z0-9 ]/", "?");
	String toWrite = "";
	for (int i = 0; i < fileContents.length(); i++) {
	    toWrite += VALS[indexOf(fileContents.substring(i, i + 1))];
	};
	System.out.println("Wrote " + fileName + ".jcompress in about " + ((System.nanoTime() - start) / 1000000000.) + " seconds");
	System.out.println("New file size: " +
			   toWrite.length() +
			   " bits");
	FileRW.write(toWrite, fileName + ".jcompress");
	
    }//end compress

    public static void decompress(String fileName) {
	long start = System.nanoTime();
	if (!(fileName.substring(fileName.length() - 10, fileName.length()).equals(".jcompress"))) {
	    throw new IllegalArgumentException("\n\t\tNot a valid .jcompress file");
	}
	String fileContents = FileRW.read(fileName);
	String toWrite = "";
	while (fileContents.length() > 1) {
	    for (int testsize = 3; testsize < fileContents.length(); testsize++) {
		boolean done = false;
		String test = fileContents.substring(0, testsize);
		for (int i = 0; i < VALS.length; i++) {
		    if (test.equals(VALS[i])) {
			toWrite += CHARS[i];
			fileContents = fileContents.substring(testsize, fileContents.length());
			done = true;
			break;
		    }
		}
		if (done) break;
	    }
	}
	FileRW.write(toWrite, fileName.substring(0, fileName.length() - 10));
	System.out.println("Wrote " + fileName.substring(0, fileName.length() - 10) + " in about " + ((System.nanoTime() - start) / 1000000000.) + " seconds");
    }//end decompress

    public static void main(String[] args) {
	if (args.length > 0) {
	    if (args[0].equals("c")) compress(args[1]);
	    else if (args[0].equals("d")) decompress(args[1]);
	    else throw new IllegalArgumentException("\n\t\tUsage: java Compress <c/d> <fileName>");
	}
	else {
	    System.out.println("1. Compress\n2. Decompress?");
	    int choice = Keyboard.readInt();
	    if (choice == 1) {
		System.out.print("File to compress: ");
		String f = Keyboard.readString();
		compress(f);
	    }
	    else if (choice == 2) {
		System.out.println("File to decompress: ");
		String f = Keyboard.readString();
		decompress(f);
	    }
	    else System.out.println("Invalid choice");
	}
    }//end main
    
}//end class
