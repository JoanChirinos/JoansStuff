/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, June 2018
*/

/**********************************************************************
A machine learning program to make words based off of a text
 **********************************************************************/

 import jutils.FileRW;
 import jutils.Keyboard;
 import java.util.ArrayList;

 public class WordMaker {

   private LetterTrees _tree;

   public WordMaker() {
     _tree = new LetterTrees();
   }

   public void learn(String path) {
     String[] words = FileRW.read(path).trim().replaceAll("[.,!@#$%^&*():;\"?_\\-\\[\\]”\\—/\\“\'‘¦]", "").split("\\s+");
     for (String word : words) {
       _tree.addWord(word);
     }
   }

   public String makeWord() {
     return _tree.getWord();
   }

   public String makeSentence() {
     int length = 21 + (int)(Math.random() * 10) - 5;
     String[] sentence = new String[length];
     for (int i = 0; i < length; i++) {
       sentence[i] = makeWord();
     }
     return String.join(" ", sentence) + ".";
   }

   public static void main(String[] args) {
     WordMaker wm = new WordMaker();
     while (true) {
       System.out.println("\n\t1. Learn\n\t2. Make word\n\t3. Make sentence\n\t4. Exit\n");
       int choice = Keyboard.readInt();
       if (choice == 1) {
         System.out.print("\n\nFilename: ");
         String path = Keyboard.readString();
         try {
           wm.learn(path);
         }
         catch (Exception e) {
           System.out.println("\nInvalid file name\n");
         }
       }
       else if (choice == 2) {
         System.out.println("Word:\n" + wm.makeWord());
       }
       else if (choice == 3) {
         System.out.println("Sentence:\n" + wm.makeSentence());
       }
       else if (choice == 4) {
         return;
       }
     }
   }

 }

 class LetterTrees {

   private ArrayList<LetterTreeNode> _nodes;

   public LetterTrees() {
     _nodes = new ArrayList<LetterTreeNode>();
   }

   public void addWord(String word) {
     char[] carr = word.toCharArray();
     LetterTreeNode nodeAt = getNode(carr[0]);
     for (int i = 1; i < carr.length; i++) {
       nodeAt = nodeAt.addBranch(carr[i]);
     }
   }

   public LetterTreeNode getNode(char l) {
     for (LetterTreeNode n : _nodes) {
       if (n.getLetter() == l) {
         return n;
       }
     }
     //if we're here, that node doesn't yet exist. Make it
     //System.out.println("Adding node " + l + " to LetterTrees");
     LetterTreeNode toAdd = new LetterTreeNode(l);
     _nodes.add(toAdd);
     return toAdd;
   }

   public String getWord() {
     String word = "";
     LetterTreeNode nodeAt = getRandomNode();
     //System.out.println("Got node " + nodeAt.getLetter());
     while (nodeAt != null) {
       word += nodeAt.getLetter();
       nodeAt = nodeAt.getRandomBranch();
       //System.out.println("Got node " + nodeAt.getLetter());
     }
     return word;
   }

   public LetterTreeNode getRandomNode() {
     //System.out.println("Start getRandomNode()");
     if (_nodes.size() == 0) {
       //System.out.println("Empty tree");
       return null;
     }
     //first get the sum of all the branches
     int sum = 0;
     for (LetterTreeNode n : _nodes) {
       sum += n.getSum();
     }
     //System.out.println("Sum = " + sum);
     //then pick a random int in that range
     int countTo = (int)(Math.random() * sum + 1);
     //System.out.println("Counting to " + countTo);
     //then iterate through node list and subtract from it until you get to count < 0
     //this is my way of having a sort of weighted randomness
     for (LetterTreeNode n : _nodes) {
       //System.out.println("At node |" + n.getLetter() + "|");
       countTo -= n.getSum();
       //System.out.println("Count is now " + countTo);
       if (countTo <= 0) {
         //return said node
         //System.out.println("Returning node |" + n.getLetter() + "|");
         return n;
       }
     }
     //if we're here, something's wrong
     //System.out.println("getRandomNode got a null node bro");
     return null;
   }

 }

 class LetterTreeNode {

   private char _letter;
   private ArrayList<LetterTreeNode> _branches;
   private int _letterSum;

   public LetterTreeNode(char newLetter) {
     _letter = newLetter;
     _letterSum  = 1;
     _branches = new ArrayList<LetterTreeNode>();
   }


   public LetterTreeNode addBranch(char l) {
     //try incrementing the count for an existing letter
     for (LetterTreeNode n : _branches) {
       if (n.getLetter() == l) {
         n.incrementCount();
         return n;
       }
     }
     //if node with char l doesn't exist, make it
     LetterTreeNode toAdd = new LetterTreeNode(l);
     _branches.add(toAdd);
     return toAdd;
   }

   public LetterTreeNode getRandomBranch() {
     if (_branches.size() == 0) {
       return null;
     }
     //first get the sum of all the branches
     int sum = 0;
     for (LetterTreeNode n : _branches) {
       sum += n.getSum();
     }
     //then pick a random int in that range
     int countTo = (int)(Math.random() * sum + 1);
     //then iterate through node list and subtract from it until you get to count < 0
     //this is my way of having a sort of weighted randomness
     for (LetterTreeNode n : _branches) {
       countTo -= n.getSum();
       if (countTo <= 0) {
         //return said node
         return n;
       }
     }
     //if we're here, something's wrong
     return null;
   }

   public char getLetter() {
     return _letter;
   }

   public void incrementCount() {
     _letterSum++;
   }

   public int getSum() {
     return _letterSum;
   }

 }
