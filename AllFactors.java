package com.janevski;

public class AllFactors {

    public static void main(String[] args){
        System.out.println(printFactors(48));
    }

    public static String printFactors(int number) {
        String factor = "";

        if (number < 1) {
            return "Invalid Value";
        } else {

            for (int i = 1; i <= number; i++) {

                if (number % i == 0) {

                    factor += i + " ";

                }
            }


            return (number + " has factors: " + factor);
        }
    }
}
