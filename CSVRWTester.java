/*
       __                     ________    _      _                 
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  

~Joan Chirinos, December 25, 2017
*/

import jutils.CSVRW;

public class CSVRWTester {

    public static void main(String[] args) {
	
	//throws IllegalArgumentException:Invalid filetype
	//-->only fails if args[0] is not a csv file and is present in
	//   this directory
	//CSVRW invalidFileType = new CSVRW(args[0]);

	//throws IllegalArgumentException:File does not exist
	//-->only fails if args[0] does not exist
	//CSVRW fileDoesNotExist = new CSVRW(args[0]);

	//throws IllegalArgumentException:Unable to read file
	//-->only fails if args[0] is locked (no read permission)
	//CSVRW unreadable = new CSVRW("unreadable.csv");

	/**
	 * Note: args[0] should be the included "test.csv" file for bestest
	 * testing
	 **/
	
	CSVRW c = new CSVRW(args[0]);
	System.out.println(c);
	//makes new file
	//c.write(args[0].substring(0, args[0].length() - 4) + "Duplicate");
	
	System.out.println("addRow()");
	System.out.println("Added at index: " + c.addRow());
	System.out.println(c);
	
	System.out.println("\naddRow(1)");
	System.out.println("Added at index: " + c.addRow(1));
	System.out.println(c);
	
	System.out.println("\naddColumn()");
	System.out.println("Added at index: " + c.addColumn());
	System.out.println(c);

	System.out.println("\naddColumn(1)");
	System.out.println("Added at index: " + c.addColumn(1));
	System.out.println(c);

	System.out.println("\nremoveRow()");
	System.out.println("Removed: " + c.removeRow());
	System.out.println(c);

	System.out.println("\nremoveRow(1)");
	System.out.println("Removed: " + c.removeRow(1));
	System.out.println(c);

	System.out.println("\nremoveColumn()");
	System.out.println("Removed: " + c.removeColumn());
	System.out.println(c);

	System.out.println("\nremoveColumn(1)");;
	System.out.println("Removed: " + c.removeColumn(1));
	System.out.println(c);

	System.out.println("delete(0, 0)");
	System.out.println("Deleted: " + c.delete(0, 0));
	System.out.println(c);

	System.out.println("\nset(0, 0, \"Lastest Name\")");
	System.out.println("replaced: " + c.set(0, 0, "Lastest Name"));
	System.out.println(c);

	CSVRW blanktest = new CSVRW();
	System.out.println(blanktest);
	for (int i = 0; i < 4; i++) {
	    blanktest.addRow();
	    blanktest.addColumn();
	    System.out.println(blanktest);
	}
	
    }

}//end class
