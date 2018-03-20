/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, March 15, 2018
*/

/**********************************************************************
A memory game with the cards and flipping and whatnot
**********************************************************************/

import jutils.*;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class MemoryGame {

  private Card[][] _board;
  //default board size: 10
  private int _boardSize = 6;
  private int _guessesNeeded;
  private String _username;
  public int _guesses;

  /*
  public MemoryGame() {
    _guesses = 0;
    _guessesNeeded = _boardSize * _boardSize / 2;
    _board = new Card[_boardSize][_boardSize];
    String[] possibleVals = new String[_boardSize / 2];
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ             ";
    for (int i = 0; i < possibleVals.length; i++) {
      possibleVals[i] = alpha.charAt((int)(Math.random() * alpha.length())) +
                        alpha.charAt((int)(Math.random() * alpha.length())) +
                        alpha.charAt((int)(Math.random() * alpha.length())) + "";
    }
    for (int r = 0; r < _board.length; r++) {
      for (int c = 0; c < _board.length; c++) {
        _board[r][c] = new Card(possibleVals[(c * _boardSize + r) / 2]);
      }
    }
  }//end default constructor
  */

  public MemoryGame(int size, String newName) {
    _username = newName;
    _guesses = 0;
    if (size % 2 != 0) {
      size++;
      System.out.println("\033[H\033[2JInvalid board size. Using size " + size);
      sleep(1);
      System.out.print("\033[H\033[2J");
    }
    if (size < 1) {
      size = _boardSize;
      System.out.println("\033[H\033[2JInvalid board size. Using size " + size);
      sleep(1);
      System.out.print("\033[H\033[2J");
    }
    _boardSize = size;
    _guessesNeeded = _boardSize * _boardSize / 2;
    _board = new Card[_boardSize][_boardSize];
    String[] possibleVals = new String[(_boardSize * _boardSize) / 2];
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for (int i = 0; i < possibleVals.length; i++) {
      int one = (int)(Math.random() * alpha.length());
      int two = (int)(Math.random() * alpha.length());
      int three = (int)(Math.random() * alpha.length());
      possibleVals[i] = alpha.substring(one, one + 1) +
                        alpha.substring(two, two + 1) +
                        alpha.substring(three, three + 1);
    }
    for (int r = 0; r < _board.length; r++) {
      for (int c = 0; c < _board.length; c++) {
        _board[r][c] = new Card(possibleVals[(r * _boardSize + c) / 2]);
      }
    }
  }//end overloaded

  public void shuffle() {
    for (int i = 0; i < _boardSize * _boardSize * 2; i++) {
      int r1 = (int)(Math.random() * _boardSize);
      int c1 = (int)(Math.random() * _boardSize);
      int r2 = (int)(Math.random() * _boardSize);
      int c2 = (int)(Math.random() * _boardSize);
      Card temp = _board[r1][c2];
      _board[r1][c2] = _board[r2][c2];
      _board[r2][c2] = temp;
    }
  }//end shuffle

  public String toString() {
    String ret = "   ";
    for (int i = 0; i < _boardSize; i++) {
      ret += "   " + String.format("%3.3s", i + "");
    }
    ret += "\n";
    for (int r = 0; r < _boardSize; r++) {
      ret += String.format("%3.3s", r + "") + " | ";
      for (int c = 0; c < _boardSize; c++) {
        ret += _board[r][c] + " | ";
      }
      ret += "\n\n";
    }
    ret += "\tGuesses so far: " + this._guesses;

    return ret;
  }//end toString

  public boolean guess(Card c1, Card c2) {
    return c1.equals(c2);
  }//end guess

  public void sleep(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    }
    catch (InterruptedException ie) { }

  }//end sleep

  public void writeHighScores(String whereTo) {
    FileRW.write("100,Dave\n101,David\n102,Davido\n103,Davidson\n104,Daviddotter", whereTo);
  }//end writeHighScores(String)

  public void writeHighScores() {
    ArrayList<String[]> hs = new ArrayList<String[]>();
    String[] hslist = FileRW.read("hs/" + _boardSize).split("\n");
    for (String s : hslist) {
      String[] listo = s.split(",");
      hs.add(listo);
    }
    for (int i = 0; i < hs.size(); i++) {
      if (_guesses < Integer.parseInt(hs.get(i)[0])) {
        String[] temp = {_guesses + "", _username};
        hs.add(i, temp);
        break;
      }
    }
    String toWrite = "";
    for (String[] s : hs) {
      toWrite += String.join(",", s) + "\n";
    }
    FileRW.write(toWrite, "hs/" + _boardSize);
  }//end writeHighScores(String, String)

  public String getHighScores() {
    String ret = "High scores for a " + _boardSize + "x" + _boardSize + "board:\n";
    try {
      String[] scores = FileRW.read("hs/" + _boardSize).split("\n");
      for (int i = 0; i < 5; i++) {
        String[] hs = scores[i].split(",");
        ret += "\n\t" + (i + 1) + ". " + String.format("%10.10s", hs[1]) + ": " + String.format("%3.3s", hs[0]);
      }
      return ret;
    }
    catch (IllegalArgumentException e) {
      writeHighScores("hs/" + _boardSize);
      return getHighScores();
    }
    catch (Exception e) {
      throw new IllegalArgumentException("\n\n\tSomething went wrong when reading your high scores");
    }
  }

  public void go() {
    System.out.println("\033[H\033[2J" + getHighScores());
    sleep(5);
    System.out.print("\033[H\033[2J");
    this.shuffle();
    System.out.println(this + "\n\nYou have about 15 seconds to memorize the board");
    for (int r = 0; r < _boardSize; r++) {
      for (Card c : _board[r]) {
        c.flipCard();
      }
    }
    sleep(15);
    System.out.print("\033[H\033[2J");
    while (_guessesNeeded > 0) {
      System.out.print("\033[H\033[2J");
      System.out.println(this);
      System.out.print("\nGuess:\nCard1\n\tRow: ");
      int r1 = Keyboard.readInt();
      System.out.print("\tColumn: ");
      int c1 = Keyboard.readInt();
      try {
        _board[r1][c1].flipCard();
        System.out.print("\033[H\033[2J" + this);
      }
      catch (Exception e) {
        System.out.println("Invalid row/column");
        sleep(2);
        continue;
      }

      System.out.print("\nCard2\n\tRow: ");
      int r2 = Keyboard.readInt();
      System.out.print("\tColumn: ");
      int c2 = Keyboard.readInt();

      try {
        _board[r2][c2].flipCard();
        System.out.print("\033[H\033[2J" + this);
      }
      catch (Exception e) {
        System.out.println("Invalid row/column");
        sleep(2);
        continue;
      }
      if (_board[r1][c1].equals(_board[r2][c2])) {
        _guessesNeeded--;
        _board[r1][c1]._canBeFlipped = false;
        _board[r2][c2]._canBeFlipped = false;
        System.out.println("\n\n\tCongrats!");
        sleep(2);
      }
      else {
        System.out.println("\n\n\tAw man, that's wrong!");
        _board[r1][c1].flipCard();
        _board[r2][c2].flipCard();
        sleep(3);
      }
      this._guesses += 1;
    }
    writeHighScores();
    int guessesAway = (this._guesses - (_boardSize * _boardSize / 2));
    if (guessesAway == 0) {
      System.out.println("\033[H\033[2J\nSUCCESS!!!\n\nYou played a perfect game!");
    }
    else if (guessesAway == 1) {
      System.out.println("\033[H\033[2J\n\t\tSUCCESS!!!\n\nYou were " + guessesAway + " guess away from a perfect game. Keep it up!");
    }
    else System.out.println("\033[H\033[2J\n\t\tSUCCESS!!!\n\nYou were " + guessesAway + " guesses away from a perfect game. Keep it up!");

    System.out.println("\n" + getHighScores());
    sleep(3);
  }//end go

  public static void main(String[] args) {
    System.out.print("\033[H\033[2JName: ");
    String name = Keyboard.readString();
    System.out.print("\033[H\033[2JBoard size: ");
    int bsize = Keyboard.readInt();
    System.out.print("\033[H\033[2J");
    MemoryGame g = new MemoryGame(bsize, name);
    g.go();
  }

}//end class MemoryGame









class Card {

  private String _cardVal;
  private boolean _flipped;
  public boolean _canBeFlipped;

  public Card(String newVal) {
    _cardVal = newVal;
    _flipped = true;
    _canBeFlipped = true;
  }//end constructor

  public String getCardVal() {
    return _cardVal;
  }//end getCardVal

  public String toString() {
    if (_flipped) {
      return _cardVal;
    }
    return "   ";
  }//end toString

  public void flipCard() {
    if (_canBeFlipped) {
      _flipped = !(_flipped);
    }
    else throw new IllegalArgumentException();
  }

  public boolean equals(Card c) {
    return this.getCardVal().equals(c.getCardVal());
  }//end equals

}//end class Card
