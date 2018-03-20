/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, March 19, 2018
*/

/**********************************************************************
  A simple and super basic compliment detector running off of command line input
 **********************************************************************/

import jutils.*;
import java.util.concurrent.TimeUnit;

 public class ComplimentDetector {

   public static int detect(String x) {
     //starting off at 50/100
     int complimentLevel = 50;

     if ((x.substring(x.length() - 1, x.length())).equals(".")) complimentLevel = 45;

     String[] slist = x.split("\\s+");
     int notCounter = 0;
     int baseScore = 20;
     for (String s : slist) {

       if (isPosMod(s)) {
         baseScore += 5;
       }

       else if (isANot(s)) {
         notCounter++;
       }

       else if (isPresent("compliments", s)) {
         if (notCounter % 2 == 0) {
           complimentLevel += baseScore;
         }
         else {
           complimentLevel -= baseScore;
         }

         //resetting base vals
         notCounter = 0;
         baseScore = 5;
       }

       else if (isPresent("insults", s)) {
         if (notCounter % 2 != 0) {
           complimentLevel += baseScore;
         }
         else {
           complimentLevel -= baseScore;
         }

         //resetting base vals
         notCounter = 0;
         baseScore = 5;
       }
     }
     return complimentLevel;
   }//end detect

   public static void addPositive(String x) {
     if (x.split("\\s+").length != 1) {
       System.out.println("==============\n Invalid word \n==============");
       sleep(2);
     }
     else if (!(isPresent("compliments", x))) {
       FileRW.write(FileRW.read("compliments") + "\n" + x, "compliments");
       System.out.println("=====================\n Addition Successful \n=====================");
       sleep(4);
     }
     else {
       System.out.println("======================\n Word Already Present \n======================");
       sleep(2);
     }
   }//end addPositive

   public static void addNegative(String x) {
     if (x.split("\\s+").length != 1) {
       System.out.println("==============\n Invalid word \n==============");
       sleep(2);
     }
     else if (!(isPresent("insults", x))) {
       FileRW.write(FileRW.read("insults") + "\n" + x, "insults");
       System.out.println("===================\nAddition Successful\n===================");
       sleep(4);
     }
     else {
       System.out.println("====================\nWord Already Present\n====================");
       sleep(2);
     }
   }//end addPositive

   public static boolean isPresent(String fileName, String s) {
     String[] f = FileRW.read(fileName).split("\n");
     for (String x : f) {
       if (x.equals(s)) return true;
     }
     return false;
   }//end isPresent

   public static boolean isANot(String x) {
     String[] nots = {"not", "dont", "don't", "aren't"}; //feel free to add more negative modifiers
     for (String s : nots) {
       if (s.equals(x)) return true;
     }
     return false;
   }//end isANot

   public static boolean isPosMod(String x) {
     String[] pmods = {"very", "super", "greatly", "amazingly", "really"}; //feel free to add more positive modifiers
     for (String s : pmods) {
       if (x.equals(s)) return true;
     }
     return false;
   }//end isPosMod

   public static void sleep(int seconds) {
     try {
       TimeUnit.SECONDS.sleep(seconds);
     }
     catch (InterruptedException ie) { }

   }//end sleep

   public static void main(String[] args) {
     System.out.print("\033[H\033[2J");
     while (true) {
       System.out.println("\n\nWould you like to...\n\t1. Test compliment level" +
                          "\n\t2. Add a positive adjective\n\t3. Add a negative adjective" +
                          "\n\t4. Exit");
      int choice = Keyboard.readInt();

      if (choice == 1) {
        System.out.println("Sentence to test:");
        String s = Keyboard.readString();
        System.out.println("\nCompliment level: " + detect(s.toLowerCase()) + "/100\n");
      }

      else if (choice == 2) {
        System.out.print("Positive adjective: ");
        addPositive(Keyboard.readString());
      }

      else if (choice == 3) {
        System.out.print("Negative adjective: ");
        addNegative(Keyboard.readString());
      }

      else if (choice == 4) {
        return;
      }

      else {
        System.out.println("Invalid choice");
        sleep(2);
        System.out.println("\033[H\033[2J");
      }
    }
  }//end main

}//end class
