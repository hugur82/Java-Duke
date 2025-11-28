
/**
 * Décrivez votre classe MarkovWordTwo ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
/**
 * Write a description of class MarkovWordOne here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.lang.*;

public class MarkovWordTwo implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    
    public MarkovWordTwo() {
        myRandom = new Random();
    }
    
    @Override
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    @Override
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    @Override
    public String getRandomText(int numWords){
         if (myText == null || myText.length < 2) return "";
         
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-1);  // random word to start with
        String key1 = myText[index];
        String key2 = myText[index + 1];
         
        sb.append(key1).append(" ").append(key2).append(" ");
        
        for(int k=0; k < numWords-2; k++){
            ArrayList<String> follows = getFollows(key1,key2);
            
            if (follows.size() == 0) {
                break;
            }
            
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next).append(" ");
            key1 = key2;
            key2 = next;
            
        }
        
        return sb.toString().trim();
    }

    private int indexOf(String[] words, String target1, String target2, int start) {
        for (int i = start; i < words.length - 1; i++) { // -1 pour éviter l'index out of bounds
            if (words[i].equals(target1) && words[i + 1].equals(target2)) {
                return i;
            }
        }
        return -1;
    }
    
    public ArrayList<String> getFollows(String key1, String key2) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while(true){
            int index = indexOf(myText, key1, key2, pos);
            if (index == -1) break;
            if (index + 2 >= myText.length) break; // +2 pour obtenir le mot suivant
    
            String next = myText[index + 2]; // le mot qui suit key1 key2
            follows.add(next);
            pos = index + 1; // avancer pour trouver les autres occurrences
        }

        return follows;
    }

    public void testIndexOf (){
        String text = "this is just a test yes this is a simple test";
        String[] words = text.split(" ");
        
        
        // System.out.println(indexOf(words, "this", 0));   // → 0
        // System.out.println(indexOf(words, "this", 3));   // → 6
        // System.out.println(indexOf(words, "frog", 0));   // → -1
        // System.out.println(indexOf(words, "frog", 5));   // → -1
        // System.out.println(indexOf(words, "simple", 2)); // → 9
        // System.out.println(indexOf(words, "test", 5));   // → 10
    }
    
    @Override
    public String toString() {
        return "MarkovWord of order 2";
    }
}
