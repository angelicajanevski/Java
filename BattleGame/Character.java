import java.util.ArrayList;
import java.util.Random;
//Angelica Janevski
//260801180

public class Character {
    public static void main(String[] args) {
        Character c = new Character("booo", 00.0,20.3,3);
        c.currHealth=3;
        c.f=10;
        Character c1 = new Character("jdjddjdjdj",2,2,2);


        System.out.println(c.f);

    }

    //initialize attributes
    private static ArrayList<Spell> spells;
    private String name;
    private double attackValue;
    private double maxHealth;
    private double currHealth;
    private int numWins;
    public static int f=0;
    //character constructor
    public Character(String name, double attackValue, double maxHealth, int numWins) {
        this.name = name;
        this.attackValue = attackValue;
        this.maxHealth = maxHealth;
        this.currHealth = maxHealth;
        this.numWins = numWins;
    }
    //makes a copy of the given array list, sets copy as attribute
    public static void setSpells(ArrayList<Spell> spells) {
        ArrayList<Spell> copy = new ArrayList<Spell>();
        for (int i = 0; i < spells.size(); i++) {       
            copy.add(i, spells.get(i));
        }
        Character.spells = copy;
    }
    //goes through spells array list and prints each
    public static void displaySpells() {
        for (int i = 0; i < spells.size(); i++)
            System.out.println(spells.get(i));

    }
    //converts spell name to lowercase before searching if spellName input string matches spell name inside spells array list
    public double castSpell(String spellName, int i) {

        int k;
        spellName=spellName.toLowerCase();
        for (k = 0; k < spells.size(); k++) {
            if (spells.get(k).getName().equals(spellName)) {
                break;
                //break when kth spell matches
            }
        }
        if (k == spells.size()) {
            //the whole loop has passed so the spell is not in the array list
            return -1;
        }

        //spell is in array list, get magic damage of kth spell
        return spells.get(k).getMagicDamage(i);
    }


    //a bunch of getters. do i really have to comment this?
    public String getName() {
        return name;
    }

    public double getAttackValue() {
        return attackValue;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getCurrHealth() {
        return currHealth;
    }

    public int getNumWins() {
        return numWins;
    }

    //a nice toString to print a character nicely
    public String toString() {
        String health = String.format("%1$.2f", getCurrHealth());
        return getName() + " current health is " + health;
    }

    //generates a random number between specified interval
    public double getAttackDamage(int seed) {
        Random r = new Random(seed);
        return getAttackValue() * (0.7 + 0.3 * (r.nextDouble()));

    }
    //takeDamage subtracts the current health of character
    public double takeDamage(double damage) {
        this.currHealth -= damage;
        return (this.currHealth);
    }

    //increase wins
    public void increaseWins() {
        this.numWins += 1;
    }

}
