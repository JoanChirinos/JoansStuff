/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, 2-27-2018
*/

/**********************************************************************
* A flashcard application
**********************************************************************/

import jutils.*;
import java.util.ArrayList;
import java.io.*;

public class Quizlit {

  public String _activeSet;

  public Quizlit(String setName) {
    _activeSet = setName;
  }

  public static void newSet(String setName) {
    int choice = 0;
    String workingSet = "";
    FileRW.write("", "Sets/" + setName);
    FileRW.write(FileRW.read("set.names") + "\n" + setName, "set.names");
    while (true) {
      System.out.println("\n\t1. Enter card\n\t2. Exit");
      try {
        choice = Integer.parseInt(Keyboard.readString());
        if (choice != 1 && choice != 2) {
          System.out.println("That's an invalid choice\n\n");
        }
        else break;
      }
      catch (Exception e) {
        System.out.println("That's an invalid choice\n\n");
      }
      System.out.println("Your choice is " + choice);
      if (choice == 2) {
        FileRW.write(workingSet, "Sets/" + setName);
        return;
      }
      else {
        System.out.print("Term: ");
        String term = Keyboard.readString();
        System.out.print("Definition: ");
        String def = Keyboard.readString();
        workingSet += term + "," + def + "\n";
      }
    }
  }//end newSet

  public void addToSet() {
    int choice = 0;
    String toWrite = FileRW.read("Sets/" + _activeSet);
    while (true) {
      System.out.println("\n\t1. Enter card\n\t2. Done");
      try {
        choice = Integer.parseInt(Keyboard.readString());
        if (choice != 1 && choice != 2) {
          System.out.println("That's an invalid choice\n\n");
        }
        else break;
      }
      catch (Exception e) {
        System.out.println("That's an invalid choice\n\n");
      }
      if (choice == 2) {
        FileRW.write(toWrite, "Sets/" + _activeSet);
        return;
      }
      else {
        System.out.print("Term: ");
        String term = Keyboard.readString();
        System.out.print("Definition: ");
        String def = Keyboard.readString();
        toWrite += term + "," + def + "\n";
      }
    }
  }//end addToSet

  public void study() {
    int choice;
    while (true) {
      System.out.println("Display\n\t1. Terms\n\t2. Definitions\n\t3. Either");
      try {
        choice = Integer.parseInt(Keyboard.readString());
        if (choice != 1 && choice != 2 && choice != 3) {
          System.out.println("That's an invalid choice\n\n");
        }
        else break;
      }
      catch (Exception e) {
        System.out.println("That's an invalid choice\n\n");
      }
    }

    if (choice == 1) {
      System.out.println("Enter any character and press enter to go to the next card\n");
      String[] set = FileRW.read("Sets/" + _activeSet).split("\n");
      set = shuffle(set);
      for (String s : set) {
        String[] card = s.split(",");
        System.out.println("Term: " + card[0]);
        Keyboard.readString();
        System.out.println("Definition: " + card[1]);
      }
    }
    if (choice == 2) {
      System.out.println("Enter any character and press enter to go to the next card\n");
      String[] set = FileRW.read("Sets/" + _activeSet).split("\n");
      set = shuffle(set);
      for (String s : set) {
        String[] card = s.split(",");
        System.out.println("Definition: " + card[1]);
        Keyboard.readString();
        System.out.println("Term: " + card[0]);
      }
    }
    if (choice == 3) {
      System.out.println("Enter any character and press enter to go to the next card\n");
      String[] set = FileRW.read("Sets/" + _activeSet).split("\n");
      set = shuffle(set);
      for (String s : set) {
        String[] card = s.split(",");
        if ((int)(Math.random() * 2) == 0) {
          System.out.println("Term: " + card[0]);
          Keyboard.readString();
          System.out.println(card[1]);
        }
        else {
          System.out.println("Definition: " + card[1]);
          Keyboard.readString();
          System.out.println("Term: " + card[0]);
        }
      }
    }
  }//end study

  public static String[] shuffle(String[] set) {
    for (int i = 0; i < set.length * 2; i++) {
      int r1 = (int)(Math.random() * set.length);
      int r2 = (int)(Math.random() * set.length);

      String temp = set[r1];
      set[r1] = set[r2];
      set[r2] = temp;
    }
    return set;
  }

  public void setActions() {
    int choice;
    while (true) {
      while (true) {
        System.out.println("What would you like to do?\n\t1. Study\n\t2. Add to set\n\t3. Remove set\n\t4. Exit");
        try {
          choice = Integer.parseInt(Keyboard.readString());
          if (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
            System.out.println("That's an invalid choice\n\n");
          }
          else break;
        }
        catch (Exception e) {
          System.out.println("That's an invalid choice\n\n");
        }
      }
      if (choice == 4) return;
      if (choice == 3) {
        System.out.println("Are you sure? (y/n)");
        String leave = Keyboard.readString().toLowerCase();
        if (leave.equals("y")) {
          File f = new File("Sets/" + _activeSet);
          if (f.delete()) {
            System.out.println("Set successfully deleted");
            return;
          }
        }
        else {
          System.out.println("Set not deleted");
        }
      }

      if (choice == 2) {
        addToSet();
      }

      if (choice == 1) {
        study();
      }
    }

  }//end existingSet

  public static void run() {
    int choice;
    while (true) {
      while (true) {
        System.out.println("\n\nWhat would you like to do?\n\t1. Make a new flashcard set\n\t2. Open an existing set\n\t3. Exit");
        try {
          choice = Integer.parseInt(Keyboard.readString());
          if (choice != 1 && choice != 2 && choice != 3) {
            System.out.println("That's an invalid choice\n\n");
          }
          else break;
        }
        catch (Exception e) {
          System.out.println("That's an invalid choice\n\n");
        }
      }

      if (choice == 3) return;

      if (choice == 1) {
        System.out.print("New set name: ");
        String setName = Keyboard.readString();
        if (isASet(setName)) {
          System.out.println("That's an existing set. Overwrite it (y/n)?");
          String overwrite = Keyboard.readString().toLowerCase();
          if (overwrite.equals("y")) newSet(setName);
          else System.out.println("Not overwritten");
        }
        else {
          newSet(setName);
        }
      }

      else if (choice == 2) {
        System.out.print("Set name: ");
        String setName = Keyboard.readString();
        if (!isASet(setName)) {
          System.out.println("That's not a valid set name.\nPrint all set names (y/n)");
          String yn = Keyboard.readString().toLowerCase();
          if (yn.equals("y")) System.out.println(FileRW.read("set.names"));
        }
        else {
          Quizlit q = new Quizlit(setName);
          q.setActions();
        }
      }
    }

  }//end run

  public static boolean isASet(String setName) {
    String[] names = FileRW.read("set.names").split("\n");
    for (String s : names) {
      System.out.println(s);
      if (setName.equals(s)) return true;
    }
    return false;
  }//end isASet

  public static void main(String[] args) {

    System.out.println("Welcome to Quizlit");
    run();

  }//end main

}//end class
