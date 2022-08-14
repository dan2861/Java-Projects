//==========================================================
// 
// File: MonteCarloPi.java     
// 
// Author: dym7
// 
// Class: MonteCarloPi
// 
// Time spent:  3.5hrs
// 
// -------------------- 
// This program calculates an estimation of pi, by generating 
// random numbers, and determining which ones fall inside a 
// circle of radius 0.5, which is contained inside a 1 x 1 square.
// The program asks the user for the number of points to drop, and
// then estimates pi by: 
//
//==========================================================

import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics2D;

public class MonteCarloPi {

    // Enforce a minimal of # of points to drop
    static final int MIN_N = 5;

    // A common practice is to define messages as class constants
    static final String PROMPT = "Please input the number of points to drop: ";
    static final String INPUT_ERROR = "The minimum allowed value is " + MIN_N + ".";

    // Specific configuration
    static final double RADIUS = 0.5;

    static final double CIRCLE_CENTER_X = 0.5;
    static final double CIRCLE_CENTER_Y = 0.5;

	// width and height of the screen
	static final int WIDTH = 800;
	static final int HEIGHT = 300;

    // The starting point to plot the line graph
    static int firstPointX = 0;
    static int firstPointY = 0;

	// set up panel. no need for double buffering in this case
	public static DrawingPanel panel = new DrawingPanel(WIDTH, HEIGHT);
	public static Graphics2D g = panel.getGraphics();

    public static void main(String[] args) {

        // Get the number of points to drop
        int N = getN();
        int pointsInCircle = 0;
            
        // Loop till all the points are dropped
        for(int pointsDropped = 1; pointsDropped <= N; pointsDropped++) {
            // No points initially dropped in the circle 
            // Get random coordinates (x,y)
            double xDropped = Math.random();
            double yDropped = Math.random();

            // Update number of points in the circle
            if(isInCircle(xDropped, yDropped)) {
                pointsInCircle += 1;
            }
            
            // Outputs estimation every 5 iterations before 50 points are dropped,
            // 50 iterations before 1000 points, and 1000 iterations for the rest
            if((pointsDropped < 50 && pointsDropped % 5 == 0) || (pointsDropped < 1000 && pointsDropped % 50 == 0) || 
                (pointsDropped >= 1000 && pointsDropped % 1000 == 0)) {
                    double estimate = estimatePi(pointsInCircle, pointsDropped);
                    System.out.printf("%d \t %.2f%n", pointsDropped, estimate);

                    // Plot the graph
                    drawGraph(pointsDropped, pointsInCircle, N);
                }
        } // end of for loop

    } // end of method main

    // Get N from user
    public static int getN() {
        // Create scanner for user input
        Scanner input = new Scanner(System.in);
        System.out.print(PROMPT);
        int numOfPoints =  input.nextInt(); 

        // Close scanner
        input.close();

        // Check minimum input
        if(numOfPoints < MIN_N) {
            System.out.println(INPUT_ERROR);
            System.exit(1); 
        }      
        return numOfPoints;
    }

    // Determine if new point (x, y) is in circle
    public static boolean isInCircle(double x,
            double y) {

        // Distance between the center of the circle and x squared
        double distanceXSquared = (CIRCLE_CENTER_X - x) * (CIRCLE_CENTER_X - x);

        // Distance between the center of the circle and y squared
        double distanceYSquared = (CIRCLE_CENTER_Y - y) * (CIRCLE_CENTER_Y - y);

        // The distance between the point and the center of the circle 
        double distance = Math.sqrt(distanceXSquared + distanceYSquared);
        // Check points inside the circle
        if(distance < RADIUS) {
            return true;
        }
        return false;
    }

    // Compute estimate of Pi given pointsInCircle and pointsDropped
    public static double estimatePi(int pointsInCircle,
            int pointsDropped) {

        // Ration of pointsInCircle / pointsDropped = the area of the circle / the area of the square
        // pointsInCircle/ pointsDropped * 4 ~ pi
        double pi = (pointsInCircle * 4.0) / pointsDropped;
        return pi;
    }

    // Plot the graph from the estimates
    public static void drawGraph(int pointsDropped, int pointsInCircle, int numberOfPoints) {
        // Scale the x and y coordinates to the canvas
        int scaledX = scale(pointsDropped, 0, numberOfPoints, WIDTH);
        // reverse the y-axis by subtracting from height
        int scaledY = HEIGHT - scale(estimatePi(pointsInCircle, pointsDropped), 0, 5, HEIGHT); 
        
        // Draw the line
        g.setColor(Color.BLACK);
        g.drawLine(firstPointX, firstPointY, scaledX, scaledY);
        firstPointX = scaledX;
        firstPointY = scaledY;
    }

    // converts a value from one scale to another
    public static int scale(double toScale, double rangeMin, double range, double newRange) {
        return (int) ((toScale - rangeMin) / range * newRange);
    }

} // end of class
