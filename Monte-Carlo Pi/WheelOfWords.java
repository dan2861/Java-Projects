//*******************************************************************
//
//   File: WheelOfWords.java          Assignment No.: 5
//
//   Author: dym7
//
//   Class: WheelOfWords
// 
//   Time spent on this problem: 3hrs
//   --------------------
//      This program implements all the functionalities of the game
//      played in the PlayWheelOfWord java class. It has the ability
//      to intitialize and display the current status of the game, get
//      a user input, and then determing whether the user is buying a
//      letter or making a guess. When the user runs out of points, 
//      or makes a correct final guess, the program outputs a score. 
//
//*******************************************************************
import java.util.Scanner;

public class WheelOfWords {
   // the secret word
   static String secretWord;

   // the word with guessed letters revealed
   static char[] displayWord;

   // remaining number of chances
   static int remainingChances;

   // remaining number of unrevealed letters, i.e., the number of '-'s
   static int remainingLetters;

   // keep track of when a game is completed
   static boolean finished;

   // Scanner
   static Scanner scan = new Scanner(System.in);

   // initialize a game
   public static void initialize(int totalChances) {
      // Pick a secret word
      secretWord = RandomWords.pickAWord().toUpperCase();

      // Game starts with maximum number of chances
      remainingChances = totalChances;

      // All the letters are not revealed at the beginning
      remainingLetters = secretWord.length();

      // Game runs for at least one turn
      finished = false;

      // Initialize the array to display the word
      displayWord = new char[secretWord.length()];

      // Initializes the char array with dashes
      initCharArray(displayWord, '-'); 
   }

   public static void play() {
      System.out.println("Welcome to Wheel of Words!");

      // Keep displaying status and getting 
      // user input while there are more chances
      // and geme is not finished
      do {
         displayCurrentStatus();
         getUserInput();
      } while (remainingChances > 0 && !finished);

      if (remainingChances == 0)
          getFinalGuess();
   }

   // initialize an array arr with ch
   public static void initCharArray(char[] arr, char ch) {
      for (int i = 0; i < arr.length; i++)
         arr[i] = ch;
   }

   // show the current progress of the displayWord
   public static void displayCurrentStatus() {
      System.out.printf("The word now looks like this: %s%n", new String(displayWord));
      System.out.printf("You can buy %d more letters.%n", remainingChances);
   }

   // take user's input and either buyLetter or makeGuess
   public static void getUserInput() {
      System.out.print("Buy a letter or make a final guess: ");

      // Creates a scanner object to take in user input
      String userInput = scan.next().toUpperCase();

      // User makes a final guess
      if (userInput.length() > 1) {
         makeGuess(userInput);
      } else { // User buys a letter
         buyLetter(userInput.charAt(0));
      }
   }

   // Update current status when user inputs a letter
   private static void buyLetter(char userChar) {
      
      // Update the displayed word based on letter bought
      for(int i = 0; i < secretWord.length(); i++) {
         if(secretWord.charAt(i) == userChar) {
            displayWord[i] = userChar;
         }
      }
      // Number of chances decreases after a guess
      remainingChances--;
   }

   // Checks if it is the secret string, and  
   // ends game when the user made a guess  
   public static void makeGuess(String userGuessStr) {
      // Determines game outcome
      boolean success = true; 

      // Checks guessed word is of equal lenght, and
      // loops through the words for comparison
      if(userGuessStr.length() == secretWord.length()) {
         for(int i = 0; i < userGuessStr.length(); i++) {
            // Loses if a single letter is different
            if(userGuessStr.charAt(i) != secretWord.charAt(i)) {
               // Game lost
               success = false;
            }
         }
      } else { 
         // Game lost because words are not of equal length 
         success = false;
      }
      
      // Displays final result depending on guess
      displayFinalMessage(success);

      // Game is over after a guess
      finished = true;
   }

   // get a guess when no more letters can be bought
   private static void getFinalGuess() {
      System.out.printf("The word now looks like this: %s%n", new String(displayWord));
      System.out.printf("You must make a final guess: ");

      // Creates a scanner object to take in user input
      // All inputs changed to upppercase for consistency
      String userInput = scan.next().toUpperCase();

      // Make a guess with the user's input
      makeGuess(userInput);
   }

   // Display the final result
   public static void displayFinalMessage(boolean success) {
      if (success) { // win
         System.out.println("You guessed the word: " + secretWord);
         System.out.printf("You win with a score of %d!\n", remainingChances);
      } else { // lost
         System.out.println("You lost!");
         System.out.printf("The word was: %s\n", secretWord);
      }
   }
}
