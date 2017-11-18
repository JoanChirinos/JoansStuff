/*
       __                     ________    _      _                 
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  

~Joan Chirinos, November 18, 2017
*/


public class TestChoices {

    public static void about() {
	System.out.println("\nThe purpose of this class is to explore the age "+
			   "old question: Always pick C, or pick randomly?\n"+
			   "This class creates a \"Test\", which is a list of"+
			   " 100 million \"answer choices\". These answer "+
			   "choices are numbers from 0 to 3, which represent "+
			   "A to D.\nThen, 2 \"students\" guess for every "+
			   "question on the test.\nStudent \"same\" chooses "+
			   "one random letter and guesses that for the whole"+
			   " test.\nStudent \"random\" chooses one random "+
			   "letter per question and guesses that.\nThen "+
			   "the percentage of correct answers are printed");
    }

    //This method populates the test with random answer choices
    public static int[] populateTest(int[] test) {
	for (int i = 0; i < test.length; i++) {
	    test[i] = (int) (Math.random() * 4);
	}
	return test;
    }

    public static void main(String[] args) {

	about();

	//Creates a test with 100 million questions
	int numOfQuestions = 100000000;
	int[] test = new int[numOfQuestions];
	test = populateTest(test);

	/*==============================
	 * Student "same"
	 ==============================*/
	
	//Student "same" picks one answer choice to guess for the whole test
	int sameGuess = (int) (Math.random() * 4);

	//How many times "same" guesses correctly
	int sameCorrect = 0;

	//"same" goes through the test and guesses
	for(int i : test) {
	    if (sameGuess == i)
		sameCorrect++;
	}

	
	/*==============================
	 * Student "random"
	 ==============================*/
	
	//I don't give randomGuess a value because it will change every time
	//"random" guesses
	int randomGuess;

	//How many times "random" guesses correctly
	int randomCorrect = 0;

	//"random" goes through the test and guesses
	for (int i : test) {
	    randomGuess = (int) (Math.random() * 4);
	    if (randomGuess == i)
		randomCorrect++;
	}

	/*==============================
	 * Here we do mathy stuff and  print out the percentage correct
	 ==============================*/

	//Initialize the values where their percentages will be stored
	double samePercent;
	double randomPercent;

	//Do mathy stuff to find % of correct answers
	samePercent = ((sameCorrect / (double) numOfQuestions) * 100);
	randomPercent = ((randomCorrect / (double) numOfQuestions) * 100);

	//Print them out
	System.out.println("\n========================================");
	System.out.println("\n\tSame: " + samePercent + "%");
	System.out.println("\tRandom: " + randomPercent + "%\n");

	System.out.println("See code for conclusions drawn " +
			   "and other information\n");
    }

}//End class

/*
 * Conclusion:
 * If the test is completely random and your choice is either one letter OR
 * also completely random, it doesn't matter if you pick one letter for the
 * whole test, or a random letter per question. You should still get about
 * 25% correct.
 *
 * Drawback of Experimental Procedure:
 * 1. The correct test choices are random. If your teacher has a program to
 *    random-ify their test choices, then it's all good. Otherwise, your
 *    teacher may have a subconcious bias towards one choice. In that case,
 *    choosing randomly should still get you a 25%, but choosing only 1 choice
 *    could get you a little more than 25%, or a little less than 25%.
 *
 * 2. In the same way, your choices on a real test aren't truly random. You
 *    likely have a subconcious bias towards one letter. However, I'm not sure
 *    how that would affect your score
 */
