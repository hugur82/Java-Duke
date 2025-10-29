import edu.duke.*;
import java.util.*;

/**
 * Décrivez votre classe WordFrequencies ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class WordFrequencies
{
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public WordFrequencies(){
        myWords = new ArrayList<String> ();
        myFreqs = new ArrayList<Integer>();
    }
    
    public void findUnique(){
        FileResource resource = new FileResource();
        myWords.clear();
        myFreqs.clear();
        for(String s : resource.words()){
            s = s.toLowerCase();
            int idx = myWords.indexOf(s);
            if(idx == -1){
                myWords.add(s);
                myFreqs.add(1);
            }
            else{
                myFreqs.set(idx, myFreqs.get(idx) + 1);
            }
        }
        
    }
    
    public int findIndexOfMax(){
        int maxFreq = 0;
        int maxDex = 0;
        findUnique();
        for(int i = 0; i<myFreqs.size();i++){
            if (maxFreq < myFreqs.get(i)) {
                maxFreq = myFreqs.get(i);
                maxDex = i;
            }
        }
        return maxDex;
    }
    

    public void test(){
        
        findUnique();
        Scanner sc = new Scanner(System.in);
        int linePerPage = 20;
        
        System.out.println("le nombre de mot inique " + myWords.size());
        for(int i = 0 ; i<myWords.size(); i++){
            System.out.printf("le mot \"%-8s\" apparait %5d fois.%n", myWords.get(i) , myFreqs.get(i));
        
            if((i+1) % linePerPage == 0){
                System.out.println("\n--- Appuyez sur entrée pour continuer---");
                sc.nextLine();
                System.out.println();
            }
        }
        sc.close();
        
        int idx = findIndexOfMax();
        System.out.println("The word that occurs the most is \"" +myWords.get(idx)+ "\" it occurs " +myFreqs.get(idx)+ " times. At index: "+ idx);
    }
}