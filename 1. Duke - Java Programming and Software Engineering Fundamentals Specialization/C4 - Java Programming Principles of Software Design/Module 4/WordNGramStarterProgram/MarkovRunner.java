
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

   public void runModel(IMarkovModel markov, String text, int size, int seed) {
        markov.setTraining(text);
        markov.setRandom(seed);
        System.out.println("running with " + markov); // utilise toString() de MarkovWordTwo
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    }   

   public void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWordOne markovWord = new MarkovWordOne(); 
        runModel(markovWord, st,120, 139); 
   } 

   public void runMarkovTwo() {
        // Lire le fichier confucius.txt
        FileResource fr = new FileResource("data/confucius.txt");
        String st = fr.asString();
        //st = st.replace('\n', ' '); // enlever les retours à la ligne
    
        // Créer l'objet MarkovWordTwo
        MarkovWordTwo markov = new MarkovWordTwo();
    
        // Générer le texte aléatoire
        runModel(markov, st, 120, 832); // 120 mots, seed 549
   }

   public void testMarkovWordTwo() {
        String text = "this is just a test yes this is a simple test";
        MarkovWordTwo markov = new MarkovWordTwo();
        markov.setTraining(text);

        System.out.println("Follows of 'this is': " + markov.getFollows("this", "is")); 
        System.out.println("Follows of 'just a': " + markov.getFollows("just", "a"));
        System.out.println(markov.getRandomText(10));
   }
    
   private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
   } 

   public void testGetFollows() {
        String text = "this is just a test yes this is a simple test";
        MarkovWordOne markov = new MarkovWordOne();
        markov.setRandom(42);
        runModel(markov, text, 10);
   }
}
