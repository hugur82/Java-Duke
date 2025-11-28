
/**
 * Write a description of class MarkovWordOne here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.lang.*;

public class MarkovWordOne implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    
    public MarkovWordOne() {
        myRandom = new Random();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-1);  // random word to start with
        String key = myText[index];
        sb.append(key);
        sb.append(" ");
        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.println("Key: " + key + " -> " + follows);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = next;
            
        }
        
        return sb.toString().trim();
    }

    private int indexOf(String[] words, String target,int start){
        for(int k =start; k < words.length; k++){
            if (words[k].equals(target)){
                return k;
            }
        }
        
        return -1;
    }
    
    private ArrayList<String> getFollows(String key) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while(true){
            int index = indexOf(myText, key, pos);
            if (index == -1) break;
            if (index + 1  >= myText.length ) break;
            String next = myText[index + 1];
            follows.add(next);
            pos =index +1 ;
            
        }
        return follows;
    }

    public void testIndexOf (){
        String text = "this is just a test yes this is a simple test";
        String[] words = text.split(" ");
        
        
        System.out.println(indexOf(words, "this", 0));   // → 0
        System.out.println(indexOf(words, "this", 3));   // → 6
        System.out.println(indexOf(words, "frog", 0));   // → -1
        System.out.println(indexOf(words, "frog", 5));   // → -1
        System.out.println(indexOf(words, "simple", 2)); // → 9
        System.out.println(indexOf(words, "test", 5));   // → 10
    }
    
    @Override
    public String toString() {
        return "MarkovWord of order 1";
    }
}
