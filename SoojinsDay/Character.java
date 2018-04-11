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

 public class Character {

   //instance vars
   private String _name;
   private int _happyPoints;

   public Character(String newName) {
     _name = newName;
     _happyPoints = 50;
   }

   //accessors
   public int getHappyPoints() {
     return _happyPoints;
   }

   public String getName() {
     return _name;
   }

   //mutators
   public int setHappyPoints(int newVal) {
     int oldVal = _happyPoints;
     _happyPoints = newVal;
     return oldVal;
   }

   public int changeName(String newName) {
     String oldName = _name;
     _name = newName;
     return oldName;
   }

 }//end public class
