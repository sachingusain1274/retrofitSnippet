package com.example.retrofittutorial.Activities;

public class Employee implements Comparable<Employee> {

    String name;
    int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Employee(String name , int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Employee employee) {
        if(this.age > employee.age){
            return 1;
        }
        else if (this.age < employee.age){
            return -1;
        }

        return 0;
    }
}
