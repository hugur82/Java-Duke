
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.*; 
import java.lang.*;

public class MarkovRunnerWithInterface {
    public void runModel(IMarkovModel markov, String text, int size, int seed) {
       if (markov instanceof AbstractMarkovModel) {
        ((AbstractMarkovModel) markov).setRandom(seed);
    }
    markov.setTraining(text);
    String output = markov.getRandomText(size);
    printOut(output);
}
    
    public void runMarkov() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 200;
        
        MarkovZero mz = new MarkovZero();
        runModel(mz, st, size,42);
    
        MarkovOne mOne = new MarkovOne();
        runModel(mOne, st, size,42);
        
        MarkovModel mThree = new MarkovModel(3);
        runModel(mThree, st, size,42);
        
        MarkovFour mFour = new MarkovFour();
        runModel(mFour, st, size,42);

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
    
    public void testHashMap() {
        String training = "yes-this-is-a-thin-pretty-pink-thistle";
        EfficientMarkovModel em = new EfficientMarkovModel(2);
        em.setRandom(42);
        em.setTraining(training);
    
        String text = em.getRandomText(50);
        System.out.println(text);
    }
    public void testRomeo() {
    FileResource fr = new FileResource("data/romeo.txt");
    String text = fr.asString().replace('\n', ' ');

    EfficientMarkovModel em = new EfficientMarkovModel(5);
    em.setRandom(615);

    runModel(em, text, 500, 615);  // runModel appellera setTraining() et printHashMapInfo()
}
    public void compareMethods() {
        FileResource fr = new FileResource();
        String training = fr.asString();     
        training = training.replace('\n', ' ');

        int order = 2;
        int length = 1000;

        // ---- MarkovModel ----
        System.out.println("---- MarkovModel ----");
        MarkovModel mm = new MarkovModel(order);

        long start1 = System.nanoTime();
        runModel(mm, training, length, 42);
        long end1 = System.nanoTime();
        System.out.println("Time: " + (end1 - start1) / 1_000_000.0 + " ms");

        // ---- EfficientMarkovModel ----
        System.out.println("---- EfficientMarkovModel ----");
        EfficientMarkovModel em = new EfficientMarkovModel(order);

        long start2 = System.nanoTime();
        runModel(em, training, length, 42);
        long end2 = System.nanoTime();
        System.out.println("Time: " + (end2 - start2) / 1_000_000.0 + " ms");
    }
    
    
}
