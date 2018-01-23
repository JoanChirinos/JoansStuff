/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, January 23, 2018
*/

/**********************************************************************
 * Driver for Grapher.java
 **********************************************************************/

 import jutils.*;

 public class GraphDriver {
   public static void main(String[] args) {
     System.out.println("\033[H\033[2JWelcome to Joan's Graphing calculator\n" +
     "As of right now, this calculator supports\n" +
     "\t* Addition --> +\n\t* Subtraction --> -\n\t" +
     "* Multiplication --> *\n\t* Division --> /\n\t" +
     "* Exponents --> a^(b)");
     Grapher g = new Grapher();
     while (true) {

       System.out.println("What would you like to do?\n\t1. Graph equation\n\t" +
       "2. Display Graph\n\t3. Exit");
       int choice = Keyboard.readInt();
       if (choice == 1)  {
         System.out.println("\033[H\033[2JEnter equation in terms of x");
         System.out.print("y = ");
         String equation = Keyboard.readString();
         g.graph(equation);
       }
       else if (choice == 2) {
         System.out.println("\033[H\033[2J");
         g.display();
       }
       else {
         return;
       }
     }
   }//end main
 }//end class
