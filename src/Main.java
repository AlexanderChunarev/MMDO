import com.company.methods.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Method> methods = new ArrayList<>();
        methods.add(new DichotomyMethod("Dichotomy method"));
        methods.add(new ParabolaMethod("Parabola method"));
        methods.add(new GoldSliceMethod("Gold slice method"));
        methods.add(new FibonacciMethod("Fibonacci method"));

        methods.forEach(method -> {
            System.out.println(method.getName());
            method.performCalculations();
            method.show();
        });
    }
}
