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
 * CSVRW: Comma Separated Value Reader and Writer [utility]                *
 * See README for detailed explanations of methods and constructors        *
 * Constructors:                                                           *
 *    CSVRW()                                                              *
 *    CSVRW(String)                                                        *
 * Methods:                                                                *
 *    toString()                                                           *
 *    write(String)                                                        *
 *    addRow()                                                             *
 *    addRow(int)                                                          *
 *    addColumn()                                                          *
 *    addColumn(int)                                                       *
 *    removeRow()                                                          *
 *    removeRow(int)                                                       *
 *    removeColumn()                                                       *
 *    removeColumn(int)                                                    *
 *    set(int, int, String)                                                *
 ***************************************************************************/

/***************************************************************************
 * Please note that the indices in this class are written with programmers *
 * in mind. Thus, the first element of the first row is at index (0,0)     *
 *                                                                         *
 * Also note that every csv file should be rectangular (all rows have the  *
 * same number of columns. If not, reading the file will make the buffer   *
 * rectangular and it will be written as a rectangular csv file            *
 ***************************************************************************/

import java.io.*;
import java.util.ArrayList;

public class CSVRW {

    //a buffer to edit file before writing
    private ArrayList<ArrayList<String>> _fileContents;;

    //used to make a new file
    public CSVRW() {
        _fileContents = new ArrayList<ArrayList<String>>();
	_fileContents.add(new ArrayList<String>());
	_fileContents.get(0).add(null);
    }

    //reads file and stores content in _fileContents
    //pre: fileName is a csv file
    //     fileName exists
    //     fileName is readable
    public CSVRW(String fileName) {
	if (!(fileName.substring(fileName.length() - 4).equals(".csv")))
	    throw new IllegalArgumentException("\n\tInvalid filetype");
	else {
	    File f = new File("./" + fileName);
	    if (!(f.exists()))
		throw new IllegalArgumentException("\n\tFile does not exist");
	    _fileContents = new ArrayList<ArrayList<String>>();
	    String toAdd;
	    try {
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		while ((toAdd = br.readLine()) != null) {
		    ArrayList<String> toAddAL = new ArrayList<String>();
		    for (String i : toAdd.split(","))
			toAddAL.add(i);
		    _fileContents.add(toAddAL);
		}
		br.close();
	    }
	    catch (IOException e) {
		throw new IllegalArgumentException("\n\tUnable to read file");
	    }
	    rectangularize();
	}
    }//end constructor

    //forces the user to conform with a rectangular csv
    private void rectangularize() {
	if (!isRectangular()) {
	    int maxLength = 0;
	    for (int r = 0; r < _fileContents.size(); r++)
		if (maxLength < _fileContents.get(r).size())
		    maxLength = _fileContents.get(r).size();
	    for (int r = 0; r < _fileContents.size(); r++) {
		while (_fileContents.get(r).size() < maxLength)
		    _fileContents.get(r).add(null);
	    }
	}
    }//end rectangularize

    //returns true if csv file read is rectangular
    private boolean isRectangular() {
	int size = _fileContents.get(0).size();
	for (int r = 1; r < _fileContents.size(); r++)
	    if (_fileContents.get(r).size() != size)
		return false;
	return true;
    }//end isRectangular

    //returns _fileContents in format
    //element,element,element...
    //element,element,element...
    //...
    public String toString() {
	String ret = "";
	for (int r = 0; r < _fileContents.size(); r++) {
	    ret += "";
	    for (int c = 0; c < _fileContents.get(r).size() - 1; c++)
		ret += _fileContents.get(r).get(c) + ",";
	    ret += _fileContents.get(r).get(_fileContents.get(r).size() - 1);
	    ret += "\n";
	}
	return ret;
    }//end toString

    //reurns # of rows
    public int size() {
	return _fileContents.size();
    }//end size

    //returns _fileContents
    public ArrayList<ArrayList<String>> get() {
        return _fileContents;
    }//end get

