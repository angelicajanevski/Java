import java.util.Random;
//Angelica Janevski
//260801180
public class Spell {
    //initialize attributes
    private String name;
    private double minDamage;
    private double maxDamage;
    private double chanceOfSuccess;


    //spell constructor
    public Spell(String name, double minDamage, double maxDamage, double chanceOfSuccess) {
        //watch out if min damage less than 0, min greater than max, success less than 0 or greater than 1
        if (minDamage < 0 || minDamage > maxDamage || chanceOfSuccess < 0 || chanceOfSuccess > 1) {
            throw new IllegalArgumentException("spell creation failed");
        }

        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.chanceOfSuccess = chanceOfSuccess;
    }

    //gets spell name
    public String getName() {
        return name;
    }

    //creates a value for magic damage with random object
    public double getMagicDamage(int seed) {
        Random r = new Random(seed);
        if (r.nextDouble() > chanceOfSuccess) {
            return 0;
        }

        return minDamage + (maxDamage - minDamage) * r.nextDouble();
    }

    //toString method to print object nicely
    public String toString() {
        return "Name: " + this.name + " Damage: " + minDamage + "-" + maxDamage + " Chance of success: " + chanceOfSuccess * 100 + "%";
    }

}
