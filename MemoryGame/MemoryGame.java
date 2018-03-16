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

public class MemoryGame {

  private Card[][] _board;
  //default board size: 10
  private int _boardSize = 6;
  private int _guessesNeeded;

  public MemoryGame() {
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

  public MemoryGame(int size) {
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

  public void go() {
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

      System.out.print("Card2\n\tRow: ");
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
        System.out.println("\n\tCongrats!");
      }
      else {
        System.out.println("Aw man, that's wrong!");
        _board[r1][c1].flipCard();
        _board[r2][c2].flipCard();
        sleep(3);
      }
    }
    System.out.println("\n\n\tSUCCESS!!!");
    sleep(1);
  }//end go

  public static void main(String[] args) {
    System.out.print("\033[H\033[2JBoard size: ");
    int bsize = Keyboard.readInt();
    System.out.print("\033[H\033[2J");
    MemoryGame g = new MemoryGame(bsize);
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
