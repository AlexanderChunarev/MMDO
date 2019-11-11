package com.company.methods;

public class FibonacciMethod extends Method {
    public FibonacciMethod(String name) {
        setName(name);
    }

    @Override
    public void performCalculations() {
        long startTime = System.nanoTime();
        int iterations = 0;
        double a, b, n = 10, u, v, fu, fv;
        a = START;
        b = END;

        u = a + fibonacci(n) / fibonacci(n + 2) * (b - a);
        v = a + b - u;

        fu = function(u);
        fv = function(v);

        for (int i = 0; i < n; i++) {
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
                u = a + fibonacci(n - i + 1) / fibonacci(n - i + 3) * (b - a);
                v = a + b - u;
                fu = function(u);
                fv = function(v);
            }
            iterations++;
        }
        long endTime = System.nanoTime();
        setTime(endTime - startTime);
        setIterations(iterations);
        setMinFunValue((a + b) / 2);
    }

    private double fibonacci(double n) {

        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}
