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

 MUST have >= 2^(_rank) - 1 defined data values
 **********************************************************************/

import jutils.*;

 public class Board {

   private Cell[][][] _board;
   private int _rank;

   public Board(String loadFrom) {
     String r = FileRW.read(loadFrom);
     String[] data = r.split("\n");
     _rank = Integer.parseInt(data[0]);
     _board = new Cell[_rank][_rank][_rank * _rank];
     for (int row = 0; row < _rank; row++) {
       for (int col = 0; col < _rank; col++) {
         for (int el = 0; el < _rank * _rank; el++) {
           _board[row][col][el] = new Cell();
         }
       }
     }
     for (int i = 1; i < data.length; i++) {
       String[] temp = data[i].split(",");
       _board[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])][Integer.parseInt(temp[2])] = new Cell(Integer.parseInt(temp[3]));
     }
   }//end constructor

   public Cell[][][] getBoard() {
     return _board;
   }//end getBoard

   public int getRank() {
     return _rank;
   }

   public Cell getEl(int r, int c, int el) {
     return getBoard()[r][c][el];
   }//end getEl

   public Cell[] getRow(int r) {
     Cell[] retRow = new Cell[getRank() * getRank()];
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

   public Cell[] getCol(int c) {
     Cell[] retCol = new Cell[getRank() * getRank()];
     for (int r = 0; r < getRank() * getRank(); r++) {
        retCol[r] = getRow(r)[c];
     }
     return retCol;
   }//end getCol

   public Cell[] getSquare(int n) {
     Cell[] retSquare = new Cell[getRank() * getRank()];
     for (int e = 0; e < retSquare.length; e++) {
       retSquare[e] = getBoard()[n/getRank()][n%getRank()][e];
     }
     return retSquare;
   }//end getSquare

   public boolean isFull() {
     for (int r = 0; r < getBoard().length; r++) {
       for (int c = 0; c < getBoard()[r].length; c++) {
         for (int e = 0; e < getBoard()[r][c].length; e++) {
           if (getEl(r, c, e).equals(0)) {
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

   public int set(int r, int c, int e, Cell newVal) {
     int oldVal = getBoard()[r][c][e].getState();
     getBoard()[r][c][e] = newVal;
     return oldVal;
   }//end set

   public int sum(Cell[] nums) {
     int ret = 0;
     for (Cell i : nums) { ret += i.getState(); }
     return ret;
   }

   public String toString() {
     Cell[] tempRow;
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
         if (tempRow[i].getState() != 0) {
           ret += tempRow[i].getState() + " ";
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

     Cell[] row0 = b.getRow(0);
     System.out.print("Row 0: ");
     for (Cell i : row0) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     Cell[] row1 = b.getRow(1);
     System.out.print("Row 1: ");
     for (Cell i : row1) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     Cell[] row2 = b.getRow(2);
     System.out.print("Row 2: ");
     for (Cell i : row2) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     Cell[] row3 = b.getRow(3);
     System.out.print("Row 3: ");
     for (Cell i : row3) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     //Testing getCol

     System.out.println("====================\nCols\n====================");

     Cell[] col0 = b.getCol(0);
     System.out.print("Col 0: ");
     for (Cell i : col0) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     Cell[] col1 = b.getCol(1);
     System.out.print("Col 1: ");
     for (Cell i : col1) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     Cell[] col2 = b.getCol(2);
     System.out.print("Col 2: ");
     for (Cell i : col2) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     Cell[] col3 = b.getCol(3);
     System.out.print("Col 3: ");
     for (Cell i : col3) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     System.out.println("====================\nSquares\n====================");

     Cell[] square00 = b.getSquare(0);
     System.out.println("Square 0: ");
     for (Cell i : square00) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     Cell[] square01 = b.getSquare(1);
     System.out.println("Square 1: ");
     for (Cell i : square01) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     Cell[] square10 = b.getSquare(2);
     System.out.println("Square 2: ");
     for (Cell i : square10) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     Cell[] square11 = b.getSquare(3);
     System.out.println("Square 3: ");
     for (Cell i : square11) {
       System.out.print(i.getState() + " ");
     }
     System.out.println("\n");

     System.out.println("====================\nCorrectness\n====================");

     System.out.println(b + "Correct: " + b.isCorrect());

     b.set(0, 0, 1, new Cell(2));
     b.set(0, 0, 2, new Cell(3));
     b.set(0, 0, 3, new Cell(4));
     b.set(0, 1, 0, new Cell(4));
     b.set(0, 1, 1, new Cell(3));
     b.set(0, 1, 3, new Cell(1));
     b.set(1, 0, 0, new Cell(4));
     b.set(1, 0, 2, new Cell(2));
     b.set(1, 0, 3, new Cell(1));
     b.set(1, 1, 0, new Cell(1));
     b.set(1, 1, 1, new Cell(2));
     b.set(1, 1, 2, new Cell(3));
     b.set(1, 1, 3, new Cell(4));

     System.out.println("\n" + b + "Correct: " + b.isCorrect());

   }//end main

 }//end class
