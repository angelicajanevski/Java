import java.io.*;
import java.util.ArrayList;
//Angelica Janevski
//260801180

public class FileIO {
    public static ArrayList<Spell> readSpells(String filename) {
        //create array list
        ArrayList<Spell> s = new ArrayList<Spell>();

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            ArrayList<String> lines = new ArrayList<>();
            // read all lines and store them in the array
            String currLine = br.readLine();
            int index = 0;
            //while the line isn't null, add the line the lines array list
            while (currLine != null) {
                lines.add(index, currLine);
                currLine = br.readLine();
                index++;
            }

            //find out how many elements (lines) in array list
            int l = lines.size();
            //create a string array that will hold the 4 spell parameters when split from a line
            String[] help = new String[4];
            for (int i = 0; i < l; i++) {
                //for as many lines there are, split line into 4 components, each separated by a tab symbol
                help = lines.get(i).split("\t");
                //with your help array that contains the 4 spell parameters, create a new spell
                Spell newSpell = new Spell(help[0], Double.parseDouble(help[1]), Double.parseDouble(help[2]), Double.parseDouble(help[3]));
                //add this spell to the spells array list
                s.add(i, newSpell);
            }
            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            System.out.println("the file was not there");
        } catch (IOException e) {
            System.out.println("something went wrong");
        }

        //returns array list of spells
        return s;

    }


    public static Character readCharacter(String filename) {
        String[] lines = null;
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            lines = new String[4];
            // read all 4 lines and store them in the array
            for (int l = 0; l < 4; l++) {
                lines[l] = br.readLine();
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("the file was not there");
        } catch (IOException e) {
            System.out.println("something went wrong");
        }


        //Array elements are fed into character constructor, parsed when necessary
        Character c1 = new Character(lines[0], Double.parseDouble(lines[1]), Double.parseDouble(lines[2]), Integer.parseInt(lines[3]));
        return c1;
    }

    public static void writeCharacter(Character c, String charFile) {
        try {
            FileWriter fw = new FileWriter(charFile);
            BufferedWriter bw = new BufferedWriter(fw);

            // writes character name, attack value, max health, and num of wins
            bw.write(c.getName());
            bw.newLine();

            bw.write((c.getAttackValue()) + "");
            bw.newLine();
            bw.write(c.getMaxHealth() + "");
            bw.newLine();
            bw.write(c.getNumWins() + "");
            bw.newLine();

            bw.close();
            fw.close();

            System.out.println("Successfully wrote to file: " + charFile);
        } catch (IOException e) {
            System.out.println("File could not be written.");
        }
    }

}
