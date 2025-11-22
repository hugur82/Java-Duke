
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

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWord markovWord = new MarkovWord(5); 
        markovWord.setRandom(844);   
        runModel(markovWord, st, 50); 
    } 

    public void runMarkovConfucius1() {
        // Lire le fichier
        FileResource fr = new FileResource("data/confucius.txt");
        String text = fr.asString();
        text = text.replace('\n', ' ');

        // Créer un MarkovWord d'ordre 3
        MarkovWord markovWord = new MarkovWord(3);

        // Définir la graine aléatoire
        markovWord.setRandom(621);

        // Générer le texte
        runModel(markovWord, text, 50);
    }
    
    public void runMarkovConfucius2() {
        // Lire le fichier
        FileResource fr = new FileResource("data/confucius.txt");
        String text = fr.asString();
        text = text.replace('\n', ' ');

        // Créer un MarkovWord d'ordre 5
        MarkovWord markovWord = new MarkovWord(5);

        // Définir la graine aléatoire
        markovWord.setRandom(844);

        // Générer le texte
        runModel(markovWord, text, 50); // ici 50 mots comme exemple
    }
    
    public void testHashMapConfuciusQ3() {
        FileResource fr = new FileResource("data/confucius.txt");
        String text = fr.asString();
        
        EfficientMarkovWord emw = new EfficientMarkovWord(3); // ordre 3
        emw.setRandom(371); // seed
        emw.setTraining(text); // construit la map et printHashMapInfo
        
        emw.printHashMapInfo(); // affiche le nombre de clés et max list
    }

    public void testLargestFollowQ4() {
        FileResource fr = new FileResource("data/confucius.txt");
        String text = fr.asString();
        
        EfficientMarkovWord emw = new EfficientMarkovWord(2); // ordre 2
        emw.setRandom(65);  // seed
        emw.setTraining(text); // construit la map
        
        // Affiche le nombre de clés et la plus grande liste
        emw.printHashMapInfo(); 
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

}
