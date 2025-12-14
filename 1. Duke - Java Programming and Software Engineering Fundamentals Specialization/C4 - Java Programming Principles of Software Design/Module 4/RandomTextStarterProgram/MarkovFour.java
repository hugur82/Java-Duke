
/**
 * Décrivez votre classe MarkovFour ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */

import java.util.*;
public class MarkovFour
{
   private String myText;
    private Random myRandom;
    
    public MarkovFour() {
        myRandom = new Random();
    }
    
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    
    public void setTraining(String s){
        myText = s.trim();
    }
    
    public String getRandomText(int numChars){
        if (myText == null) return "";
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length()-4);
        String key = myText.substring(index,index+4);
        sb.append(key);
        
        for(int k=0; k < numChars-4; k++){
            ArrayList<String> follows = getFollows(key);
            if (follows.size()==0){
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
             key = key.substring(1) + next;
        }
        
        return sb.toString();
    }
    
     public ArrayList<String> getFollows(String key) {
        ArrayList<String> follows = new ArrayList<>();
        int index = 0;
        while (index < myText.length() - key.length()) {
            int pos = myText.indexOf(key, index);
            if (pos == -1) break;
            if (pos + key.length() >= myText.length()) break;
            String next = myText.substring(pos + key.length(), pos + key.length() + 1);
            follows.add(next);
            index = pos + 1;
        }
        return follows;
    }
}