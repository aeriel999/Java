package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String []args){

//        Scanner scaner = new Scanner(System.in);
//        String name;
//        System.out.printf("Input your name ");
//        name = scaner.nextLine();
//        System.out.println("Hello " + name);

        final int n = 10;
        Integer[] mas = new Integer[n];

        for(int i=0; i<n; i++)
        {
            mas[i] = getRandom(18,60);
        }

        for(var i : mas)
        {
            System.out.printf("%d\t", i);
        }

        System.out.println("Sort Array");

        Arrays.sort(mas);

        for(var i : mas)
        {
            System.out.printf("%d\t", i);
        }

        System.out.println("Sort Array revers");
        Arrays.sort(mas, Collections.reverseOrder());

        for(var i : mas)
        {
            System.out.printf("%d\t", i);
        }
    }

private static int getRandom(int min, int max){
    Random random = new Random();
    return random.nextInt(max-min)+min;
}
}