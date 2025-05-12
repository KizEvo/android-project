package com.example.android_project;

class Student {
    private int id;
    private String name;
    private int age;
    private String major;
    private int resId;

    public Student(int id, String name, int age, String major, int resId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.major = major;
        this.resId = resId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMajor() {
        return major;
    }

    public int getResId() {
        return resId;
    }
}
