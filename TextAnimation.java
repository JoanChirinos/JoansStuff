/*
       __                     ________    _      _                 
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  

~Joan Chirinos, December 24, 2017
*/

/************************************************************************
 * I will not be updating or improving this. This was merely a proof of *
 * concept and a fun animation excercise                                *
 ************************************************************************/

import jutils.Keyboard;
import java.util.concurrent.TimeUnit;

public class TextAnimation {

    public static String[][] screen = new String[18][64];
    public static int frames = 30;
    public static int xx = 0;
    public static int xy = 0;
    public static int xr = 16 - xy;
    public static int xc = xx;

    public static void fill(int frame) {

	
	
    }//end fill

    public static void init() {

	for (int r = 0; r < screen.length; r++)
	    for (int c = 0; c < screen[r].length; c++)
		screen[r][c] = " ";
		    
	screen[0][0] = "~";
	screen[15][0] = "~";
	screen[15][63] = "~";
	screen[0][63] = "~";
	

	String p1 = "Please make";
	String p2 = "your screen";
	String p3 = "about this big";

	for (int i = 0; i < p1.length(); i++)
	    screen[7][i + 26] = p1.substring(i, i + 1);

	for (int i = 0; i < p2.length(); i++)
	    screen[8][i + 26] = p2.substring(i, i + 1);

	for (int i = 0; i < p3.length(); i++)
	    screen[9][i + 25] = p3.substring(i, i + 1);
	
    }//end init

    public static void draw(int frame) {

        
	for (int i = 0; i < screen[17].length; i++)
	    screen[17][i] = "_";
	screen[xr + 1][xc] = "o";

    }//end draw

    public static void printScreen() {
	for (int r = 0; r < screen.length; r++) {
	    for (int c = 0; c < screen[r].length; c++)
		System.out.print(screen[r][c]);
	    System.out.println();
	}
    }//end printScreen

    public static void clearScreen() {
	for (int r = 0; r < screen.length; r++)
	    for (int c = 0; c < screen[r].length; c++)
		screen[r][c] = " ";
    }//end clearScreen

    public static void printScreen(boolean lmao) {
	for (int r = 0; r < screen.length - 2; r++) {
	    for (int c = 0; c < screen[r].length; c++)
		System.out.print(screen[r][c]);
	    System.out.println();
	}
    }//end printScreen

    public static void main(String[] args) {

	init();
	printScreen(false);
	System.out.println("Type 1 to start: ");
	Keyboard.readString();
	clearScreen();
	int fnum = 0;
	while (true) {
	    draw(fnum);
	    printScreen();
	    try {
		TimeUnit.MILLISECONDS.sleep(66);
	    }
	    catch (InterruptedException e) { }
	    clearScreen();
	    xx++;
	    xc = (xx + 1) % 64;
	    xy = (int)(Math.abs(Math.sin(xc / 3.) * 5) + 0.5);
	    xr = 16 - xy;
	    fnum++;
	}
	    

    }//end main

}//end class
