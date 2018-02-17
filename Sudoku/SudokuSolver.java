import jutils.*;

/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, <DATE>
*/

/**********************************************************************

 **********************************************************************/

 public class SudokuSolver {

   private Board _b;

   public SudokuSolver(String loadFrom) {
     _b = new Board(loadFrom);
   }//end constructor

   public void solve() {
     while (!(_b.isFull())) {
       System.out.println(_b);
       for (int r = 0; r < _b.getRank() * _b.getRank(); r++) {
         for (Cell c  : _b.getRow(r)) {
           for (Cell el : _b.getRow(r)) {
             el.cantBe(c.getState());
           }
         }
         for (Cell c : _b.getCol(r)) {
           for (Cell el : _b.getCol(r)) {
             el.cantBe(c.getState());
           }
         }
         for (Cell c : _b.getSquare(r)) {
           for (Cell el : _b.getSquare(r)) {
             el.cantBe(c.getState());
           }
         }
       }
       for (int r = 0; r < _b.getRank() * _b.getRank(); r++) {
         for (Cell el : _b.getRow(r)) {
           if (el.getPossibleValues().size() == 1) {
             el.setState(el.getPossibleValues().get(0));
           }
         }
       }
     }
   }//end solve

   public String toString() {
     return _b.toString();
   }//end toString



   public static void main(String[] args) {
     System.out.print("Board to solve: ");
     String choice = Keyboard.readString();
     SudokuSolver ss = new SudokuSolver(choice);
     System.out.println(ss);
     ss.solve();
     System.out.println("\n\n" + ss);
   }//end main

 }//end class
