package jutils;

/*
       __                     ________    _      _                 
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  

~Joan Chirinos, December 25, 2017
*/

/***************************************************************************
 * Generic File Reader/Writer
 * See README for detailed explanations of methods
 * Methods:
 *    read(String)
 *    write(String, String)
 ***************************************************************************/

import java.io.*;

public class FileRW {

    public static String read(String fileName) {
	File f = new File("./" + fileName);
	if (!(f.exists()))
	    throw new IllegalArgumentException("\n\tFile does not exist");
	String ret = "";
	try {
	    FileReader fr = new FileReader(fileName);
	    BufferedReader br = new BufferedReader(fr);
	    String toAdd;
	    while ((toAdd = br.readLine()) != null)
		ret += toAdd + "\n";
	}
	catch (IOException e) {
	    throw new IllegalArgumentException("\n\tUnable to read file");
	}
	return ret;
    }//end read

    public static void write(String toWrite, String fileName) {
	File f = new File(fileName);
	f.delete();
	try {
	    FileWriter fw = new FileWriter(fileName);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(toWrite);
	    bw.close();
	}
	catch (IOException e) {
	    throw new IllegalArgumentException("\n\tCannot write to file");
	}
    }//end write
    
}//end class