    //writes _fileContents to fileName.csv
    //if fileName.csv exists, overwrites it
    //pre: file ends in .csv
    //---->otherwise, will append ".csv" to the end
    //-->return fileName.csv
    public String write(String fileName) {
	if (!(fileName.substring(fileName.length() - 4).equals(".csv")))
	    fileName = fileName + ".csv";
	File f = new File(fileName);
	f.delete();
	try {
	    FileWriter fw = new FileWriter(fileName);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(this.toString());
	    bw.close();
	}
	catch (IOException e) {
	    throw new IllegalArgumentException("\n\tCannot write to file");
	}
	return fileName;
    }//end write
    
    /******************************************************
     * For addRow methods, all other rows get pushed down *
     ******************************************************/
    
    //adds a row of empty strings at the end of _fileContents
    //-->Returns index of addition
    public int addRow() {
	return addRow(_fileContents.size());
    }//end addRow()

    //adds a row of empty strings at index index
    //-->Return index of addition
    public int addRow(int index) {
	if (index < 0 || index > _fileContents.size()) {
	    throw new IllegalArgumentException("\n\tInvalid index");
	}
	ArrayList<String> toAdd = new ArrayList<String>();
	for (int c = 0; c < _fileContents.get(0).size(); c++)
	    toAdd.add(null);
	_fileContents.add(index, toAdd);
	return index;
    }//end arrRow(int, int)

    /************************************************************************
     * For all addColumn methods, all other columns get pushed to the right *
     ************************************************************************/

    //adds a blank string at end of each row
    //-->Returns index of addition
    public int addColumn() {
	return addColumn(_fileContents.get(0).size());
    }//end addColumn()
    
    //adds a blank string at index index of each row
    //--Returns index of addition
    public int addColumn(int index) {
	if (index < 0 || index > _fileContents.get(0).size())
	    throw new IllegalArgumentException("\n\tInvalid index");
	for (int r = 0; r < _fileContents.size(); r++)
	    _fileContents.get(r).add(index, null);
	return index;
    }//end addColumn(int)

    /**************************************************************
     * For all removeRow methods, all other rows get pushed to up *
     **************************************************************/
    
    //removes row from end of _fileContents
    //-->Returns removed row
    public ArrayList<String> removeRow() {
	return removeRow(_fileContents.size() - 1);
    }//end removeRow()

    //removes row from index index of _fileContents
    //-->Returns removed row
    public ArrayList<String> removeRow(int index) {
	if (index < 0 || index > _fileContents.size() - 1)
	    throw new IllegalArgumentException("\n\tInvalid index");
	ArrayList<String> oldRow = new ArrayList<String>();
	for (int c = 0; c < _fileContents.get(index).size(); c++)
	    oldRow.add(_fileContents.get(index).get(c));
	_fileContents.remove(index);
	return oldRow;
    }//end removeRow(int)

    /**************************************************************************
     * For all removeColumn methods, all other columns get pushed to the left *
     **************************************************************************/

    //removes element at the end of every row of _fileContents
    public ArrayList<String> removeColumn() {
	return removeColumn(_fileContents.get(0).size() - 1);
    }//end removeColumn()

    //removes elements at index index of every row of _fileContents
    public ArrayList<String> removeColumn(int index) {
	if (index < 0 || index > _fileContents.get(0).size())
	    throw new IllegalArgumentException("\n\tInvalid index");
	ArrayList<String> oldColumn = new ArrayList<String>();
	for (int r = 0; r < _fileContents.size(); r++) {	    
	    oldColumn.add(_fileContents.get(r).get(index));
	    _fileContents.get(r).remove(index);
	}
	return oldColumn;
    }//end removeColumn(int)
    
    //sets element c of row r to newVal
    //-->Returns the deleted value
    public String set(int r, int c, String newVal) {
	if (r < 0 || c < 0 ||
	    r > _fileContents.size() || c > _fileContents.get(0).size())
	    throw new IllegalArgumentException("/n/tInvalid index/indices");
	String oldVal = _fileContents.get(r).get(c);
	_fileContents.get(r).set(c, newVal);
	return oldVal;
    }//end set

    //"deletes" element c of row r (replaces it with empty String)
    public String delete(int r, int c) {
	return set(r, c, "");
    }//end delete
    
}//end class
