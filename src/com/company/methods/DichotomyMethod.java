package com.company.methods;

import static java.lang.Math.abs;

public class DichotomyMethod extends Method {

    public DichotomyMethod(String name) {
        setName(name);
    }

    @Override
    public void performCalculations() {
        long startTime = System.nanoTime();
        double a = START, b = END, delta = EPSILON / 3;
        double x1, x2, f1, f2;
        int iterations = 0;
        do {
            x1 = (a + b - delta) / 2;
            x2 = (a + b + delta) / 2;
            f1 = function(x1);
            f2 = function(x2);
            if (f1 <= f2) {
                b = x2;
            } else {
                a = x1;
            }
            iterations++;
        }
        while (abs(a-b) > EPSILON);
        long endTime = System.nanoTime();
        setTime(endTime - startTime);
        setIterations(iterations);
        setMinFunValue((a + b) / 2);
    }
}
