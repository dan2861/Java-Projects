//*******************************************************************
//
//   File: PlayWheelOfWords.java          Assignment No.: 5
//
//   Author: dym7
//
//   Class: PlayWheelOfWords
// 
//   Time spent on this problem: < 0.5
//   --------------------
//      This program implements the functions and variables of two
//      other classes to start the wheel of words game. It serves
//      as an interface to build and play the game.
//
//*******************************************************************
import java.util.Scanner;

public class PlayWheelOfWords {
   static final int MAXCHANCES = 8;

   public static void main(String[] args) {
      String response;
      Scanner scan = new Scanner(System.in);

      do {
         // create the game for one round

         WheelOfWords.initialize(MAXCHANCES);

         WheelOfWords.play();

         System.out.println();
         
         System.out.print("Play another round of Hangman (y/n)? ");
         response = scan.nextLine().toUpperCase();  // allows both y and Y 
         
      } while (response.equals("Y")); // Checks it's y or Y 

      scan.close();
   }
}
