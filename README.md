# JoansStuff
Here is generally where any code I write that is NOT homework will go.
WARNING: a lot of code here may not have comments whoops

# Classes

## CSVEditor
A Java program for editing csv files. Warns user if file seems
too large to print. Simply compile and run to use. Utilizes jutils.CSVRW

## ScrabbleCheats/ScrabbleSolver.py
A Python program that uses the official Scrabble dictionary (as of 2016) to
suggest words. Returns all possible word combinations, sorted by most points
to fewest
To use the program, type "python path/to/ScrabbleSolver.py dict.txt
<(optional) letter to start with> <letters you have> <(optional) letter to end
with>
For example, if your rack contained "exkeped" and you wanted to start with
the letter "d", you would type "python path/to/ScrabbleSolver.py dict.txt
d exkeped"
Note that dict.txt could be updated with a new dictionary in the form
"word1\nword2\nword3\n..."

## FizzBuzz
A FizzBuzz engine that is easily expandable to check for more remainders.
See the code for instructions and explanations

## TestChoices
A Java program that  tests whether one should pick random answers or stick
with one answer while guessing on a test

## PrimeGen
A Python program that has several functions that generate primes either
indefinitely or to a maximum value. It also uses matplotlib to graph how long
it takes to generate these numbers

## MySortsPlus
A version of MySorts that I did in APCS class, but including a couple other
interesting sorts.

## JoanCipher
A encryption/decryption program that utilizes a caesar-cipher-like mechanism
to shift letters. However, the key shifts letters in such a way that
encrypting "llll" generally will not result in a String of 4 of the same
character (like "aaaa"; it will return something like "suhn")

## Blackjack
The simple, yet fun game of Blackjack! Player vs. Computer. Multiplayer
(hopefully) coming soon!

## TextAnimation
A proof-of-concept type file to test text-based animation.
Will not be edited or improved upon, though some of the 
techniques learned here may be used in future projects :)

## Calculator
WIP

# Utilities
##### these are stored in jutils/

## CSVRW
A class to facilitate reading, editing, and writing csv files.
This works with all csv files, but will only output one that
are rectangular (each row has the same number of elements).
Note that this is coded with programmers in mind, so the
first element of the first row is at (0, 0)

### Instance vars
- ArrayList<ArrayList<String>> _fileContents: Acts as a buffer
to store contents of csv file and changes made before writing

### Constructors
- CSVRW(): Stores a new 1x1 file in the buffer with 1 empty
string

- CSVRW(String fileName): Opens and reads fileName and stores
the data in the buffer
  - Throws: IllegalArgumentException if String doesn't end
            in .csv, file does not exist, or the program
            is unable to read the file

### Methods

- String toString(): Returns _fileContents in format
element,element,element...
element,element,element...
...

- int size(): Returns the number of rows

- ArrayList<ArrayList<String>> get(): Returns _fileContents

- String write(String fileName): deletes file fileName and 
writes new file with _fileContents saved in it
  - Returns: String fileName
  - Throws: IllegalArgumentException if unable to write to
            file

- int addRow(): adds a row of empty Strings to the end of 
_fileContents
  - Returns: index of insertion

- int addRow(int index): adds a row of empty Strings to index
of _fileContents
  - Returns: index
  - Throws: IllegalArgumentException if index is invalid

- int addColumn(): adds an element at the end of each row of
_fileContents
  - Returns: index of column insertion

- int addColumn(int index): adds an element to index of each
row of _fileContents
  - Returns: index
  - Throws: IllegalArgumentException if index is invalid

- ArrayList<String> removeRow(): removes last element from
each row of_fileContents
  - Returns: ArrayList<String> containing elements from the
             deleted row

- ArrayList<String> removeRow(int index): removes element at
index of each row of _fileContents
  - Returns: ArrayList<String> containing elements from the
             deleted row
  - Throws: IllegalArgumentException if index is invalid

- ArrayList<String> removeColumn(): removes element from the
end of each row of _fileContents
  - Returns: ArrayList<String> of removed elements

- ArrayList<String> removeColumn(int index): removes element at
index from each row of _fileContents
  - Returns: ArrayList<String> of removed elements
  - Throws: IllegalArgumentException if index is invalid

- String set(int r, int c, String newVal): sets element at
row r and column c of _fileContents to String newVal
  - Returns: the value being replaced
  - Throws: IllegalArgumentException if index is invalid

- String delete(int r, int c): sets element at row r and
column c to an empty String
  - Returns: the value being replaced

## FileRW
A Java program for reading and writing any file. Does not have
editing capabilities built in
### Methods
- String read(String): opens and reads file.
  - Returns: raw text from the file
  - Throws: IllegalArgumentException if file does not exist or
            is unreadable

- void write(String toWrite, String fileName): writes toWrite
to file fileName
  - Throws: IllegalArgumentException if unable to write to file

## Keyboard
A class to facilitate reading user input

~Joan Chirinos
