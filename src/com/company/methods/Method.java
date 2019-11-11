package com.company.methods;

import static java.lang.Math.pow;

abstract public class Method {
    static final double EPSILON = 0.00000001;
    static final double START = 1;
    static final double END = 2;
    private int iterations = 0;
    private int functionCallCount = 0;
    private double minFunValue = 0;
    private long time = 0;
    private String name;

    public String getName() {
        return name;
    }

    public void setTime(long time) {
        this.time = time;
    }

    void setName(String name) {
        this.name = name;
    }

    void setIterations(int iterations) {
        this.iterations = iterations;
    }

    void setMinFunValue(double minFunValue) {
        this.minFunValue = minFunValue;
    }

    double function(double x) {
        functionCallCount++;
        return pow(x, 4) + 4 * pow(x, 3) - 3 * pow(x, 2) - 36 * x + 45;
    }

    public void show() {
        System.out.println("Iterations: " + iterations);
        System.out.println("Call function count: " + functionCallCount);
        System.out.println("x = " + minFunValue);
        System.out.println("function(x) = " + function(minFunValue));
        System.out.println("time = " + time + "\n");
    }

    public abstract void performCalculations();
}
