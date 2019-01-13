package com.janevski;

public class Palindrome  {

    public static void main(String[] args) {
        int x = -121;
        System.out.println("Is " + x + " a Palindrome? : " + isPalindrome(x));
    }

    public static boolean isPalindrome(int parameter) {
        int reverse = 0;
        int number = parameter;

        while (number != 0) {
            reverse *= 10;
            int lastDigit = number % 10;
            reverse += lastDigit;
            number /= 10;

        }


        if (reverse == parameter) {
            return true;
        }
        return false;

    }
}
