//Fill in your name and student number
//Name: Angelica Janevski
//Student Number: 260801180

// do NOT touch the following import statement

import java.util.Random;
import java.util.Scanner;

public class GuessTheWord {

    private static final String[] words = {"perfect", "country", "pumpkin", "special", "freedom", "picture", "husband",
            "monster", "seventy", "nothing", "sixteen", "morning", "journey", "history", "amazing", "dolphin", "teacher",
            "forever", "kitchen", "holiday", "welcome", "diamond", "courage", "silence", "someone", "science", "revenge",
            "harmony", "problem", "awesome", "penguin", "youtube", "blanket", "musical", "thirteen", "princess", "assonant",
            "thousand", "language", "chipotle", "business", "favorite", "elephant", "children", "birthday", "mountain",
            "football", "kindness", "abdicate", "treasure", "strength", "together", "memories", "darkness", "sandwich",
            "calendar", "marriage", "building", "function", "squirrel", "tomorrow", "champion", "sentence", "daughter",
            "hospital", "identical", "chocolate", "beautiful", "happiness", "challenge", "celebrate", "adventure",
            "important", "consonant", "dangerous", "irregular", "something", "knowledge", "pollution", "wrestling",
            "pineapple", "adjective", "secretary", "ambulance", "alligator", "congruent", "community", "different",
            "vegetable", "influence", "structure", "invisible", "wonderful", "nutrition", "crocodile", "education",
            "beginning", "everything", "basketball", "weathering", "characters", "literature", "perfection", "volleyball",
            "homecoming", "technology", "maleficent", "watermelon", "appreciate", "relaxation", "abominable", "government",
            "strawberry", "retirement", "television", "silhouette", "friendship", "loneliness", "punishment", "university",
            "confidence", "restaurant", "abstinence", "blackboard", "discipline", "helicopter", "generation", "skateboard",
            "understand", "leadership", "revolution"};


    public static String getRandomWord(int seed) {
        Random gen = new Random(seed);
        int randomIndex = gen.nextInt(words.length);
        return words[randomIndex];
    }


    public static void main(String[] args) {
        play(50);


    }


    public static boolean isValidGuess(char c) {
        for (char i = 'a'; i <= 'z'; ++i) {
            if (c == i) {
                return true;
            }
        }
        //the char did not match any lower case letter of the english alphabet
        return false;
    }

    public static int[] generateArrayOfGuesses(String s) {
        //create an array the length of the secret word
        int[] initialA = new int[s.length()];
        return initialA;
    }

    public static boolean checkAndUpdate(String secret, int[] currentGuesses, char lastGuess) {
        int guessesRight = 0;

        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == lastGuess) {
                //if the char is in the word, update the array with a 1 and increase the guesses right count
                currentGuesses[i] = 1;
                guessesRight++;
            }
        }


        if (guessesRight != 0) {
            return true;
        } else {
            //the guess right count is still zero, therefore char is not in secret word
            return false;
        }


    }

    public static String getWordToDisplay(String secret, int[] currentGuesses, char lastGuess) {
        String word = "";
        String upperSecret = secret.toUpperCase();


        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == lastGuess) {
                //if the char is guessed, add uppercase char to string
                word = word + upperSecret.charAt(i);

            }
            else if(currentGuesses[i] == 1){
                //else if already guessed, add a lower case char to string
                word=word+secret.charAt(i);

            }
            else {
                //else add hyphen
                word = word + "-";
            }
        }


        return word;

    }

    public static boolean isWordGuessed(int[] g) {
        int i;
        for (i = 0; i < g.length; i++) {
            if (g[i] != 1) {
                //if you find a 0 in the array, break out of loop
                break;
            }
        }
        if (i == g.length) {
            //if no zeroes were found, i==g.length and the word is guessed
            return true;
        } else {
            return false;
        }

    }

    public static void play(int n) {
        //create scanner
        Scanner sc = new Scanner(System.in);
        //the secret word is var w
        String w = getRandomWord(n);

        //initial message
        System.out.println("Welcome to \"Guess the Word!\" ");
        System.out.println("Your secret word has been generated. It has " + w.length() + " characters. You have 10 lives. Good luck!");

       //creating 10 lives, array of guesses, and string and char variable to use for input later
        int lives = 10;
        int[] guessArray = generateArrayOfGuesses(w);
        String check;
        char guess;


        while (lives > 0) {
           //getting input string
            System.out.println();
            System.out.println("You have " + lives + " lives left. Please enter a character:");
            check = sc.nextLine();

           //if-else checks length of string
            if (check.length() != 1) {
                System.out.println("You can only enter one single character. Try again!");
            } else {
                //length is good, so extract char from input string
                guess = check.charAt(0);

                if (!isValidGuess(guess)) {
                    //the guess is not valid, show error
                    System.out.println("The character must be a lower case letter of the English alphabet. Try again.");
                } else if (checkAndUpdate(w, guessArray, guess)) {
                    //the char is a valid guess and it's in the word
                    System.out.println("Good job! The secret word contains the character " + guess);
                    System.out.println(getWordToDisplay(w, guessArray, guess));
                } else {
                    //the char is valid but it's not in the word
                    lives--;
                    System.out.println("There's no such character. Try again!");
                    System.out.println(getWordToDisplay(w, guessArray, guess));
                }


                if (isWordGuessed(guessArray)) {
                    //the whole word was guessed, the program should terminate
                    System.out.println();
                    System.out.println("Congratulations you guessed the secret word!");
                    return;
                }
            }

        }

        //loop is done so lives=0, game over
        sc.close();
        System.out.println();
        System.out.println("You have no lives left, better luck next time! The secret word was: \"" + w + "\"");
    }

}