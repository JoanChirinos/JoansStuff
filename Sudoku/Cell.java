import java.util.ArrayList;

public class Cell {

  private int _state;
  private ArrayList<Integer> possibleValues;

  public Cell(int rank) {
    _state = 0;
    possibleValues = new ArrayList<Integer>();
    for (int i = 0; i < rank * rank; i++) {
      possibleValues.add(i + 1);
    }
  }//end Cell()

  public Cell(int newState, int rank) {
    this(rank);
    _state = newState;
  }//end Cell(String)

  public void cantBe(int x) {
    for (int i = 0; i < possibleValues.size(); i++) {
      if (possibleValues.get(i) == x) {
        possibleValues.remove(i);
        return;
      }
    }
  }//end cantBe

  public ArrayList<Integer> getPossibleValues() {
    return possibleValues;
  }//end getPossibleValues

  public int getState() {
    return _state;
  }//end getState

  public int setState(int newState) {
    int oldState = getState();
    _state = newState;
    return oldState;
  }//end setState

  public String toString() {
    return "" + getState();
  }//end toString

  public boolean equals(int x) {
    return _state == x;
  }

}//end class
