package com.janevski;

public class PerfectNumber {

    public static void main(String[] args) {
        int x=28;
        System.out.println("Is " + x+ " a perfect number? : "+ isPerfectNumber(x));
    }

    public static boolean isPerfectNumber(int number){
        if (number<1){
            return false;
        }

        else {
            int factor =0;
            int sum =0;

            for (int i=1; i<=number; i++){
                if (number %i==0){
                    System.out.println("This is the factor : " + i);
                    sum += i;
                    System.out.println("Sum: " + sum);


                    if (sum==number){
                        return true;
                    }

                }
            }

            return false;
        }
    }
}
