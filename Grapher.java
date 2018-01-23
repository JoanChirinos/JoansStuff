/*
       __                     ________    _      _
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  )
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/

~Joan Chirinos, <DATE>
*/

/**********************************************************************

 **********************************************************************/

import java.util.ArrayList;
import jutils.*;

public class Grapher {

  //Instance Vars

<<<<<<< HEAD
  //data is { {xval, yval}, ... }
  private ArrayList<ArrayList<Double>> _data;

  //points { {row/ycor, column/xcor, value}, ... }
  private ArrayList<ArrayList<String>> _points;

  public Grapher() {
    _data = new ArrayList<ArrayList<Double>>();
    _points = new ArrayList<ArrayList<String>>();
  }

  //graphs in xrange[-10, 10) and yrange[10, 10) with a 0.5 step
  public void graph(String equation) {
    for (int i = 0; i < 40; i++) {
      _data.add(new ArrayList<Double>());
      _data.get(i).add(i * 0.5 - 10);
      _data.get(i).add(CalcUtil.mathify(equation.replace("x", "" + _data.get(i).get(0))));
    }
    for (int r = 0; r < 40; r++) {
      _points.add(new ArrayList<String>());
      for (int c = 0; c < 40; c++) {
        _points.get(r).add(" ");
      }
    }
    for (ArrayList<Double> i : _data) {
      int xc = (int)((i.get(0) + 10) * 2);
      int yr = (int)(20 - (Math.round(i.get(1) * 2)) - 1);
      try {
        _points.get(yr).set(xc, "*");
      }
      catch (Exception e) { }
    }
  }//end graph

  public void display() {
    if (_data.size() == 0) {
      throw new NullPointerException("\n\n\tMake sure to initialize the graph using graph(<equation>)\n");
    }
    for (int r = 0; r < _points.size(); r++) {
      for (int c = 0; c < _points.get(r).size(); c++) {
        System.out.print(_points.get(r).get(c));
      }
      System.out.println();
    }
  }//end display
=======
  //data is { {xval, yval}, {xval, yval}, ... }
  private ArrayList<ArrayList<Double>> _data;

  public Grapher() {
    _data = new ArrayList<ArrayList<Double>>();
  }

  public void graph(String equation) {

  }
>>>>>>> 3c58691debc03473264df71e361b51348c5b48f9

}//end class
