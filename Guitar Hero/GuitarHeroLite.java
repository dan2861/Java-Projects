
//************************************************************************
// File: GuitarHeroLite.java         Assignment 7
// 
// Author: <netid>
//
// Class: GuitarHeroLite
// Dependencies: RingBuffer GuitarString StdAudio DrawingPanel 
//
// Description  :  GuitarHero template
//  
//  Plays two guitar strings (concert A and concert C) when the user
//  types the lowercase letters 'a' and 'c', respectively in the 
//  standard drawing window.
//************************************************************************
import java.awt.*;

public class GuitarHeroLite {
	// set up panel and graphics objects
	private static final int WIDTH = 768;
	private static final int HEIGHT = 256;
	private static DrawingPanel panel = new DrawingPanel(WIDTH, HEIGHT);
	private static Graphics2D g = panel.getGraphics();

	private static final int DRAW_SAMPLE_RATE = 20; // draw at a rate of 20/sec
	private static final int AUDIO_PER_DRAW = StdAudio.SAMPLE_RATE / DRAW_SAMPLE_RATE;

	private static final int PLAY_TIME = 10; // target 60 seconds display window
	private static final int X_RANGE = DRAW_SAMPLE_RATE * PLAY_TIME;

	// general scaling method
	public static double scale(double oldValue, double oldMin, double oldMax, double newMin, double newMax) {
		return (oldValue - oldMin) / (oldMax - oldMin) * (newMax - newMin) + newMin;
	}

	// helpers for scaling to window
	public static int scaleToHeight(double oldValue) {
		return (int) (scale(oldValue, -1, 1, 0, HEIGHT));
	}

	public static int scaleToWidth(double oldValue) {
		return (int) (scale(oldValue, 0, X_RANGE, 0, WIDTH));
	}

	public static void main(String[] args) {

		// Create two guitar strings, for concert A and C
		final double CONCERT_A = 440.0;
		final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
		GuitarString stringA = new GuitarString(CONCERT_A);
		GuitarString stringC = new GuitarString(CONCERT_C);

		// Set up keyboard listener (similar to mouse listener from last pset)
		panel.onKeyDown((key) -> {
			if (key == 'a') {
				stringA.pluck();
			} else if (key == 'c') {
				stringC.pluck();
			}
		});

		// fence post
		double xprev = 0, yprev = 0;

		while (true) {
			// compute the superposition of the samples for duration
			double sample = stringA.sample() + stringC.sample();

			// send the result to standard audio
			StdAudio.play(sample);

			// advance the simulation of each guitar string by one step
			stringA.tic();
			stringC.tic();

			// Decide if we need to draw.
			// Audio sample rate is StdAudio.SAMPLE_RATE per second
			// Draw sample rate is DRAW_SAMPLE_RATE
			// Hence, we draw every StdAudio.SAMPLE_RATE / DRAW_SAMPLE_RATE
			if (stringA.time() % AUDIO_PER_DRAW == 0) {
				g.setColor(Color.RED);
				g.drawLine(scaleToWidth(xprev),
						scaleToHeight(yprev),
						scaleToWidth(xprev + 1),
						scaleToHeight(sample));
				xprev++;
				yprev = sample;
				// check if wrapped around
				if (xprev > X_RANGE) {
					xprev = 0;
					// clear the image
					g.setColor(Color.WHITE);
					g.fillRect(0, 0, WIDTH, HEIGHT);
				}
			} // end of if

		} // end of while

	} // end of main

}
// end of class
