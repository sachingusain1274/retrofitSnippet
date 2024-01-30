package com.example.retrofittutorial.Activities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {

        Set<Employee> set = new TreeSet<>();
        set.add(new Employee("sachin" ,31));
        set.add(new Employee("sujata" ,26));
        set.add(new Employee("niku" ,2));


        System.out.println(set);


    }


}
