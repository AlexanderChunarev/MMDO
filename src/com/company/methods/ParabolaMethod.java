package com.company.methods;

public class ParabolaMethod extends Method {

    public ParabolaMethod(String name) {
        setName(name);
    }

    @Override
    public void performCalculations() {
        long startTime = System.nanoTime();
        double h = 0.001;
        double a = START;
        int iterations = 0;

        while ((function(a + h) - 2 * function(a) + function(a - h)) / (h * h) <= 0) {
            a += 0.1;
        }
        double x1;
        x1 = a - 0.5 * h * (function(a + h) - function(a - h)) / (function(a + h) - 2 * function(a) + function(a - h));
        while (Math.abs(x1 - a) > EPSILON) {
            a = x1;
            x1 = a - 0.5 * h * (function(a + h) - function(a - h)) / (function(a + h) - 2 * function(a) + function(a - h));
            iterations++;
        }
        long endTime = System.nanoTime();
        setTime(endTime - startTime);
        setIterations(iterations);
        setMinFunValue(x1);
    }
}
