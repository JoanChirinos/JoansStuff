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

import jutils.Keyboard;
import jutils.FileRW;

 public class ScrabbleHelper {

   public static void findBest(String[][] b, String[] rack) {
     //find all the tiles with space around them and try to build a word with that
     //at the beginning, end, and inside the word. Probably some sort of method that
     //tries words according to length of word (and measures points)

     //maybe split each dictionary into its own folder with a 1.txt, 2.txt,...
     //to 7.txt, 8.txt, and so on so that we have an easy way to search through
     //a certain word length without parsing through the whole list. It's also faster
     //to hard-build that than construct it every time we need to search
   }

   public static void main(String[] args) {
     System.out.println("I'm playing\n\t1. Scrabble\n\t2. Words With Friends");
     int game = Keyboard.readInt();
     String[] words;
     if (game == 1) {
       words = FileRW.read("dict.txt").split("\n");
     }
     else if (game == 2) {
       words = FileRW.read("wwf.txt").split("\n");
     }
     else {
       System.out.println("For serious? xdd");
       return;
     }
     System.out.print("Board size: ");
     int size = Keyboard.readInt();
     if (size > 30 || size < 1) {
       System.out.println("Literally no");
       return;
     }
     Board b = new Board(size);
     int choice;
     while (true) {
       System.out.println(b);
       System.out.println("Choose\n\t1. Add word\n\t2. Find best move\n\t3. Bye!");
       choice = Keyboard.readInt();
       if (choice == 1) {
         System.out.print("Word: ");
         String word = Keyboard.readString();
         System.out.print("Word starting point:\n\tRow: ");
         int row = Keyboard.readInt();
         System.out.print("\tColumn: ");
         int col = Keyboard.readInt();
         System.out.println("Word is going\n\t1. Down\n\t2. Right");
         String dir = Keyboard.readString();
         if (dir.equals("1")) dir = "down";
         else if (dir.equals("2")) dir = "right";
         b.addWord(word, row, col, dir);
       }
       else if (choice == 2) {
         System.out.print("Rack (_ for blank tiles): ");
         String[] rack = Keyboard.readString().split("");
       }
       else if (choice == 3) {
         return;
       }
     }
   }

 }//end class
