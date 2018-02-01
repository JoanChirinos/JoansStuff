public class Cell {

  private int _state;

  public Cell() {
    _state = 0;
  }//end Cell()

  public Cell(int newState) {
    _state = newState;
  }//end Cell(String)

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
