import jutils.Keyboard;

public class ThisAndThat {

    public static void main(String[] args) {

	int counter = 1;

	while (true) {

	    System.out.print("This\t");
	    for (int i = 0; i < counter; i++)
		System.out.print("and\t");
	    System.out.println("that.");
	    Keyboard.readString();
	    counter = counter * 4 + 1;
	    
	}

    }
    
}
