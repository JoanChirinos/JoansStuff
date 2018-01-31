/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, <DATE>
*/

/**********************************************************************
 The board file must be in form:
 rank
 row, column, element number, value
 row, column, element number, value
 ...

 MUST have >= 2^(rank) - 1 defined data values
 **********************************************************************/

import jutils.*;

 public class Board {

   private int[][][] _board;
   private int _rank;

   public Board(String loadFrom) {
     String r = FileRW.read(loadFrom);
     String[] data = r.split("\n");
     _rank = Integer.parseInt(data[0]);
     _board = new int[_rank][_rank][_rank * _rank];
     for (int i = 1; i < data.length; i++) {
       String[] temp = data[i].split(",");
       _board[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])][Integer.parseInt(temp[2])] = Integer.parseInt(temp[3]);
     }
   }//end constructor

   public int[][][] getBoard() {
     return _board;
   }//end getBoard

   public int getRank() {
     return _rank;
   }

   public int getEl(int r, int c, int el) {
     return getBoard()[r][c][el];
   }//end getEl

   public int[] getRow(int r) {
     int[] retRow = new int[getRank() * getRank()];
     int count = 0;
     for (int c = 0; c < getRank(); c++) {
       int minEl = (r % getRank()) * getRank();
       for (int e = minEl; e < minEl + getRank(); e++) {
         retRow[count] = getBoard()[r/getRank()][c][e];
         count++;
       }
     }
     return retRow;
   }//end getRow

   public int[] getCol(int c) {
     int[] retCol = new int[getRank() * getRank()];
     for (int r = 0; r < getRank() * getRank(); r++) {
        retCol[r] = getRow(r)[c];
     }
     return retCol;
   }//end getCol

   public int[] getSquare(int n) {
     int[] retSquare = new int[getRank() * getRank()];
     for (int e = 0; e < retSquare.length; e++) {
       retSquare[e] = getBoard()[n/getRank()][n%getRank()][e];
     }
     return retSquare;
   }//end getSquare

   public boolean isFull() {
     for (int r = 0; r < getBoard().length; r++) {
       for (int c = 0; c < getBoard()[r].length; c++) {
         for (int e = 0; e < getBoard()[r][c].length; e++) {
           if (getEl(r, c, e) == 0) {
             return false;
           }
         }
       }
     }
     return true;
   }//end isFull

   public boolean isCorrect() {
     if (!isFull()) return false;
     int mustEqual = ((getRank() * getRank()) * (1 + getRank() * getRank())) / 2;
     //returns true if every col has all the nums, every row has all the nums, and every square has all the nums
     for (int i = 0; i < getRank() * getRank(); i++) {
       if (sum(getRow(i)) != mustEqual || sum(getCol(i)) != mustEqual || sum(getSquare(i)) != mustEqual) {
         return false;
       }
     }
     return true;
   }//end isCorrect

   public int set(int r, int c, int e, int newVal) {
     int oldVal = getBoard()[r][c][e];
     getBoard()[r][c][e] = newVal;
     return oldVal;
   }//end set

   public int sum(int[] nums) {
     int ret = 0;
     for (int i : nums) { ret += i; }
     return ret;
   }

   public String toString() {
     int[] tempRow;
     int rowCounter = 0;
     String ret = "";
     for (int i = 0; i < 2 * (getRank() * getRank() + (getRank() + 1)) - 1; i++) {
       ret += "-";
     }
     ret += "\n";
     for (int row = 0; row < getRank() * getRank(); row++) {
       tempRow = getRow(row);
       ret += "| ";
       for (int i = 0; i < tempRow.length; i++) {
         if (tempRow[i] != 0) {
           ret += tempRow[i] + " ";
         }
         else {
           ret += "  ";
         }
         if ((i + 1) % getRank() == 0) {
           ret += "| ";
         }
       }
       if ((row + 1) % getRank() == 0) {
         ret += "\n";
         for (int i = 0; i < 2 * (getRank() * getRank() + (getRank() + 1)) - 1; i++) {
           ret += "-";
         }
       }
       ret += "\n";
     }
     return ret;
   }//end toString

   public static void main(String[] args) {
     //sampleBoard corresponds with sampleBoard.JPG
     //also note that sampleBoard has 2 solutions
     Board b = new Board("sampleBoard");
     System.out.println(b);

     System.out.println("Full: " + b.isFull() + "\n\n");

     //Testing getRow

     System.out.println("====================\nRows\n====================");

     int[] row0 = b.getRow(0);
     System.out.print("Row 0: ");
     for (int i : row0) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     int[] row1 = b.getRow(1);
     System.out.print("Row 1: ");
     for (int i : row1) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     int[] row2 = b.getRow(2);
     System.out.print("Row 2: ");
     for (int i : row2) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     int[] row3 = b.getRow(3);
     System.out.print("Row 3: ");
     for (int i : row3) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     //Testing getCol

     System.out.println("====================\nCols\n====================");

     int[] col0 = b.getCol(0);
     System.out.print("Col 0: ");
     for (int i : col0) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     int[] col1 = b.getCol(1);
     System.out.print("Col 1: ");
     for (int i : col1) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     int[] col2 = b.getCol(2);
     System.out.print("Col 2: ");
     for (int i : col2) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     int[] col3 = b.getCol(3);
     System.out.print("Col 3: ");
     for (int i : col3) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     System.out.println("====================\nSquares\n====================");

     int[] square00 = b.getSquare(0);
     System.out.println("Square 0: ");
     for (int i : square00) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     int[] square01 = b.getSquare(1);
     System.out.println("Square 1: ");
     for (int i : square01) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     int[] square10 = b.getSquare(2);
     System.out.println("Square 2: ");
     for (int i : square10) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     int[] square11 = b.getSquare(3);
     System.out.println("Square 3: ");
     for (int i : square11) {
       System.out.print(i + " ");
     }
     System.out.println("\n");

     System.out.println("====================\nCorrectness\n====================");

     System.out.println(b + "Correct: " + b.isCorrect());

     b.set(0, 0, 1, 2);
     b.set(0, 0, 2, 3);
     b.set(0, 0, 3, 4);
     b.set(0, 1, 0, 4);
     b.set(0, 1, 1, 3);
     b.set(0, 1, 3, 1);
     b.set(1, 0, 0, 4);
     b.set(1, 0, 2, 2);
     b.set(1, 0, 3, 1);
     b.set(1, 1, 0, 1);
     b.set(1, 1, 1, 2);
     b.set(1, 1, 2, 3);
     b.set(1, 1, 3, 4);

     System.out.println("\n" + b + "Correct: " + b.isCorrect());

   }//end main

 }//end class
