import java.util.*;
import edu.duke.*;
/**
 * Décrivez votre classe CharactersInPlay ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class CharactersInPlay
{
   private ArrayList<String> myCharacters;
   private ArrayList<Integer> myCounts;
   
   public CharactersInPlay(){
       myCharacters = new ArrayList<>();
       myCounts = new ArrayList<>();
   }
   
   public void update(String person){
       int index = myCharacters.indexOf(person);
       if (index == -1){
           myCharacters.add(person);
           myCounts.add(1);
       } else {
           myCounts.set(index,myCounts.get(index)+1);
       }
   }
   
   public void findAllCharacters (){
       myCharacters.clear();
       myCounts.clear();
       
       FileResource resource = new FileResource();
       
       for(String line : resource.lines()){
           line = line.trim(); // remove leading/trailing spaces
           int periodIndex = line.indexOf(".");
           if (periodIndex != -1) {
               String possibleName = line.substring(0, periodIndex).trim();
               if (!possibleName.isEmpty()) {
                   update(possibleName);
               }
            }
       }
   }
   
    // Show all characters with counts between num1 and num2
    public void charactersWithNumParts(int num1, int num2) {
        for (int i = 0; i < myCharacters.size(); i++) {
            int count = myCounts.get(i);
            if (count >= num1 && count <= num2) {
                System.out.println(myCharacters.get(i) + "\t" + count);
            }
        }
    }
    
    // Helper to find index of main character (the one with the most lines)
    private int findIndexOfMax() {
        int maxIndex = 0;
        for (int i = 0; i < myCounts.size(); i++) {
            if (myCounts.get(i) > myCounts.get(maxIndex)) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    
    // Tester method
    public void tester() {
        findAllCharacters();
    
        System.out.println("All characters and their speaking parts:");
        for (int i = 0; i < myCharacters.size(); i++) {
            System.out.println(myCharacters.get(i) + "\t" + myCounts.get(i));
        }
    
        System.out.println("\nMain characters (those with > 1 speaking part):");
        charactersWithNumParts(2, 1000);
    
        int maxIndex = findIndexOfMax();
        System.out.println("\nCharacter with the most speaking parts:");
        System.out.println(myCharacters.get(maxIndex) + "\t" + myCounts.get(maxIndex));
    }
}