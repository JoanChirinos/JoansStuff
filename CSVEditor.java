/*
       __                     ________    _      _                 
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  

~Joan Chirinos, December 23, 2017
*/

/**********************************************************************
 * Stand-alone CSV editor
 * Should use jutils.CSVRW (change coming soon?)
 * Compile and run to use the program
 **********************************************************************/

import java.io.*;
import java.util.ArrayList;
import jutils.Keyboard;

public class CSVEditor {

    private ArrayList<ArrayList<String>> fileContents;
    private String openFile;
    private boolean printWarn;
    private int DEFAULT_MAX_PRINT_SIZE = 5;
    private boolean printAnyway;

    public CSVEditor() {
	fileContents = new ArrayList<ArrayList<String>>();
	openFile = "";
	printWarn = false;
	printAnyway = true;
    }//end constructor

    public void open(String fileName) {
	fileContents = new ArrayList<ArrayList<String>>();
	if (fileName.equals("")) {
	    System.out.println("Invalid file!");
	}
	else {
	    openFile = "";
	    File f = new File("./" + fileName);
	    if (f.exists()) openFile = fileName;
	    else {
		System.out.println("File doesn't exist!");
		System.out.println("Create new file? (y/n)");
		String choice = Keyboard.readString().toLowerCase();
		if (choice.equals("y")) {
		    try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("");
			bw.close();
		    }
		    catch (IOException e) {
			System.out.println("File writing went wrong");
		    }
		    openFile = fileName;
		    System.out.print("Rows: ");
		    int r = Keyboard.readInt();
		    if (r < 1) r = 1;
		    System.out.print("Columns: ");
		    int c = Keyboard.readInt();
		    if (c < 1) c = 1;
		    for ( ; r > 0; r--) {
			ArrayList<String> a = new ArrayList<String>();
			for (int col = c; col > 0; col--)
			    a.add(null);
			fileContents.add(a);
		    }
		}
	    }
	    read();
	}
    }//end open

    public void read() {
	String toAdd;
        try {
	    FileReader fr = new FileReader(openFile);
	    BufferedReader br = new BufferedReader(fr);
	    while ((toAdd = br.readLine()) != null) {
		String[] toAddList = toAdd.split(",");
		ArrayList<String> toAddAL = new ArrayList<String>();
		for (String i : toAddList)
		    toAddAL.add(i);
		fileContents.add(toAddAL);
	    }
	    br.close();
	}
	catch (FileNotFoundException e) {
	    System.out.println("Unable to read " + openFile);
	}
	catch (IOException e) {
	    System.out.println("Error when reading file " + openFile);
	}
	if (fileContents.size() > DEFAULT_MAX_PRINT_SIZE ||
	    fileContents.get(0).size() > DEFAULT_MAX_PRINT_SIZE) {
	    printWarn = true;
	    printAnyway = false;
	}
    }//end read

    public void write() {
	if (openFile.equals(""))
	    System.out.println("Open a file first!");
	else {
	    String toAdd = "";
	    if (fileContents.size() != 0 && fileContents.get(0).size() != 0) {
		for (int r = 0; r < fileContents.size(); r++) {
		    for (int c = 0; c < fileContents.get(r).size(); c++)
			toAdd += fileContents.get(r).get(c) + ",";
		    toAdd = toAdd.substring(0, toAdd.length() - 1);
		    toAdd += "\n";
		}
	    }
	    System.out.println(toAdd);
	    try {
		FileWriter fw = new FileWriter(openFile);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(toAdd);
		bw.close();
	    }
	    catch (IOException e) {
		System.out.println("File writing went wrong");
	    }	    
	}
    }//end write
    
    public void replace() {
	printFile();
	System.out.print("Row: ");
	int r = Keyboard.readInt();
	System.out.print("Column: ");
	int c = Keyboard.readInt();
	System.out.print("New value: ");
	String newVal = Keyboard.readString();
	try {
	    ArrayList<String> row = fileContents.get(r - 1);
	    row.set(c - 1, newVal);
	    fileContents.set(r - 1, row);
	}
	catch (IndexOutOfBoundsException e) {
	    System.out.println("Row or column not valid");
	}
	System.out.println();
    }//end replace

        public void replace(String str) {
	printFile();
	System.out.print("Row: ");
	int r = Keyboard.readInt();
	System.out.print("Column: ");
	int c = Keyboard.readInt();
	try {
	    ArrayList<String> row = fileContents.get(r - 1);
	    row.set(c - 1, "");
	    fileContents.set(r - 1, row);
	}
	catch (IndexOutOfBoundsException e) {
	    System.out.println("Row or column not valid");
	}
	System.out.println();
    }//end replace

    public void editFile() {
	while (true) {
	    printFile();
	    System.out.println("\nEdit options:" +
			       "\n\tadd" +
			       "\n\treplace" +
			       "\n\tremove" +
			       "\n\tback");
	    System.out.println("What would you like to do?");
	    String choice = Keyboard.readString().toLowerCase();
	    if (choice.equals("back"))
		break;
	    else if (choice.equals("print"))
		printFile();
	    else if (choice.equals("replace"))
		replace();
	    else if (choice.equals("add"))
		add();
	    else if (choice.equals("remove"))
		remove();
	}	
    }//end editFile

    public void remove() {
	while (true) {
	    printFile();
	    System.out.println("What would you like to remove?" +
			       "\n\trow" +
			       "\n\tcolumn" +
			       "\n\telement" +
			       "\n\tback");
	    String choice = Keyboard.readString().toLowerCase();
	    if (choice.equals("row")) {
		removeRow();
		break;
	    }
	    else if (choice.equals("column")) {
		removeCol();
		break;
	    }
	    else if (choice.equals("element")) {
		replace("");
		break;
	    }
	    else if (choice.equals("back"))
		break;
	}
    }//end remove

    public void removeRow() {
	printFile();
	System.out.println("Which row would you like to remove?" +
			   "\n > All other rows will be pushed up");
	System.out.print("Row: ");
	int choice = Keyboard.readInt();
	choice--;
	if (choice >= fileContents.size() || choice < -1) {
	    System.out.println("Removal canceled");
	    return;
	}
	else {
	    fileContents.remove(choice);
	    System.out.println("Row " + (choice + 1) + " removed");
	}
    }//end removeRow

    public void removeCol() {
	printFile();
	System.out.println("Which row would you like to remove?" +
			   "\n > All other rows will be pushed up");
	System.out.print("Row: ");
	int choice = Keyboard.readInt();
	choice--;
	if (choice >= fileContents.get(0).size() || choice < -1) {
	    System.out.println("Removal canceled");
	    return;
	}
	else {
	    for (int i = 0; i < fileContents.size(); i++)
		fileContents.get(i).remove(choice);
	    System.out.println("Row " + (choice + 1) + " removed");
	}
    }

    public void add() {
	while (true) {
	    printFile();
	    System.out.println("What would you like to add?" +
			       "\n\trow" +
			       "\n\tcolumn" +
			       "\n\telement" +
			       "\n\tback");
	    String choice = Keyboard.readString().toLowerCase();
	    if (choice.equals("row")) {
		addRow();
		break;
	    }
	    else if (choice.equals("column")) {
		addCol();
		break;
	    }
	    else if (choice.equals("element")) {
		replace();
		break;
	    }
	    else if (choice.equals("back"))
		break;
	}
    }//end add

    public void addCol() {
	printFile();
	System.out.println("Where would you like to add the column?" +
			   "\n > All other columns will be pushed left" +
			   "\n > To add a new column at the end, enter 0" +
			   "\n > Enter -1 to cancel");
	System.out.print("Column: ");
	int choice = Keyboard.readInt();
	choice--;
	if (choice >= fileContents.get(0).size() || choice < -1) {
	    System.out.println("Addition canceled");
	    return;
	}
	else if (choice == -1)
	    for (int i = 0; i < fileContents.size(); i++)
		fileContents.get(i).add("");
	else
	    for (int i = 0; i < fileContents.size(); i++)
		fileContents.get(i).add(choice, "");
    }//end addrow

    public void addRow() {
	printFile();
	System.out.println("Where would you like to add the row?" +
			   "\n > All other rows will be pushed down" +
			   "\n > To add a new row at the end, enter 0" +
			   "\n > Enter -1 to cancel");
	System.out.print("Row: ");
	int choice = Keyboard.readInt();
	choice--;
	if (choice >= fileContents.size() || choice < -1) {
	    System.out.println("Addition canceled");
	    return;
	}
	else if (choice == -1) {
	    ArrayList<String> a = new ArrayList<String>();
	    for (int i = 0; i < fileContents.get(0).size(); i++)
		a.add("");
	    fileContents.add(a);
	}
	else {
	    ArrayList<String> a = new ArrayList<String>();
	    for (int i = 0; i < fileContents.get(0).size(); i++)
		a.add("");
	    fileContents.add(choice, a);
	}
	
    }//end addCol

    public void printFile() {
	if (!printAnyway && printWarn) {
	    System.out.println("File may be too large to print." +
			       "\nPrint anyway? (y/n)");
	    String choice = Keyboard.readString().toLowerCase();
	    if (choice.equals("y"))
		printAnyway = true;
	}
	if (printAnyway) {
	    System.out.println();
	    for (ArrayList<String> i : fileContents)
		System.out.println("\t" + i);
	    System.out.println();
	}
    }//end printFile

    public String getOpenFile() {
	return openFile;
    }//end getOpenFile

    public void clearCSVEditor() {
	openFile = "";
	fileContents = new ArrayList<ArrayList<String>>();
	printWarn = false;
	printAnyway = false;
    }//end clearCSVEditor

    public static void main(String[] args) {

	CSVEditor ce = new CSVEditor();

	while (true) {
	    System.out.println("\ncommands:" +
			       "\n\topen <fileName>" +
			       "\n\texit");
	    System.out.println("What would you like to do?");
	    String choice = Keyboard.readString().toLowerCase();
	    try {
		//if user doesn't specify file name
		if (choice.substring(0, 4).equals("open") &&
		    choice.length() < 5)
		    System.out.println("Filename not specified.\n" +
				       "Usage: open <filename>");

		//to open file
		else if (choice.substring(0, 4).equals("open")) {
		    ce.open(choice.substring(5));
		    while (true) {

			System.out.println("\nFile currently open: " +
					   ce.getOpenFile());

			System.out.println("\nCommands:" +
					   "\n\tprint" +
					   "\n\tedit" +
					   "\n\twrite" +
					   "\n\tback");
			System.out.println("What would you like to do?");
			String openchoice = Keyboard.readString().toLowerCase();

			//to print read file
			if (openchoice.equals("print"))
			    ce.printFile();
			else if (openchoice.equals("edit"))
			    ce.editFile();
			else if (openchoice.equals("write"))
			    ce.write();
			else if (openchoice.equals("back")) {
			    System.out.println("You will lose everything you" +
					       " haven't write-ed!\nAre you" +
					       " sure you want to go back?" +
					       "(y/n)");
			    String backchoice = Keyboard.readString();
			    backchoice = backchoice.toLowerCase();
			    if (backchoice.equals("y")) {
				ce.clearCSVEditor();
				break;
			    }
			}
		    }
		}
		else if (choice.equals("exit"))
		    break;
	    }
	    catch (Exception e) { System.out.println("Something went wrong"); }
	    }
	
    }//end main
    
}//end class
