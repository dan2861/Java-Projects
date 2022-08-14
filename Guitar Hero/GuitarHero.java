
//************************************************************************
// File: GuitarHeroLite.java         Assignment 7
// 
// Author: dym7
//
// Class: GuitarHero
// Dependencies: RingBuffer GuitarString StdAudio DrawingPanel 
//
// Description  :  GuitarHero template
//  
//  Plays guitar string when the user types any keys from keyboard, which 
//  contains the elements "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' " in the 
//  standard drawing window.
//
//************************************************************************
import java.awt.*;
import java.lang.Character;

public class GuitarHero {
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

		// concert A is the first 
		final double CONCERT_A = 440.0;
		String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        // create a guitar string for all the keys
		GuitarString[] notes = new GuitarString[keyboard.length()];

		for(int i = 0; i < notes.length; i++) {
			double concert = CONCERT_A * Math.pow(2, (i - 24) / 12.0);
			notes[i] = new GuitarString(concert);
		}
		// Set up keyboard listener (similar to mouse listener from last pset)
		panel.onKeyDown((key) -> {
			if (keyboard.indexOf(Character.toLowerCase(key)) != -1) {
				notes[keyboard.indexOf(Character.toLowerCase(key))].pluck();
			}
		});

		// fence post
		double xprev = 0, yprev = 0;

		while (true) {
			// compute the superposition of the samples for duration
			// by summing every notes of the guitar string
			double sample = 0;
			for(int i = 0; i < notes.length; i++) {
				sample += notes[i].sample();
			}

			// send the result to standard audio
			StdAudio.play(sample);

			// advance the simulation of each guitar string by one step
			for(int i = 0; i < notes.length; i++) {
				notes[i].tic();
			}


			// Decide if we need to draw.
			// Audio sample rate is StdAudio.SAMPLE_RATE per second
			// Draw sample rate is DRAW_SAMPLE_RATE
			// Hence, we draw every StdAudio.SAMPLE_RATE / DRAW_SAMPLE_RATE
			if (notes[0].time() % AUDIO_PER_DRAW == 0) {
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
} // end of class
