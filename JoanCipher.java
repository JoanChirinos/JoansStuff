/*
       __                     ________    _      _                 
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  

~Joan Chirinos, December 21, 2017
*/

import jutils.Keyboard;

public class JoanCipher {

    private int[] _key = new int[100];
    private String _stringKey = "";
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public JoanCipher() {
	//create a random array of key values and store
	for (int i = 0; i < 100; i++) {
	    int toAdd = (int)(Math.random() * 26);
	    if (("" + toAdd).length() == 1)
		_stringKey += "0" + toAdd;
	    else
		_stringKey += toAdd;
	    _key[i] = toAdd;
	}
    }

    public JoanCipher(String newKey) {
	
	//initialize a JoanCipher
	this();
	
	//if newKey is too short or long, throw exception
	if (newKey.length() != 200)
	    throw new IllegalArgumentException("Invalid key!\n" +
					       "Random key used");
	
	int[] keyUpdate = new int[100];
	
	for (int i = 0; i < 100; i++) {
	    int toAdd = Integer.parseInt((newKey.substring(i * 2,
							   i * 2 + 2)));
	    //if a key value thing is invalid, throw exception
	    if (toAdd < 0 || toAdd > 25)
		throw new IllegalArgumentException("Invalid key!\n" +
						   "Random key used");
	    keyUpdate[i] = toAdd;
	}
	
	//if above code runs, reset _stringKey
	_stringKey = "";
	
	for (int i = 0; i < 100; i++) {
	    //overwrite vals in _key to new ones in keyUpdate
	    _key[i] = keyUpdate[i];
	    _stringKey += "" + keyUpdate[i];
	}
    }

    //encrypts str using _key
    public String encrypt(String str) {
        String ret = "";
	for (int i = 0; i < str.length(); i++) {
	    String chr = str.substring(i, i + 1);
	    int alpIndex = ALPHABET.indexOf(chr);
	    if (alpIndex == -1) ret += chr;
	    else {
		alpIndex = (alpIndex + (_key[i % 100])) % 26;
		chr = ALPHABET.substring(alpIndex, alpIndex + 1);
		ret += chr;
	    }
	}
	return ret;
    }

    //decrypts str using _key
    public String decrypt(String str) {
	String ret = "";
	for (int i = 0; i < str.length(); i++) {
	    String chr = str.substring(i, i + 1);
	    int alpIndex = ALPHABET.indexOf(chr);
	    if (alpIndex == -1) ret += chr;
	    else {
		alpIndex = (26 + alpIndex - (_key[i % 100])) % 26;
		chr = ALPHABET.substring(alpIndex, alpIndex + 1);
		ret += chr;
	    }
	}
	return ret;
	
    }

    //returns true if key is valid: each part of key is int in range [0, 25]
    //static bc is not dependent on instance of JoanCipher
    public static boolean isValidKey(String key) {
        if (key.length() != 200) return false;
	for (int i = 0; i < 100; i++)
	    if (Integer.parseInt(key.substring(i * 2, i * 2 + 2)) < 0 ||
		Integer.parseInt(key.substring(i * 2, i * 2 + 2)) > 25)
		return false;
	return true;
    }

    //returns the key in string form
    public String getKey() {
	return _stringKey;
    }

    public static void main(String[] args) {

	JoanCipher jc = new JoanCipher();
	System.out.println( "New key:\n" + jc.getKey() );
	
	String choice;

	outside:
	while (true) {
	
	    while (true) {
		System.out.print("Encrypt or decrypt or get current key " +
				 "or exit (en/de/key/ex): ");
		choice = Keyboard.readString();
		if (choice.equals("en") ||
		    choice.equals("de") ||
		    choice.equals("key")) break;
		if (choice.equals("ex")) break outside;
	    }

	    System.out.println();

	    if (choice.equals("key"))
		System.out.println("\n" + jc.getKey() + "\n");
	
	    else if (choice.equals("en")) {

		System.out.println("Enter valid key and press enter, or " +
				   "enter \"1\" to use the key above");
	        String key = Keyboard.readString();
	        if (isValidKey(key))
		    jc = new JoanCipher(key);
		else
		    System.out.println("Using the key above");
		
		System.out.println("Type what you'd like to encrypt and" +
				   " press enter\n");
		String toEncrypt = Keyboard.readString();
		toEncrypt = toEncrypt.toLowerCase();
		System.out.println("\nEncrypted text:\n\n" +
				   jc.encrypt(toEncrypt));
	    }
	
	    else if (choice.equals("de")) {
		
		System.out.println("Enter valid key and press enter, or " +
				   "enter \"1\" to use the key above");
	        String key = Keyboard.readString();
	        if (isValidKey(key))
		    jc = new JoanCipher(key);
		else
		    System.out.println("Using the key above");

		System.out.println("Type what you'd like to decrypt and" +
				   " press enter\n");
		String toDecrypt = Keyboard.readString();
		toDecrypt = toDecrypt.toLowerCase();
		System.out.println("\nDecrypted text:\n\n" +
				   jc.decrypt(toDecrypt));
	    }
	    
	}//end all-encompasing while loop lol
	
    }//end main
	
	
}//end class
