/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, <DATE>
*/

/**********************************************************************
 * Board file for ScrabbleHelper. The board is a 2d String array where
 * blank spaces are " ".
 **********************************************************************/

 public class Board {

   public String[][] _board;
   public int _size;

   public Board(int size)
   {
     _board = new String[size][size];
     for (int r = 0; r < size; r++) {
       for (int c = 0; c < size; c++) {
         _board[r][c] = " ";
       }
     }
     _size = size;
   }

   //sets _board[r][c] to String setTo
   public String set(int r, int c, String setTo) {
     if (r < 0 || r >= _size || c < 0 || c >= _size) {
       throw new IndexOutOfBoundsException("\n\n\trow or column is invalid in setSpot()");
     }
     String old = _board[r][c];
     _board[r][c] = setTo;
     return old;
   }

   //adds a word to the board starting at (r, c) and moving down or right
   public boolean addWord(String word, int r, int c, String direction) {
     switch (direction) {
       //down case
       case "down":
         if (r + word.length() > _size) return false;
         for (int i = 0; i < word.length(); i++) {
           //if the word can't fit there, return false
           if (!(_board[r + i][c]).equals(" ") && !(_board[r + i][c]).equals(word.substring(i, i + 1))) return false;
         }
         //if it can, put it there
         for (int i = 0; i < word.length(); i++) {
           _board[r + i][c] = word.substring(i, i + 1);
         }
         break;

       //right case
       case "right":
         if (c + word.length() > _size) return false;
         for (int i = 0; i < word.length(); i++) {
           //if the word can't fit there, return false
           if (!(_board[r][c + i]).equals(" ") && !(_board[r][c + i]).equals(word.substring(i, i + 1))) return false;
         }
         //if it can, put it there
         for (int i = 0; i < word.length(); i++) {
           _board[r][c + i] = word.substring(i, i + 1);
         }
         break;
     }
     return true;
   }

   public String toString() {
     String ret = "";
     for (int r = 0; r < _size; r++) {
       for (int c = 0; c < _size; c++) {
         ret += String.format("%2s", _board[r][c]);
       }
       ret += "\n";
     }
     return ret;
   }

   public static void main(String[] args) {
     Board b = new Board(11);
     b.addWord("hello", 0, 0, "down");
     b.addWord("old", 4, 0, "right");
     b.addWord("dude", 2, 2, "down");
     System.out.println(b);
   }

 }//end Board
