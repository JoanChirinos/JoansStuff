/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, January 24, 2018
*/

/**********************************************************************

 **********************************************************************/

 import java.util.ArrayList;
 import jutils.*;

 public class TicTacToe {

   private ArrayList<ArrayList<String>> _board;

   public TicTacToe() {
     _board = new ArrayList<ArrayList<String>>();
     _board.add(new ArrayList<String>());
     for (int i = 0; i < 2; i++) {
       _board.add(new ArrayList<String>());
       _board.get(i).add("_");
       _board.get(i).add("|");
       _board.get(i).add("_");
       _board.get(i).add("|");
       _board.get(i).add("_");
     }
     _board.add(new ArrayList<String>());
     _board.get(2).add(" ");
     _board.get(2).add("|");
     _board.get(2).add(" ");
     _board.get(2).add("|");
     _board.get(2).add(" ");
   }//end constructor

   public boolean playTurn() {

   }//end playTurn

   public String toString() {
     String ret = "";
     for (int r = 0; r < _board.size(); r++) {
       for (int c = 0; c < _board.get(r).size(); c++) {
         ret += _board.get(r).get(c);
       }
       ret += "\n";
     }
     return ret;
   }//end toString

   public static void main(String[] args) {
     TicTacToe board = new TicTacToe();
     System.out.println(board);
   }//end main

 }//end class
