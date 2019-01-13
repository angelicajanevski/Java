import java.util.Random;
import java.util.Scanner;
//Angelica Janevski
//260801180

public class BattleGame {
    //creation of random object
    private static Random r = new Random();

    public static void playGame(String playerFile, String monsterFile, String spellFile) {
        try {
            //read the files
            Character player = FileIO.readCharacter(playerFile);
            Character monster = FileIO.readCharacter(monsterFile);
            Character.setSpells(FileIO.readSpells(spellFile));

            //call private display method for each character(see bottom of class for my custom private methods)
            display(player);
            display(monster);

            //if nothing in spell file, print message
            if (FileIO.readSpells(spellFile).isEmpty()) {
                System.out.println("The game will be played without spells.");
                System.out.println();
            } else {
                //display spells
                System.out.println("Here are the available spells:");
                Character.displaySpells();
                System.out.println();

            }


            //start scanner
            Scanner sc = new Scanner(System.in);
            //set the current health of characters to local variables
            double playerLife = player.getCurrHealth();
            double monsterLife = monster.getCurrHealth();
            //this variable will be used to exit the game
            boolean endGame = false;

            //while both characters are not knocked
            while (playerLife > 0 || monsterLife > 0) {
                System.out.println("Enter a command:");
                String command = sc.nextLine();
                System.out.println();

                //attack command
                if (command.equals("attack")) {
                    //all attacks are carried out by my 2 private custom methods, characterAttacks() and knockoutCharacter(). SEE CODE AT END OF CLASS
                    //player attacks w
                    monsterLife = characterAttacks(player, playerLife, monster, monsterLife);
                    endGame = knockoutChecker(player, playerLife, playerFile, monster, monsterLife, monsterFile);
                    if (endGame) {
                        break;
                    }

                    //monster attacks
                    playerLife = characterAttacks(monster, monsterLife, player, playerLife);
                    endGame = knockoutChecker(player, playerLife, playerFile, monster, monsterLife, monsterFile);
                    if (endGame) {
                        break;
                    }

                    //quit command
                } else if (command.equals("quit")) {

                    System.out.println("Goodbye!");
                    return;


                } else {// everything else is considered a spell

                    if (FileIO.readSpells(spellFile).isEmpty()|| command.length()==0) {
                        System.out.println("Input not recognized. Try \'attack\' or \'quit\'");
                        System.out.println();
                    } else {

                        double damage = player.castSpell(command.toLowerCase(), r.nextInt());
                        //if the spell cannot be found, .castsSpell() returns -1
                        if (damage == -1) {
                            System.out.println(player.getName() + " tried to to cast " + command + ", but they don't know that spell. ");
                            System.out.println();
                            playerLife = characterAttacks(monster, monsterLife, player, playerLife);
                            endGame = knockoutChecker(player, playerLife, playerFile, monster, monsterLife, monsterFile);
                            if (endGame) {
                                break;
                            }


                        } else if (damage <= 0) {
                            //the spell has 0 or less damage
                            System.out.println("Odin tried to cast a spell, but spell cast failed!");
                            System.out.println();
                            //monster attacks
                            playerLife = characterAttacks(monster, monsterLife, player, playerLife);
                            endGame = knockoutChecker(player, playerLife, playerFile, monster, monsterLife, monsterFile);
                            if (endGame) {
                                break;
                            }
                        } else {

                            //spell deals damage
                            String damageStr = String.format("%1$.2f", damage);
                            System.out.println(player.getName() + " casts " + command.toLowerCase() + " dealing " + damageStr + " damage!");
                            monsterLife = monster.takeDamage(damage);
                            endGame = knockoutChecker(player, playerLife, playerFile, monster, monsterLife, monsterFile);
                            if (endGame) {
                                break;
                            }
                            System.out.println(monster);
                            System.out.println();

                            //monster attacks
                            playerLife = characterAttacks(monster, monsterLife, player, playerLife);
                            endGame = knockoutChecker(player, playerLife, playerFile, monster, monsterLife, monsterFile);
                            if (endGame) {
                                break;
                            }
                        }

                    }
                }


            }

            sc.close();

        } catch (NullPointerException e) {
            System.out.println("Game cannot be played.");
        }

    }

    //these are private custom methods to help organize code
    // this method displays the character information
    private static void display(Character c) {
        System.out.println("Name: " + c.getName() + "\nHealth: " + c.getMaxHealth() + "\nAttack Value: " + c.getAttackValue() + "\nNumber of Wins: " + c.getNumWins());
        System.out.println();
    }

    //this method is called in the event of a character attack (excluding spell attack). getAttackdDamage and takeDamage are called
    //to inflict damage on a character. The method returns the health of the attacked character after being attacked
    private static double characterAttacks(Character attacker, double attackerLife, Character opponent, double opponentLife) {
        double damageByAttacker = attacker.getAttackDamage(r.nextInt());
        //format the string
        String attackStr = String.format("%1$.2f", damageByAttacker);
        System.out.println(attacker.getName() + " attacks for " + attackStr + " damage!");
        //opponent life updated
        opponentLife = opponent.takeDamage(damageByAttacker);

        //if a character has been knocked out, we want to skip printing something like "Character has -1.56 current Health"
        if (attackerLife > 0 && opponentLife > 0) {
            System.out.println(opponent);
            System.out.println();
        }

        return opponentLife;
    }

    //this method is called after a spell attack or a normal attack to check if someone's health is equal to or below 0
    // the method does the following:
    //1- declare the winner
    //2- call increaseWins for the winner
    //3- write the wins into the winner.txt file
    //4- returns a boolean which will be used to determine whether the game should end or not
    private static boolean knockoutChecker(Character player, double playerLife, String playerFile, Character monster, double monsterLife, String monsterFile) {
        boolean someoneIsConked = false;
        if (playerLife <= 0 && monsterLife > 0) {
            System.out.println(player.getName() + " was knocked out! You lose.");
            monster.increaseWins();
            FileIO.writeCharacter(monster, monsterFile);
            System.out.println(monster.getName() + " has won: " + monster.getNumWins() + " times");
            someoneIsConked = true;
            return someoneIsConked;
        } else if (playerLife > 0 && monsterLife <= 0) {
            System.out.println(monster.getName() + " was knocked out! You win.");
            player.increaseWins();
            FileIO.writeCharacter(player, playerFile);
            System.out.println(player.getName() + " has won: " + player.getNumWins() + " times");
            someoneIsConked = true;
            return someoneIsConked;
        } else {
            return someoneIsConked;
        }
    }

    //play game
    public static void main(String[] args) {
        //playGame("player.txt", "monster.txt", "spells.txt");
        Character c = new Character("Bob",10.0,30,0);
        int x = c.f;
        System.out.println(x);


    }


}
