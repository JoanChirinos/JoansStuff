/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, January 23, 2018
*/

/**********************************************************************

 **********************************************************************/

 import java.util.ArrayList;
 import jutils.*;

 public class TestGrapher {

   public static void main(String[] args) {
     Grapher g = new Grapher();
     g.graph("x^(2) - 5");
     g.display();
   }//end main

 }//end class
