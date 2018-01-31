public class Cell {

  private String _state;

  public Cell() {
    _state = " ";
  }//end Cell()

  public Cell(String newState) {
    _state = newState;
  }//end Cell(String)

  public String getState() {
    return _state;
  }//end getState

  public String setState(String newState) {
    String oldState = getState();
    _state = newState;
    return oldState;
  }//end setState

  public String toString() {
    return getState();
  }//end toString

}//end class
