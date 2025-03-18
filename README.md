# Android Application Project
_A combination of Mobile Application Development projects_
- Subject: Mobile Application Development
- Lecturer: MSc. NGUYỄN QUANG ANH
- Description: This repository includes projects implemented on Android Studio for the "Mobile Application Development" course exercise at HCMUS

# Group 4 Member List

| Student ID | Name           | Username                                     |
|------------|----------------|----------------------------------------------|
| 21200310   | LÊ NGỌC LONG   | [@LongLe7184](https://github.com/LongLe7184) |
| 21200328   | NGUYỄN ĐỨC PHÚ | [@KizEvo](https://github.com/KizEvo)         |

## Week 1 - Activities & Intents
_Requirement: create an Quadratic Equation Solver app using Intent and Bundle_

![image](https://github.com/user-attachments/assets/dfffcdba-6c38-40f7-af77-077d12c1fd16)

_Pseudo Code_
![image](https://github.com/user-attachments/assets/8ac868b3-6ead-47e7-ab35-beb19084e554)

_QuadraticEquation.java Class_

```
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
```
