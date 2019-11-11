package com.company.methods;

public class GoldSliceMethod extends Method {

    public GoldSliceMethod(String name) {
        setName(name);
    }

    @Override
    public void performCalculations() {
        long startTime = System.nanoTime();
        int iterations = 0;
        double a, b, u, v, fu, fv;
        a = START;
        b = END;

        u = a + (3 - Math.sqrt(5)) / 2 * (b - a);
        v = a + b - u;

        fu = function(u);
        fv = function(v);

        do {
            iterations++;
            if (fu <= fv) {
                b = v;
                v = u;
                fv = fu;
                u = a + b - v;
                fu = function(u);
            } else {
                a = u;
                u = v;
                fu = fv;
                v = a + b - u;
                fv = function(v);
            }
            if (u > v) {
                u = a + (3 - Math.sqrt(5)) / 2 * (b - a);
                v = a + b - u;
                fu = function(u);
                fv = function(v);
            }
        } while (Math.abs(b - a) > EPSILON);
        long endTime = System.nanoTime();
        setTime(endTime - startTime);
        setIterations(iterations);
        setMinFunValue((a + b) / 2);
    }
}
