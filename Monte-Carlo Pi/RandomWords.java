//*******************************************************************
//
//   File: FileName.java          Assignment No.: 5
//
//   Author: dym7
//
//   Class: RandomWords
// 
//   Time spent on this problem: 1.5hr
//   --------------------
//      This program contains the function to open a file and pick a
//      random word from that file. It does this by opening the file
//      twice, first to count how many lines (one word per line) it 
//      has, then closes it to open it a second time to choose a random
//      line containing a word.
//
//*******************************************************************
import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class RandomWords {
   public static String pickAWord() {
      Scanner input = null;
      // Open a file with a scanner object
      try{
         // Open the file containing words in a scanner object
         input = new Scanner(new File("words.txt"));
      } catch (FileNotFoundException e) {
         // Print an error message
         System.err.println("File not found");
         // Exit with an error code
         System.exit(1);
      }
      
      // The total number of words 
      int wordCount = 0; 

      // Iterate throught the lines of file
      while(input.hasNextLine()) {
         wordCount++;
         input.nextLine();
      }

      // Generate a random number using the total number of words
      Random rand = new Random();
      int randomLine = rand.nextInt(wordCount);
      
      // Close the scanner object
      input.close();

      Scanner newInput = null;
      // Reopen the file with a new scanner object
      try{
         // Open the file containing words in a scanner object
         newInput = new Scanner(new File("words.txt"));
      } catch (FileNotFoundException e) {
         System.err.println("File not found");
         System.exit(1);
      }

      // The word on the random line
      String randomWord = "";

      // Read the file random number of times,
      // last word read is the random word
      for(int line = 0; line <= randomLine; line++) {
         randomWord = newInput.nextLine();
      }

      // Close input reading from file 
      newInput.close();

      return randomWord;
   }
} // end of class RandomWords
