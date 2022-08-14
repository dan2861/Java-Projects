//************************************************************************
// File: GuitarString.java         Assignment 8
// 
// Author: dym7             
//
// Class: GuitarString
// Dependencies: RingBuffer 
//
// Description  :  GuitarString template (apprx. 1.5hr)
//  
//  This is a template file for GuitarString.java. It lists the 
//  constructors and methods you need, along with descriptions of what 
//  they're supposed to do. A guitar string utilizes the RingBuffer 
//  class circular array feature and all the methods that it contains.
//  
//************************************************************************

public class GuitarString {

    private RingBuffer buffer; // ring buffer
    private static final double DECAY = 0.996;
    private int capacity; // the max size of the buffer
    private int countTic; // number of times tic() is called

    // create a guitar string of the given frequency
    public GuitarString(double frequency) {
        // capacity is the ration of sampling rate to frequency
        capacity = round(StdAudio.SAMPLE_RATE/frequency);
        // create a ring buffer
        buffer = new RingBuffer(capacity);

        // enqueue N zeros, where N is capacity
        for(int i = 0; i < capacity; i++) {
            buffer.enqueue(0);
        }
    }

    // create a guitar string with size & initial values given by the array
    public GuitarString(double[] init) {
        // create a ring buffer
        buffer = new RingBuffer(init.length);
        
       // initializes the contents of the buffer with the values in the array
        for(int i = 0; i < init.length; i++) {
            buffer.enqueue(init[i]);
        }
    }

    // pluck the guitar string by replacing the buffer with white noise
    public void pluck() {
        // Replace the N items in the ring buffer 
        // with N random values between -0.5 and +0.5. 
        for(int i = 0; i < buffer.size(); i++) {
            buffer.dequeue();
            buffer.enqueue(Math.random() - 0.5);
        }
    }

    // advance the simulation one time step
    public void tic() {
        // apply the Karplus-Strong update: 
        // first, delete the sample at the front of the ring buffer 
        double firstSample = buffer.dequeue();
        double newFirstSample = sample();

        // add the average of the two samples,
        // and multiply by the decay factor
        double average = (firstSample + newFirstSample) / 2.0;
        buffer.enqueue(average * DECAY);
        countTic++;

    }

    // return the current sample
    public double sample() {
        return buffer.peek();
    }

    // return number of times tic was called
    public int time() {
        return countTic;
    }

    // round a double to the nearest int
    public static int round(double d) {
        return (int) Math.round(d);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        double[] samples = { .2, .4, .5, .3, -.2, .4, .3, .0, -.1, -.3 };  
        GuitarString testString = new GuitarString(samples);
        for (int i = 0; i < N; i++) {
            int t = testString.time();
            double sample = testString.sample();
            System.out.printf("%6d %8.4f\n", t, sample);
            testString.tic();
        }
    }
}
