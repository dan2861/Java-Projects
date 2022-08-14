//************************************************************************
// File: RingBuffer.java         Assignment 8
// 
// Author: dym7       
//
// Class: RingBuffer 
//
// Description  :  RingBuffer template
//  
//  This is a template file for RingBuffer.java. It lists the constructors 
//  and methods you need, along with descriptions of what they're supposed 
//  to do.
//
//************************************************************************

public class RingBuffer {
    private double[] rb;          // items in the buffer
    private int first;            // index for the next dequeue or peek
    private int last;             // index for the next enqueue
    private int size;             // number of items in the buffer

    // create an empty buffer, with given max capacity
    public RingBuffer(int capacity) {
        // an array of doubles
        rb = new double[capacity];
        // initialize the instance variables
        first = last = size = 0;
    }

    // return number of items currently in the buffer
    public int size() {
        return size;
    }

    // returns true if the buffer is empty (size equals zero)
    public boolean isEmpty() {
        return this.size() == 0;
    }

    // reutrns true if the buffer is full (size equals array capacity)?
    public boolean isFull() {
        return this.size() == rb.length;
    }

    // add item x to the end
    public void enqueue(double x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }

        // add element to last index
        rb[last] = x;   
        // increment last
        last++;  
        // increment size   
        size++;     

        // wrap around when index equals capacity
        if(last == rb.length) {
            last = 0;
        }
    }

    // delete and return item from the front
    public double dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        // removed element
        double dequeue = rb[first]; 
        // new first element
        first++;    
        // size decreases
        size--;       

        // wrap around when index equals capacity
        if(first == rb.length) {
            first = 0;
        }
        
        return dequeue;
    }

    // return (but do not delete) item from the front
    public double peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        // returns first item
        return rb[first];
    }

    // a simple test of the constructor and methods in RingBuffer
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(N);
        for (int i = 1; i <= N; i++) {
            buffer.enqueue(i);
        }
        double t = buffer.dequeue();
        buffer.enqueue(t);
        System.out.println("Size after wrap-around is " + buffer.size());
        while (buffer.size() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        System.out.println(buffer.peek());
    }
}
