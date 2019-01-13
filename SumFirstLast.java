package com.janevski;

public class SumFirstLast {

    public static void main(String[] args) {
        System.out.println("The sum is : " + sumFirstAndLastDigit(6656564));
    }

    public static int sumFirstAndLastDigit(int number){
        if (number <0){
            return -1;
        }

        else{
            int lastDigit= number %10;
            int firstDigit = 0;

            while (number != 0){
                firstDigit = number %10;
                number /=10;


            }



            return lastDigit + firstDigit ;

        }

    }
}

