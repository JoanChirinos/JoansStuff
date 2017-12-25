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
 * Compile and run to use the program
 **********************************************************************/

import jutils.Keyboard;
import jutils.CSVRW;
import java.util.ArrayList;
import java.io.*;

public class CSVEditor {

    private CSVRW _fileContents;
    private String _openFile;
    private boolean printWarn;
    private int DEFAULT_MAX_PRINT_SIZE = 5;
    private boolean printAnyway;

    public CSVEditor() {
	_fileContents = new CSVRW();
	_openFile = "";
	printWarn = false;
	printAnyway = true;
    }//end constructor

    public boolean open(String fileName) {
	clearCSVEditor();
	if (fileName.equals("")) {
	    System.out.println("Invalid file!");
	}
	else {
	    File f = new File("./" + fileName);
	    if (f.exists()) {
		_openFile = fileName;
		_fileContents = new CSVRW(fileName);
	    }
	    else {
		System.out.println("File doesn't exist!");
		System.out.println("Create new file? (y/n)");
		String choice = Keyboard.readString().toLowerCase();
		if (choice.equals("y")) {
		    _fileContents = new CSVRW();
		    _openFile = fileName;
		    System.out.print("Rows: ");
		    int r = Keyboard.readInt();
		    if (r < 1) r = 1;
		    while (r > 1) {
			_fileContents.addRow();
			r--;
		    }
		    
		    System.out.print("Columns: ");
		    int c = Keyboard.readInt();
		    if (c < 1) c = 1;
		    while (c > 1) {
			_fileContents.addColumn();
			c--;
		    }
		}
		else return false;
	    }
	    warn();
	}
	return true;
    }//end open

    public void warn() {
	if (_fileContents.size() > DEFAULT_MAX_PRINT_SIZE ||
	    _fileContents.get(0).size() > DEFAULT_MAX_PRINT_SIZE) {
	    printWarn = true;
	    printAnyway = false;
	}
    }//end read

    public void write() {
	if (_openFile.equals(""))
	    System.out.println("Open a file first!");
	String fileName = _openFile;
	if (!(_openFile.substring(_openFile.length() - 4).equals(".csv")))
	    fileName += ".csv";
        _fileContents.write(fileName);
	System.out.println("Wrote " + fileName);
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
	    _fileContents.set(r - 1, c - 1, newVal);
	}
	catch (IllegalArgumentException e) {
	    System.out.println("Invalid row/column");
	}
	System.out.println();
    }//end replace

    public void removeElement() {
	printFile();
	System.out.print("Row: ");
	int r = Keyboard.readInt();
	System.out.print("Column: ");
	int c = Keyboard.readInt();
        _fileContents.delete(r - 1, c - 1);
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
		removeElement();
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
	try {
	    _fileContents.removeRow(choice);
	    System.out.println("Row " + (choice + 1) + " removed");
	}
	catch (IllegalArgumentException e) {
	    System.out.println("Removal canceled");
	}
    }//end removeRow

    public void removeCol() {
	printFile();
	System.out.println("Which row would you like to remove?" +
			   "\n > All other columns will be pushed left");
	System.out.print("Column: ");
	int choice = Keyboard.readInt();
	choice--;
	try {
	    _fileContents.removeColumn(choice);
	    System.out.println("Column " + (choice + 1) + " removed");
	}
	catch (IllegalArgumentException e) {
	    System.out.println("Removal canceled");
	}
    }//end removeCol

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
			   "\n > All other columns will be pushed right" +
			   "\n > To add a new column at the end, enter 0" +
			   "\n > Enter -1 to cancel");
	System.out.print("Column: ");
	int choice = Keyboard.readInt();
	choice--;
	if (choice == -1)
	    _fileContents.addColumn();
	else {
	    try {
		_fileContents.addColumn(choice);
	    }
	    catch (IllegalArgumentException e) {
		System.out.println("Addition cancelled");
	    }
	}
    }//end addCol

    public void addRow() {
	printFile();
	System.out.println("Where would you like to add the row?" +
			   "\n > All other rows will be pushed down" +
			   "\n > To add a new row at the end, enter 0" +
			   "\n > Enter -1 to cancel");
	System.out.print("Row: ");
	int choice = Keyboard.readInt();
	choice--;
	if (choice == -1)
	    _fileContents.addRow();
	else {
	    try {
		_fileContents.addRow(choice);
	    }
	    catch (IllegalArgumentException e) {
		System.out.println("Addition cancelled");
	    }
	}
    }//end addRow

    public void printFile() {
	if (!printAnyway && printWarn) {
	    System.out.println("File may be too large to print." +
			       "\nPrint anyway? (y/n)");
	    String choice = Keyboard.readString().toLowerCase();
	    if (choice.equals("y"))
		printAnyway = true;
	}
	if (printAnyway)
	    System.out.println(_fileContents);
    }//end printFile

    public String getOpenFile() {
	return _openFile;
    }//end getOpenFile

    public void clearCSVEditor() {
	_openFile = "";
	_fileContents = new CSVRW();
	printWarn = false;
	printAnyway = true;
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
		    if (!(ce.open(choice.substring(5)))) break;
		    while (true) {

			System.out.println("\nFile currently open: " +
					   ce.getOpenFile());

			System.out.println("\nCommands:" +
					   "\n\tprint" +
					   "\n\tedit" +
					   "\n\twrite" +
					   "\n\tclose");
			System.out.println("What would you like to do?");
			String openchoice = Keyboard.readString().toLowerCase();

			//to print read file
			if (openchoice.equals("print"))
			    ce.printFile();
			else if (openchoice.equals("edit"))
			    ce.editFile();
			else if (openchoice.equals("write"))
			    ce.write();
			else if (openchoice.equals("close")) {
			    System.out.println("You will lose everything you" +
					       " haven't write-ed!\nAre you" +
					       " sure you want to close?" +
					       "(y/n)");
			    String backchoice = Keyboard.readString();
			    backchoice = backchoice.toLowerCase();
			    if (backchoice.equals("y")) {
				break;
			    }
			}
		    }
		}
		else if (choice.equals("exit"))
		    break;
	    }
	    catch (Exception e) { System.out.println(e); }
	    }
	
    }//end main
    
}//end class
