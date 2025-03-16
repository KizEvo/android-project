package com.example.android_project;

public class QuadraticEquation {
    //Input
    private double a, b, c;
    //Intermediate
    private double delta;

    //Constructor
    public QuadraticEquation(double A, double B, double C) {
        this.a = A;
        this.b = B;
        this.c = C;
        this.delta = B*B - 4*A*C;
    }

    //Solving function return: String [case, x0, x1]
    public String[] Solve() {
        String res[] = {"", "", ""};
        if(delta < 0) {
            res[0] = "LESS";
            res[1] = String.valueOf(-b / (2*a)) + " + " + String.valueOf(Math.sqrt(-delta) / (2*a)) + "i";
            res[2] = String.valueOf(-b / (2*a)) + " - " + String.valueOf(Math.sqrt(-delta) / (2*a)) + "i";
        }
        if(delta == 0) {
            res[0] = "EQUAL";
            res[1] = String.valueOf( -b / (2*a) );
            res[2] = String.valueOf( -b / (2*a) );
        }
        if(delta > 0) {
            res[0] = "LARGER";
            res[1] = String.valueOf( (-b + Math.sqrt(delta)) / (2*a) );
            res[2] = String.valueOf( (-b - Math.sqrt(delta)) / (2*a) );
        }
        return res;
    }
}
