package jutils;

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

 public class CalcUtil {

   public static double mathify(String expression) {
     expression = " " + expression;
     String[] sides;
     if (expression.indexOf("^(") != -1) {
       sides = new String[3];
       sides[0] = expression.substring(0, expression.lastIndexOf(" ", expression.indexOf("^(")));
       sides[1] = expression.substring(expression.lastIndexOf(" ", expression.indexOf("^(")), 1 + expression.indexOf(")", expression.indexOf("^(")));
       sides[2] = expression.substring(expression.indexOf(")", expression.indexOf("^(")) + 1);

       System.out.println("To solve: " + expression);
       expression = " " + expression;

       String[] sides;


       if (expression.indexOf("^(") != -1) {
         System.out.println("Raising");
         sides = new String[3];
         sides[0] = expression.substring(0, expression.lastIndexOf(" ", expression.indexOf("^(")));
         System.out.println("0: " + sides[0]);
         sides[1] = expression.substring(expression.lastIndexOf(" ", expression.indexOf("^(")), 1 + expression.indexOf(")", expression.indexOf("^(")));
         System.out.println("1 " + sides[1]);
         sides[2] = expression.substring(expression.indexOf(")", expression.indexOf("^(")) + 1);
         System.out.println("2: " + sides[2]);
         double base = Double.parseDouble(sides[1].split("\\^\\(")[0]);
         String power = sides[1].split("\\^\\(|\\)")[1];
         return mathify(sides[0] + " " + Math.pow(base, mathify(power)) + " " + sides[2]);
       }
       else if (expression.indexOf(" + ") != -1) {

         System.out.println("Adding");

         sides = expression.split(" \\+ ", 2);
         return mathify(sides[0]) + mathify(sides[1]);
       }
       else if (expression.indexOf(" - ") != -1) {

         System.out.println("Subtracting");

         sides = expression.split(" - ", 2);
         return mathify(sides[0]) - mathify(sides[1]);
       }
       else if (expression.indexOf(" * ") != -1) {
         System.out.println("Multiplying");
         sides = expression.split(" \\* ", 2);
         return mathify(sides[0]) * mathify(sides[1]);
       }
       else if (expression.indexOf(" / ") != -1) {
         System.out.println("Dividing");
         sides = expression.split(" \\/ ", 2);
         return mathify(sides[0]) / mathify(sides[1]);
       }
       else {
         try {
           return Double.parseDouble(expression);
         }
         catch (NumberFormatException e) {
           throw new IllegalArgumentException("\n\n\tInvalid equation. See README\n");
         }
         catch (Exception e) {
           throw new IllegalArgumentException("\n\tSomething went wrong");
         }
       }
     }//end mathify

   }//end class

 }
